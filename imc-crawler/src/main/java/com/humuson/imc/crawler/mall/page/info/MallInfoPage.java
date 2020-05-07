package com.humuson.imc.crawler.mall.page.info;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class MallInfoPage {

    public void navigateMallInfoPage(ChromeDriver driver) {
        // 상점관리 메뉴
        driver.findElement(By.id("QA_Gnb_store")).click();
        // 기본정보관리 메뉴
        driver.findElement(By.id("QA_Lnb_Menu10")).click();
        // 내쇼핑몰 정보 메뉴
        driver.findElement(By.id("QA_Lnb_Menu11")).click();
    }

}
