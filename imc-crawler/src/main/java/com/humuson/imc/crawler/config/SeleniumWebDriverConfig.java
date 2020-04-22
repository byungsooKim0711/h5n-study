package com.humuson.imc.crawler.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
@ConfigurationProperties(prefix = "imc.crawler.web-driver")
public class SeleniumWebDriverConfig {

    private String key;
    private String value;

    @PostConstruct
    public void init() {
        System.setProperty(key, value);
    }

}
