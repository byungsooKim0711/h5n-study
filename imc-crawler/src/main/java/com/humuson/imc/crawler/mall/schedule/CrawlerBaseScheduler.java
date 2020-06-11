package com.humuson.imc.crawler.mall.schedule;

import com.humuson.imc.crawler.config.SeleniumWebDriverConfig;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class CrawlerBaseScheduler {

    private boolean ready = false;
    private ChromeDriver driver;

    @Autowired
    private SeleniumWebDriverConfig driverConfig;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReadyEvent() {
        this.ready = true;
    }

    boolean isReady() {
        return !this.ready;
    }

    // 쇼핑몰 정보 메뉴로 이동
    protected abstract String parseShopInfo(ChromeDriver driver);
    protected abstract void navigateTemplateMenu(ChromeDriver driver);
    protected abstract void searchCondition(ChromeDriver driver);
//    protected abstract boolean checkDuplicateMessage(Object object);

    protected abstract void schedule();

    ChromeDriver getChromeDriver(String id, String pw) {
        if (this.driver != null) {
            return this.driver;
        }

        ChromeOptions options = new ChromeOptions();
        this.driverConfig.getOptions().forEach(options::addArguments);
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(driverConfig.getImplicitlyWait(), TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get("https://eclogin.cafe24.com/Shop/");
        driver.findElement(By.id("mall_id")).click();
        driver.findElement(By.id("mall_id")).clear();
        driver.findElement(By.id("mall_id")).sendKeys(id);
        driver.findElement(By.id("userpasswd")).click();
        driver.findElement(By.id("userpasswd")).clear();
        driver.findElement(By.id("userpasswd")).sendKeys(pw);
        driver.findElement(By.linkText("로그인")).click();

        // 로그인 이후 비밀번호 변경 페이지가 나왔을 때
        List<WebElement> changePassword = driver.findElements(By.id("iptBtnEm"));
        if (changePassword != null && !changePassword.isEmpty()) {
            changePassword.get(0).click();
        }

        // 스마트모드라면 프리모드로 변경
        driver.findElement(By.id("ec-influencer-gnb-mode-pro")).click();
        return this.driver;
    }

    // 에러발생 시 기존 driver를 버린다.
    protected void onException() {
        this.driver.quit();
        this.driver = null;
    }

    @PreDestroy
    public void preDestroy() {
        if (this.driver != null) {
            log.info("DESTROY WEB DRIVER: {}", this.driver);
            this.driver.quit();
        }
    }
}