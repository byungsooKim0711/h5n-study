package com.humuson.imc.crawler.mall.schedule;

import com.humuson.imc.crawler.model.ImcMtMsg;
import com.humuson.imc.crawler.model.MallAdmin;
import com.humuson.imc.crawler.service.MtMsgService;
import com.humuson.imc.crawler.template.TemplateUtils;
import com.humuson.imc.crawler.template.code.Mall;
import com.humuson.imc.crawler.template.code.Welcome;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
    private final ConcurrentHashMap<Long, MallAdmin> mallAdminMap;


    @Scheduled(cron = "${imc.crawler.scheduled.welcome}")
    @Override
    public void schedule() {

        // 어플리케이션 초기화 전에 스케줄러가 동작 방지
        if (!super.isReady()) {
            log.info("[UNREADY WELCOME SCHEDULER...]");
            return;
        }

        // TODO: 계정마다 Driver 생성, 작업 후 quit();
        // TODO: 쓰레드 사용 확인해봐야 한다.
        ChromeDriver driver = super.getChromeDriver();

        log.info("[TRY WELCOME SCHEDULER...]");


        synchronized (driver) {
            // 상점관리 메뉴
            driver.findElement(By.id("QA_Gnb_store")).click();
            // 기본정보관리 메뉴
            driver.findElement(By.id("QA_Lnb_Menu10")).click();
            // 내쇼핑몰 정보 메뉴
            driver.findElement(By.id("QA_Lnb_Menu11")).click();

            String template = TemplateUtils.WELCOME_TEMPLATE;

            // 쇼핑몰 이름
            template = TemplateUtils.replaceTemplateVariable(template, Mall.MALL_NAME.getRegex(), driver.findElement(By.name("mall_name")).getAttribute("value"));
            // 대표 휴대전화
            template = TemplateUtils.replaceTemplateVariable(template, Mall.MALL_TEL_NUMBER.getRegex(), driver.findElement(By.xpath("//div[@id='QA_myShop1']/div[2]/table/tbody/tr[5]/td/input")).getAttribute("value"));
            // 도메인
            String mallUrl = driver.findElement(By.xpath("//div[@id='QA_myShop1']/div[2]/table/tbody/tr[7]/td")).getText();
            template = TemplateUtils.replaceTemplateVariable(template, Mall.MALL_URL.getRegex(), mallUrl);

            // 고객관리 메뉴
            driver.findElement(By.xpath("//A[@id='QA_Gnb_member']")).click();

            driver.findElement(By.id("QA_Lnb_Menu90")).click();

            // N개씩 보기 선택 (Headless에서는 왜 선택이 안될까요..)
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

                    contents = TemplateUtils.replaceTemplateVariable(contents, Welcome.CUSTOMER_NAME.getRegex(), driver.findElement(By.xpath("//div[@id='QA_profile2']/div[4]/table/tbody/tr[" + i + "]/td[3]")).getText());
                    String userId = driver.findElement(By.xpath("//div[@id='QA_profile2']/div[4]/table/tbody/tr[" + i + "]/td[4]")).getText();
                    contents = TemplateUtils.replaceTemplateVariable(contents, Welcome.CUSTOMER_ID.getRegex(), userId);

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
                        log.info("FINISHED TEMPLATE:\n{}", contents);
                    } else {
                        log.warn("UNFINISHED TEMPLATE:\n{}", contents);
                    }
                }
            }

            List<ImcMtMsg> saved = mtMsgService.saveAll(messages);
            log.info("SAVED MESSAGE SIZE: {}.", saved.size());
        }
    }

}
