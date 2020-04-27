package com.humuson.imc.crawler.mall.schedule;

import com.humuson.imc.crawler.model.ImcMtMsg;
import com.humuson.imc.crawler.service.MtMsgService;
import com.humuson.imc.crawler.template.TemplateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class WelcomeScheduler extends CrawlerBaseScheduler {

    private final MtMsgService mtMsgService;
    private final ConcurrentHashMap<String, Boolean> welcomeMap;
    private final ConcurrentHashMap<String, Boolean> testMap;

    @PostConstruct
    public void init() {
        System.out.println("== [welcome map]");
        welcomeMap.entrySet().forEach(System.out::println);
        System.out.println("== [test map]");
        testMap.entrySet().forEach(System.out::println);
    }

    @Scheduled(cron = "*/1 * * * * *")
    public void schedule() {
        log.info("실행 전");

        // 어플리케이션 초기화 전에 스케줄러가 동작 방지
        if (!super.isReady()) {
            return;
        }

        ChromeDriver driver = super.getChromeDriver();

        log.info("실행 중");


        synchronized (driver) {
            // 상점관리 메뉴
            driver.findElement(By.id("QA_Gnb_store")).click();
            driver.findElement(By.id("QA_Lnb_Menu10")).click();
            driver.findElement(By.id("QA_Lnb_Menu11")).click();

            String template = TemplateUtils.WELCOME_TEMPLATE;

            // 쇼핑몰 이름

            template = TemplateUtils.replaceTemplateVariable(template, "\\$\\{쇼핑몰이름}", driver.findElement(By.name("mall_name")).getAttribute("value"));
            // 대표 휴대전화
            template = TemplateUtils.replaceTemplateVariable(template, "\\$\\{쇼핑몰번호}", driver.findElement(By.xpath("//div[@id='QA_myShop1']/div[2]/table/tbody/tr[5]/td/input")).getAttribute("value"));
            // 도메인
            String mallUrl = driver.findElement(By.xpath("//div[@id='QA_myShop1']/div[2]/table/tbody/tr[7]/td")).getText();
            template = TemplateUtils.replaceTemplateVariable(template, "\\$\\{쇼핑몰URL}", mallUrl);

            // 고객관리 메뉴
            driver.findElement(By.xpath("//A[@id='QA_Gnb_member']")).click();

            driver.findElement(By.id("QA_Lnb_Menu90")).click();

            // N개씩 보기 선택
//            driver.findElement(By.id("rows")).click();
//            new Select(driver.findElement(By.id("rows"))).selectByVisibleText("10개씩보기");
            driver.findElement(By.xpath("//div[@id='QA_profile1']/div/div[4]/a/span")).click();

            // tbody tr, td 가져옴
            WebElement tbody = driver.findElement(By.xpath("//TBODY[@class='center']"));
            List<WebElement> trs = tbody.findElements(By.tagName("tr"));


            List<ImcMtMsg> messages = new ArrayList<>();

            WebElement pageElement = driver.findElement(By.xpath("//DIV[@class='mPaginate']/ol"));
            List<WebElement> liTags = pageElement.findElements(By.tagName("li"));

            boolean stop = false;
            for (WebElement li : liTags) {
                if (stop) {
                    return;
                }
                // 페이지 번호 클릭
                li.click();
                // tr의 인덱싱이 1부터 시작하나 봅니다.
                for (int i = 1; i <= trs.size(); i++) {
                    String contents = template;

                    contents = TemplateUtils.replaceTemplateVariable(contents, "\\$\\{고객이름}", driver.findElement(By.xpath("//div[@id='QA_profile2']/div[4]/table/tbody/tr[" + i + "]/td[3]")).getText());
                    String userId = driver.findElement(By.xpath("//div[@id='QA_profile2']/div[4]/table/tbody/tr[" + i + "]/td[4]")).getText();
                    contents = TemplateUtils.replaceTemplateVariable(contents, "\\$\\{고객ID}", userId);

                    // TODO: 중복체크..
                    if (welcomeMap.containsKey(mallUrl + ":" + userId)) {
                        stop = true;
                        break;
                    } else {
                        welcomeMap.put(mallUrl + ":" + userId, true);
                    }

                    if (TemplateUtils.isFinishedTemplate(contents)) {
                        ImcMtMsg msg = new ImcMtMsg();
                        msg.setReservedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
                        msg.setPhoneNumber(driver.findElement(By.xpath("//div[@id='QA_profile2']/div[4]/table/tbody/tr[" + i + "]/td[7]")).getText().replaceAll("-", ""));
                        msg.setCallback("01049492891");
                        msg.setMessage(contents);
                        msg.setMtType("LM");

                        messages.add(msg);
                        log.info("Finished Template: {}\n", contents);
                    } else {
                        log.warn("Unfinished template: {}\n", contents);
                    }
                }
            }

            List<ImcMtMsg> saved = mtMsgService.saveAll(messages);
            log.info("SAVED MESSAGE SIZE: {}.", saved.size());
        }
    }

}
