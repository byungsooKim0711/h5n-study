package com.humuson.imc.admin.config;

import org.springframework.context.annotation.Bean;

import java.util.concurrent.ConcurrentHashMap;

//@Configuration
//@EnableSpringHttpSession
public class SessionConfig {

    @Bean
    public ImcAdminUserSessionRepository sessionRepository() {
        return new ImcAdminUserSessionRepository(new ConcurrentHashMap<>());
    }
}
