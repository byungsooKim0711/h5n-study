package com.humuson.imc.crawler.mall.memory;

import com.humuson.imc.crawler.model.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class DuplicateMemoryConfig {

    @Bean(name = "mallAdminMap")
    public ConcurrentHashMap<Long, MallAdmin> mallAdminMap() {
        return new ConcurrentHashMap<>();
    }

    @Bean(name = "mallUserMap")
    public ConcurrentHashMap<Long, MallUser> mallUserMap() {
        return new ConcurrentHashMap<>();
    }

    // mallId + userId hashing
    @Bean(name = "welcomeMap")
    public ConcurrentHashMap<String, WelcomeMessage> welcomeMap() {
        return new ConcurrentHashMap<>();
    }


    // mallId + orderId hashing
    @Bean(name = "depositGuideMap")
    public ConcurrentHashMap<String, DepositMessage> depositGuideMap() {
        return new ConcurrentHashMap<>();
    }

    // mallId + orderId hashing
    @Bean(name = "newOrderMap")
    public ConcurrentHashMap<String, OrderMessage> newOrderMap() {
        return new ConcurrentHashMap<>();
    }

    // mallId + userId + regsteredDate
    @Bean(name = "boardMap")
    public ConcurrentHashMap<String, BoardMessage> boardMap() {
        return new ConcurrentHashMap<>();
    }
}
