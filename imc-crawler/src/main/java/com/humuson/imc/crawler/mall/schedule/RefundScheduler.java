package com.humuson.imc.crawler.mall.schedule;

import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

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

    @Override
    protected void schedule() {

    }
}
