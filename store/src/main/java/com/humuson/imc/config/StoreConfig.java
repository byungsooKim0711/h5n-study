package com.humuson.imc.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
@ConfigurationProperties(prefix = "imc.relay", ignoreUnknownFields = true)
@Slf4j
public class StoreConfig {

    // server name
    private String serverName;

    // sql context 파일 경로 설정
    private String sqlContextPath;

    // sql logging 설정
    private boolean showSql;

    @Data
    public static class Scheduled {

        // db 주요 정보를 redis에 저장할 지 여부
        private boolean enableRedisStore;

        // apiInfo 정보를 db에서 redis로 저장하는 주기
        private String redisStoreApiInfo;
    }

    @PostConstruct
    private void init() {
        StringBuilder builder = new StringBuilder();

        // server name
        builder.append("\t").append("server-name").append(" = ").append(serverName).append(System.lineSeparator());

        // sql context 파일 경로 설정
        builder.append("\t").append("sql-context-path").append(" = ").append(sqlContextPath)
                .append(System.lineSeparator());

        // sql logging 설정
        builder.append("\t").append("show-sql").append(" = ").append(showSql).append(System.lineSeparator());

        log.info("[Configurations]\n{}", builder.toString());
    }
}
