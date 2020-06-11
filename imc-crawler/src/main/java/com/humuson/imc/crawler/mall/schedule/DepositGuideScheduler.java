package com.humuson.imc.crawler.mall.schedule;

import com.humuson.imc.crawler.service.MtMsgService;
import com.humuson.imc.crawler.template.TemplateUtils;
import com.humuson.imc.crawler.template.code.Deposit;
import com.humuson.imc.crawler.template.code.Mall;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
//@Component
@RequiredArgsConstructor
public class DepositGuideScheduler extends CrawlerBaseScheduler {

    private final MtMsgService mtMsgService;

    @Override
    protected String parseShopInfo(ChromeDriver driver) {
        // 상점관리 메뉴
        driver.findElement(By.id("QA_Gnb_store")).click();
        // 기본정보관리 메뉴
        driver.findElement(By.id("QA_Lnb_Menu10")).click();
        // 내쇼핑몰 정보 메뉴
        driver.findElement(By.id("QA_Lnb_Menu11")).click();

        String template = TemplateUtils.DEPOSIT_GUIDE_TEMPLATE;

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
        // 주문관리 메뉴 클릭
        driver.findElement(By.id("QA_Gnb_sales")).click();
        //입금전 관리 메뉴 클릭
        driver.findElement(By.id("QA_Lnb_Menu71")).click();
    }

    @Override
    protected void searchCondition(ChromeDriver driver) {
        // 시작일 캘린더 (3일 버튼 클릭)
        driver.findElement(By.cssSelector("#QA_deposit1 > div:nth-child(2) > table > tbody > tr:nth-child(2) > td > a:nth-child(4)")).click();

        // option:nth-child(N): N==1 ? 10개씩 보기 / N==2? 20개씩 보기 / N==3? 30개씩 보기 / N==4? 50개씩 보기 / N==5? 100개씩 보기
        driver.findElement(By.cssSelector("#tabNumber > div.mState > div.gRight > select:nth-child(2) > option:nth-child(5)")).click();
        // 검색버튼 클릭
        driver.findElement(By.xpath("//a[@id='eBtnSearch']/span")).click();
    }

    @Scheduled(cron = "${imc.crawler.scheduled.deposit-guide}")
    @Override
    public void schedule() {
        if (super.isReady()) {
            log.info("[UNREADY DEPOSIT-GUIDE SCHEDULER...]");
            return;
        }
        ChromeDriver driver = super.getChromeDriver("", "");

        log.info("[TRY DEPOSIT-GUIDE SCHEDULER...]");

        synchronized (driver) {
            String template = this.parseShopInfo(driver);

            this.navigateTemplateMenu(driver);

            this.searchCondition(driver);

            // 메인 페이지
            String parentWindow = driver.getWindowHandle();
            log.info("BEFORE SWITCHING WINDOW: {}, TITLE: {}.", parentWindow, driver.getTitle());

            List<WebElement> pageElements = driver.findElements(By.xpath("//DIV[@class='mPaginate']/ol"));
            List<WebElement> liTags = null;
            if (pageElements != null && !pageElements.isEmpty()) {
                liTags = pageElements.get(0).findElements(By.tagName("li"));
            }

            int l = 0;
            // do while로 진행 한 이유는 페이징 할 게 없으면 표시가 안됨.
            do {
                int liTagSize = 0;
                if (liTags != null && !liTags.isEmpty()) {
                    WebElement pageElement1 = driver.findElement(By.xpath("//DIV[@class='mPaginate']/ol"));
                    List<WebElement> liTags1 = pageElement1.findElements(By.tagName("li"));
                    liTagSize = liTags1.size();
                    // 페이지 번호 클릭
                    if (l > 0) {
                        liTags1.get(l).click();
                    }
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
                    String price = driver.findElement(By.cssSelector("#payInfoDetail > div.detailView > ul > li:nth-child(2) > span")).getText().replaceAll(",", "");
                    String username = driver.findElement(By.xpath("//div[@id='QA_detail4']/div[2]/table/tbody/tr/td")).getText();
                    username = username.split("\\(")[0].trim();

                    log.info("PRICE:{}, BANK:{}, ACCOUNT:{}, DEPOSITOR:{}", price, bank, account, depositor);
                    String contents = template;
                    contents = TemplateUtils.replaceTemplateVariable(contents, Deposit.DEPOSIT_BANK.getRegex(), bank);
                    contents = TemplateUtils.replaceTemplateVariable(contents, Deposit.DEPOSIT_ACCOUNT.getRegex(), account);
                    contents = TemplateUtils.replaceTemplateVariable(contents, Deposit.DEPOSIT_DEPOSITOR.getRegex(), depositor);
                    contents = TemplateUtils.replaceTemplateVariable(contents, Deposit.DEPOSIT_PRICE.getRegex(), price);
                    contents = TemplateUtils.replaceTemplateVariable(contents, Deposit.DEPOSIT_USER.getRegex(), username);
                    if (TemplateUtils.isFinishedTemplate(contents)) {
                        log.info("\nFINISHED TEMPLATE:\n{}", contents);
                    } else {
                        log.error("\nUNFINISHED TEMPLATE:\n{}", contents);
                    }

                    // 새로 띄운 창 닫음
                    driver.close();
                    // 메인 페이지로 포커스 이동
                    driver.switchTo().window(parentWindow);
                    log.info("BACK TO PARENT WINDOW: {}, TITLE: {}", driver.getWindowHandle(), driver.getTitle());
                }
                l++;
                // N 개의 페이지 번호를 전부 클릭했고, Next 버튼이 있을 경우
                if (l >= liTagSize) {
                    List<WebElement> nextButton = driver.findElements(By.cssSelector("#tabNumber > div.mPaginate > a"));
                    if (nextButton != null && !nextButton.isEmpty()) {
                        nextButton.get(0).click();
                        l = 0;
                        log.info("[CLICK THE NEXT BUTTON]");
                    }
                }
            } while (liTags != null && l < liTags.size());
        }
    }
}
