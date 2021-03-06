package com.humuson.imc.config;

import lombok.Data;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.List;
import java.util.Optional;

@Data
@Configuration
@ConfigurationProperties(prefix = "imc.kafka")
public class KafkaTopicConfigurations {

    private List<KafkaTopicConfig> topics;

    public Optional<List<KafkaTopicConfig>> getTopics() {
        return Optional.ofNullable(this.topics);
    }

    @Data
    public static class KafkaTopicConfig {
        private String name;

        // application.yml 에 입력 안하면 기본 설정 1
        private Integer partition = 1;
        private Integer replicationFactor = 1;

        public NewTopic createNewTopic() {
            return TopicBuilder
                    .name(this.name)
                    .partitions(this.partition)
                    .replicas(this.replicationFactor)
                    .build();
        }
    }
}
