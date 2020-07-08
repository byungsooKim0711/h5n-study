package org.kimbs.imc.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

import java.util.concurrent.ConcurrentHashMap;

//@Configuration
//@EnableSpringHttpSession
public class SessionConfig {

    @Bean
    public ImcAdminUserSessionRepository sessionRepository() {
        return new ImcAdminUserSessionRepository(new ConcurrentHashMap<>());
    }
}
