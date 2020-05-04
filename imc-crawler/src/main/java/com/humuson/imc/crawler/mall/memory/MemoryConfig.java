package com.humuson.imc.crawler.mall.memory;

import com.humuson.imc.crawler.model.MallAdmin;
import com.humuson.imc.crawler.model.MallUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class MemoryConfig {

    @Bean(name = "welcomeMap")
    public ConcurrentHashMap<String, Boolean> welcomeMap() {
        return new ConcurrentHashMap<>();
    }

    @Bean(name = "mallAdminMap")
    public ConcurrentHashMap<Long, MallAdmin> mallAdminMap() {
        return new ConcurrentHashMap<>();
    }

    @Bean(name = "mallUserMap")
    public ConcurrentHashMap<Long, MallUser> mallUserMap() {
        return new ConcurrentHashMap<>();
    }

//    @Bean(name = "depositGuideMap")
//    public ConcurrentHashMap<Long, DepositGuide> depositGuideMap() {
//        return new ConcurrentHashMap<>();
//    }
}
