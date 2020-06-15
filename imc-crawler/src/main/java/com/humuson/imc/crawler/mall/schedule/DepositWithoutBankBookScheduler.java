package com.humuson.imc.crawler.mall.schedule;

import com.humuson.imc.crawler.template.TemplateUtils;
import com.humuson.imc.crawler.template.code.Mall;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


// 무통장 입금 완료 스케줄러
@Slf4j
@Component
public class DepositWithoutBankBookScheduler extends CrawlerBaseScheduler {


    @Override
    protected String parseShopInfo(ChromeDriver driver) {
        // 상점관리 메뉴
        driver.findElement(By.id("QA_Gnb_store")).click();
        // 기본정보관리 메뉴
        driver.findElement(By.id("QA_Lnb_Menu10")).click();
        // 내쇼핑몰 정보 메뉴
        driver.findElement(By.id("QA_Lnb_Menu11")).click();

        String template = TemplateUtils.DEPOSIT_WITHOUT_BANKBOOK;

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
        // 배송 준비중 관리 메뉴
        driver.findElement(By.id("QA_Lnb_Menu72")).click();
    }

    @Override
    protected void searchCondition(ChromeDriver driver) {
        // 시작일 캘린더 (3일 버튼 클릭)
        driver.findElement(By.cssSelector("#QA_deposit1 > div:nth-child(2) > table > tbody > tr:nth-child(2) > td > a:nth-child(4)")).click();
        // option:nth-child(N): N==1 ? 10개씩 보기 / N==2? 20개씩 보기 / N==3? 30개씩 보기 / N==4? 50개씩 보기 / N==5? 100개씩 보기
        driver.findElement(By.cssSelector("#QA_prepareNumber2 > div.mState > div.gRight > select:nth-child(2) > option:nth-child(5)")).click();
        // 검새버특 클릭
        driver.findElement(By.xpath("//a[@id='eBtnSearch']/span")).click();
    }

    @Scheduled(cron = "${imc.crawler.scheduled.without-bankbook}")
    @Override
    protected void schedule() {
        // 어플리케이션 초기화 전에 스케줄러가 동작 방지
        if (super.isReady()) {
            log.info("[UNREADY WELCOME SCHEDULER...]");
            return;
        }

        ChromeDriver driver = super.getChromeDriver("", "");

        log.info("[TRY DEPOSIT WITHOUT BANKBOOK SCHEDULER...]");

        synchronized (driver) {
            String template = this.parseShopInfo(driver);

            this.navigateTemplateMenu(driver);

            this.searchCondition(driver);


        }
    }
}
