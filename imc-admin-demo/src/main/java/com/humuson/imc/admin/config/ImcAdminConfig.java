package com.humuson.imc.admin.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter
@Validated
@ConstructorBinding
@AllArgsConstructor
@ConfigurationProperties(prefix = "imc.admin")
public class ImcAdminConfig {

    private final List<String> superAdminIpList;

    private final Security security;

    @Getter
    @Validated
    @ConstructorBinding
    @AllArgsConstructor
    public static class Security {
        private final String defaultViewUrl;

        private final String[] permitAllUrl;

        private final String loginUrl;

        private final String logoutUrl;

        private final String[] staticResources;

        private final String[] roleManageUrl;
    }
}
