package com.humuson.imc.crawler.mall.memory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class MemoryConfig {

    @Bean(name = "welcomeMap")
    public ConcurrentHashMap<String, Boolean> welcomeMap() {
        return new ConcurrentHashMap<>();
    }

    @Bean(name = "testMap")
    public ConcurrentHashMap<String, Boolean> testMap() {
        ConcurrentHashMap<String, Boolean> map = new ConcurrentHashMap<>();
        map.put("test1", true);
        map.put("test2", true);
        map.put("test3", true);
        return map;
    }
}
