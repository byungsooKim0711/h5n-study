package com.humuson.imc.crawler.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "imc.crawler.web-driver")
public class SeleniumWebDriverConfig {

    private String key;
    private String value;
    private List<String> options = Collections.emptyList();
    private int implicitlyWait = 10;

    @PostConstruct
    public void init() {
        log.info("Selenium Key: {}, Value: {}", key, value);
        log.info("Selenium Options: {}", options);
        log.info("Selenium implicitly wait: {}", implicitlyWait);

        System.setProperty(key, value);
    }
}
