package com.humuson.imc.admin.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
@ConstructorBinding
@AllArgsConstructor
@ConfigurationProperties(prefix = "imc.admin")
public class ImcAdminConfig {

    private Security security;

    @Getter
    @Validated
    @ConstructorBinding
    @AllArgsConstructor
    public static class Security {
        private String defaultViewUrl;

        private String[] permitAllUrl;

        private String loginUrl;

        private String logoutUrl;

        private String[] staticResources;

        private String[] roleManageUrl;
    }
}
