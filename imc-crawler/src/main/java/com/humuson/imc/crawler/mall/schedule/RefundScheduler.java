package com.humuson.imc.crawler.mall.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RefundScheduler extends CrawlerBaseScheduler {

    @Override
    protected String parseShopInfo(ChromeDriver driver) {
        return null;
    }

    @Override
    protected void navigateTemplateMenu(ChromeDriver driver) {

    }

    @Override
    protected void searchCondition(ChromeDriver driver) {

    }

    @Schedules(value = {
            @Scheduled(cron = "0 0/2 * * * *")
    })
    @Override
    protected void schedule() {
    }
}
