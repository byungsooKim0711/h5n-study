package org.kimbs.uracker.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "uracker", ignoreUnknownFields = true)
public class UrackerConfig {

    private int ttl;

    private Topics topics = new Topics();

    @Data
    public static class Topics {

        // url tracking topic list
        private List<String> histTarget;

        // report topic list
        private List<String> report;

    }
}
