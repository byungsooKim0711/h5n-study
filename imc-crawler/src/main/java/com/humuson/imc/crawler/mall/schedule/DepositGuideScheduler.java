package com.humuson.imc.crawler.mall.schedule;

import com.humuson.imc.crawler.service.MtMsgService;
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
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DepositGuideScheduler extends CrawlerBaseScheduler {

    private final MtMsgService mtMsgService;

    @Scheduled(cron = "${imc.crawler.scheduled.deposit-guide}")
    @Override
    public void schedule() {
        if (!super.isReady()) {
            log.info("[UNREADY DEPOSIT-GUIDE SCHEDULER...]");
            return;
        }
        ChromeDriver driver = super.getChromeDriver();

        log.info("[TRY DEPOSIT-GUIDE SCHEDULER...]");

        synchronized (driver) {
            // 주문관리 메뉴 클릭
            driver.findElement(By.id("QA_Gnb_sales")).click();
            //입금전 관리 메뉴 클릭
            driver.findElement(By.id("QA_Lnb_Menu71")).click();

            // 검색조건: 어제 ~ 오늘
            // 시작일 캘린더 (어제)
            LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
            driver.findElement(By.id("eStartCalendar")).click();
            driver.findElement(By.xpath("//li[2]/select")).click();
            new Select(driver.findElement(By.xpath("//li[2]/select"))).selectByVisibleText(yesterday.format(DateTimeFormatter.ofPattern("yyyy")));
            driver.findElement(By.xpath("//li[2]/select")).click();
            driver.findElement(By.xpath("//li[2]/select[2]")).click();
            new Select(driver.findElement(By.xpath("//li[2]/select[2]"))).selectByVisibleText(yesterday.format(DateTimeFormatter.ofPattern("MM")));
            driver.findElement(By.xpath("//li[2]/select[2]")).click();
            driver.findElement(By.linkText(yesterday.format(DateTimeFormatter.ofPattern("dd")))).click();

            // 종료일 캘린더 (오늘)
            LocalDateTime today = LocalDateTime.now();
            driver.findElement(By.id("eEndCalendar")).click();
            driver.findElement(By.xpath("//li[2]/select")).click();
            new Select(driver.findElement(By.xpath("//li[2]/select"))).selectByVisibleText(today.format(DateTimeFormatter.ofPattern("yyyy")));
            driver.findElement(By.xpath("//li[2]/select")).click();
            driver.findElement(By.xpath("//li[2]/select[2]")).click();
            new Select(driver.findElement(By.xpath("//li[2]/select[2]"))).selectByVisibleText(today.format(DateTimeFormatter.ofPattern("MM")));
            driver.findElement(By.xpath("//li[2]/select[2]")).click();
            driver.findElement(By.linkText(today.format(DateTimeFormatter.ofPattern("dd")))).click();


            // 검색버튼 클릭
            driver.findElement(By.xpath("//a[@id='eBtnSearch']/span")).click();

            // 메인 페이지
            String parentWindow = driver.getWindowHandle();
            log.info("BEFORE SWITCHING WINDOW: {}, TITLE: {}.", parentWindow, driver.getTitle());

            WebElement pageElement = driver.findElement(By.xpath("//DIV[@class='mPaginate']/ol"));
            List<WebElement> liTags = pageElement.findElements(By.tagName("li"));

            for (int l=0; l<liTags.size(); l++) {
                WebElement pageElement1 = driver.findElement(By.xpath("//DIV[@class='mPaginate']/ol"));
                List<WebElement> liTags1 = pageElement1.findElements(By.tagName("li"));
                // 페이지 번호 클릭
                if (l != 0) {
                    liTags1.get(l).click();
                }

                WebElement table = driver.findElement(By.id("searchResultList"));
                List<WebElement> tbodys = table.findElements(By.xpath(".//TBODY[@class='center']"));

                for (int i=0; i<tbodys.size(); i++) {
                    log.info("PAGE NUMBER: {}, INDEX: {}", l, i);
                    WebElement tbody = tbodys.get(i);

                    tbody.findElement(By.id("eOrderDetailPopup" + i)).click();
                    for (String window : driver.getWindowHandles()) {
                        // 새로 띄운 창으로 포커스 변경
                        if (!window.equals(parentWindow)) {
                            driver.switchTo().window(window);
                            log.info("AFTER SWITCHING WINDOW: {}, TITLE: {}", window, driver.getTitle());
                            break;
                        }
                    }
                    String accountInfo = driver.findElement(By.xpath("//div[@id='QA_detail8']/div[2]/table/tbody/tr[3]/td")).getText();
                    String[] splitAccountInfo = accountInfo.split("/");
                    String bank = splitAccountInfo[0].trim();
                    String account = splitAccountInfo[1].trim();
                    String depositor = splitAccountInfo[2].trim().split(" ")[0].trim();

                    log.info("BANK:{}, ACCOUNT:{}, DEPOSITOR:{}", bank, account, depositor);

                    // 새로 띄운 창 닫음
                    driver.close();
                    // 메인 페이지로 포커스 이동
                    driver.switchTo().window(parentWindow);
                    log.info("BACK TO PARENT WINDOW: {}, TITLE: {}", driver.getWindowHandle(), driver.getTitle());
                }
            }
        }
    }
}
