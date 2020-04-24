package com.humuson.imc.crawler.cafe24.schedule;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Component
public class WelcomeScheduler extends BaseScheduler {

    Random random = new Random();

    @Scheduled(cron = "*/1 * * * * *")
    public void schedule() {
        log.info("실행 전");

        // 어플리케이션 초기화 전에 스케줄러가 동작 방지
        if (!super.isReady()) {
            return ;
        }

        ChromeDriver driver = super.getChromeDriver();

        log.info("실행 중");


        synchronized (driver) {
            driver.findElement(By.xpath("//A[@id='QA_Gnb_member']")).click();

//            String newMemberCount = driver.findElement(By.xpath("//div[@id='content']/div[2]/div/div[2]/table/tbody/tr/td/strong")).getText();

//            log.info(newMemberCount);
//            if (newMemberCount.trim().equals("0")) {
//                log.info("추가인원 없음");
//                return;
//            }
//            log.info("추가인원 있음");

            driver.findElement(By.id("QA_Lnb_Menu90")).click();
//            driver.findElement(By.name("day_type")).click();
//            new Select(driver.findElement(By.name("day_type"))).selectByVisibleText("가입일");
//            driver.findElement(By.name("day_type")).click();
            driver.findElement(By.id("rows")).click();
            new Select(driver.findElement(By.id("rows"))).selectByVisibleText("10개씩보기");
//            driver.findElement(By.xpath("//div[@id='QA_profile1']/div/div[4]/a/span")).click();

            WebElement tbody = driver.findElement(By.xpath("//TBODY[@class='center']"));
            List<WebElement> trs = tbody.findElements(By.tagName("tr"));

            for (WebElement tr : trs) {
                List<WebElement> tds = tr.findElements(By.tagName("td"));

                log.info(tds.stream().map(WebElement::getText).collect(Collectors.joining(", ")));

            }

            // 페이지 클릭
            WebElement pageElement = driver.findElement(By.xpath("//DIV[@class='mPaginate']/ol"));
            List<WebElement> liTags = pageElement.findElements(By.tagName("li"));
            for (WebElement li : liTags) {
                li.click();
            }

            int test = random.nextInt();
            System.out.println(test);
            if (test%5 == 0) {
                log.info("[EXCEPTION]-[EXCEPTION]-[EXCEPTION]-[EXCEPTION]-[EXCEPTION]-[EXCEPTION]");
                super.onException();
            }

            //TODO: N개씩 보기 페이징 처리, 중복 체크, ...
        }
    }

    private boolean checkNewMember() {
        return false;
    }
}
