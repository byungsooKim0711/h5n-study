package com.humuson.imc.crawler.mall.schedule;

import com.humuson.imc.crawler.model.ImcMtMsg;
import com.humuson.imc.crawler.service.MtMsgService;
import com.humuson.imc.crawler.template.TemplateUtils;
import com.humuson.imc.crawler.template.code.Mall;
import com.humuson.imc.crawler.template.code.Welcome;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
//@Component
@RequiredArgsConstructor
public class WelcomeScheduler extends CrawlerBaseScheduler {

    private final MtMsgService mtMsgService;

    @Override
    protected String parseShopInfo(ChromeDriver driver) {
        // 상점관리 메뉴
        driver.findElement(By.id("QA_Gnb_store")).click();
        // 기본정보관리 메뉴
        driver.findElement(By.id("QA_Lnb_Menu10")).click();
        // 내쇼핑몰 정보 메뉴
        driver.findElement(By.id("QA_Lnb_Menu11")).click();

        String template = TemplateUtils.WELCOME_TEMPLATE;

        // 쇼핑몰 이름
        String shopName = driver.findElement(By.name("mall_name")).getAttribute("value");
        // 대표 휴대전화
        String phoneNumber = driver.findElement(By.xpath("//div[@id='QA_myShop1']/div[2]/table/tbody/tr[5]/td/input")).getAttribute("value");
        // 도메인
        String mallUrl = driver.findElement(By.xpath("//div[@id='QA_myShop1']/div[2]/table/tbody/tr[7]/td")).getText();

        template = TemplateUtils.replaceTemplateVariable(template, Mall.MALL_NAME.getRegex(), shopName);
        template = TemplateUtils.replaceTemplateVariable(template, Mall.MALL_TEL_NUMBER.getRegex(), phoneNumber);
        template = TemplateUtils.replaceTemplateVariable(template, Mall.MALL_URL.getRegex(), mallUrl);

        return template;
    }

    @Override
    protected void navigateTemplateMenu(ChromeDriver driver) {
        // 고객관리 메뉴
        driver.findElement(By.xpath("//A[@id='QA_Gnb_member']")).click();

        driver.findElement(By.id("QA_Lnb_Menu90")).click();
    }

    @Override
    protected void searchCondition(ChromeDriver driver) {
        // 검색조건:
        // 가입일 선택
        driver.findElement(By.name("day_type")).click();
        new Select(driver.findElement(By.name("day_type"))).selectByVisibleText("가입일");
        driver.findElement(By.name("day_type")).click();

        // 시작일 캘린더 (3일 전)
        LocalDateTime yesterday = LocalDateTime.now().minusDays(3);
        driver.findElement(By.id("btnRegistStartDate")).click();
        driver.findElement(By.xpath("//li[2]/select")).click();
        new Select(driver.findElement(By.xpath("//li[2]/select"))).selectByVisibleText(yesterday.format(DateTimeFormatter.ofPattern("yyyy")));
        driver.findElement(By.xpath("//li[2]/select")).click();
        driver.findElement(By.xpath("//li[2]/select[2]")).click();
        new Select(driver.findElement(By.xpath("//li[2]/select[2]"))).selectByVisibleText(yesterday.format(DateTimeFormatter.ofPattern("MM")));
        driver.findElement(By.xpath("//li[2]/select[2]")).click();
        driver.findElement(By.linkText(yesterday.format(DateTimeFormatter.ofPattern("dd")))).click();

        // 종료일 캘린더 (오늘)
        LocalDateTime today = LocalDateTime.now();
        driver.findElement(By.id("btnRegistEndDate")).click();
        driver.findElement(By.xpath("//li[2]/select")).click();
        new Select(driver.findElement(By.xpath("//li[2]/select"))).selectByVisibleText(today.format(DateTimeFormatter.ofPattern("yyyy")));
        driver.findElement(By.xpath("//li[2]/select")).click();
        driver.findElement(By.xpath("//li[2]/select[2]")).click();
        new Select(driver.findElement(By.xpath("//li[2]/select[2]"))).selectByVisibleText(today.format(DateTimeFormatter.ofPattern("MM")));
        driver.findElement(By.xpath("//li[2]/select[2]")).click();
        driver.findElement(By.linkText(today.format(DateTimeFormatter.ofPattern("dd")))).click();

        // option:nth-child(N): N==1 ? 10개씩 보기 / N==2? 20개씩 보기 / N==3? 30개씩 보기 / N==4? 50개씩 보기 / N==5? 100개씩 보기
        driver.findElement(By.cssSelector("#QA_profile2 > div.mState > div.gRight > select:nth-child(2) > option:nth-child(5)")).click();
        // 검색버튼 클릭
        driver.findElement(By.xpath("//div[@id='QA_profile1']/div/div[4]/a/span")).click();
    }

    @Scheduled(cron = "${imc.crawler.scheduled.welcome}")
    @Override
    public void schedule() {

        // 어플리케이션 초기화 전에 스케줄러가 동작 방지
        if (super.isReady()) {
            log.info("[UNREADY WELCOME SCHEDULER...]");
            return;
        }

        // TODO: 계정마다 Driver 생성, 작업 후 quit();
        // TODO: 쓰레드 사용 확인해봐야 한다.
        ChromeDriver driver = super.getChromeDriver("", "");

        log.info("[TRY WELCOME SCHEDULER...]");

        synchronized (driver) {
            String template = this.parseShopInfo(driver);

            this.navigateTemplateMenu(driver);

            this.searchCondition(driver);

            WebElement pageElement = driver.findElement(By.xpath("//DIV[@class='mPaginate']/ol"));
            List<WebElement> liTags = pageElement.findElements(By.tagName("li"));

            List<ImcMtMsg> messages = new ArrayList<>();

            int l = 0;
            // do while로 안해도 되는 이유는 페이징 할 게 없어도 1 표시가 됨.
            while (l < liTags.size()) {
                WebElement pageElement1 = driver.findElement(By.xpath("//DIV[@class='mPaginate']/ol"));
                liTags = pageElement1.findElements(By.tagName("li"));
                if (l > 0) {
                    liTags.get(l).click();
                }

                // tbody tr, td 가져옴
                WebElement tbody = driver.findElement(By.xpath("//TBODY[@class='center']"));
                List<WebElement> trs = tbody.findElements(By.tagName("tr"));

                // tr의 인덱싱이 1부터 시작하나 봅니다.
                for (int i = 1; i <= trs.size(); i++) {
                    String contents = template;

                    contents = TemplateUtils.replaceTemplateVariable(contents, Welcome.CUSTOMER_NAME.getRegex(), driver.findElement(By.xpath("//div[@id='QA_profile2']/div[4]/table/tbody/tr[" + i + "]/td[3]")).getText());
                    String userId = driver.findElement(By.xpath("//div[@id='QA_profile2']/div[4]/table/tbody/tr[" + i + "]/td[4]")).getText();
                    contents = TemplateUtils.replaceTemplateVariable(contents, Welcome.CUSTOMER_ID.getRegex(), userId);

                    // TODO: 중복체크..
//                    if (welcomeMap.containsKey(mallUrl + ":" + userId)) {
//                        stop = true;
//                        break;
//                    } else {
//                        welcomeMap.put(mallUrl + ":" + userId, true);
//                    }

                    if (TemplateUtils.isFinishedTemplate(contents)) {
                        // TODO: 발송 방식 정해지면, 문자메시지에서 알림톡으로 전환
                        ImcMtMsg msg = new ImcMtMsg();
                        msg.setReservedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
                        msg.setPhoneNumber(driver.findElement(By.xpath("//div[@id='QA_profile2']/div[4]/table/tbody/tr[" + i + "]/td[7]")).getText().replaceAll("-", ""));
                        msg.setCallback("01049492891");
                        msg.setMessage(contents);
                        msg.setMtType("LM");

                        messages.add(msg);
                        log.info("\nFINISHED TEMPLATE:\n{}", contents);
                    } else {
                        log.error("\nUNFINISHED TEMPLATE:\n{}", contents);
                    }
                }
                l++;
                // N 개의 페이지 번호를 전부 클릭했고, Next 버튼이 있을 경우
                if (l >= liTags.size()) {
                    List<WebElement> nextButton = driver.findElements(By.cssSelector("#tabNumber > div.mPaginate > a"));
                    if (nextButton!= null && !nextButton.isEmpty()) {
                        nextButton.get(0).click();
                        l = 0;
                        log.info("[CLICK THE NEXT BUTTON]");
                    }
                }
            }

            List<ImcMtMsg> saved = mtMsgService.saveAll(messages);
            log.info("SAVED MESSAGE SIZE: {}.", saved.size());
        }
    }
}
