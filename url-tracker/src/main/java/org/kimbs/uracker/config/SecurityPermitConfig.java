package org.kimbs.uracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityPermitConfig {

    @Bean
    public SecurityWebFilterChain springSecurityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange()
                .anyExchange()
                .permitAll()
                .and()
                .csrf()
                .disable()
                .build();
    }
}
