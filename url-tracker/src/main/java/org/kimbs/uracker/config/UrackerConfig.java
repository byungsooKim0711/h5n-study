package org.kimbs.uracker.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "", ignoreUnknownFields = true)
public class UrackerConfig {
}
