package com.humuson.imc.crawler.mall.schedule;

import com.humuson.imc.crawler.config.SeleniumWebDriverConfig;
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
        return this.ready;
    }

    protected abstract void schedule();

    ChromeDriver getChromeDriver() {
        if (this.driver != null) {
            return this.driver;
        }

        ChromeOptions options = new ChromeOptions();
//        options.setPageLoadStrategy(PageLoadStrategy.NONE);
        this.driverConfig.getOptions().forEach(options::addArguments);
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get("https://eclogin.cafe24.com/Shop/");
        driver.findElement(By.id("mall_id")).click();
        driver.findElement(By.id("mall_id")).clear();
        driver.findElement(By.id("mall_id")).sendKeys("asdf");
        driver.findElement(By.id("userpasswd")).click();
        driver.findElement(By.id("userpasswd")).clear();
        driver.findElement(By.id("userpasswd")).sendKeys("asdf!");
        driver.findElement(By.linkText("로그인")).click();

        // 로그인 이후 비밀번호 변경 페이지가 나왔을 때
        List<WebElement> changePassword = driver.findElements(By.id("iptBtnEm"));
        if (changePassword != null && !changePassword.isEmpty()) {
            changePassword.get(0).click();
        }

        driver.findElement(By.id("ec-influencer-gnb-mode-pro")).click();
//        List<WebElement> gnb = driver.findElements(By.id("ec-influencer-gnb-mode-pro"));
//        if (gnb != null && !gnb.isEmpty()) {
//            gnb.get(0).click();
//        }
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
            this.driver.quit();
        }
    }
}
