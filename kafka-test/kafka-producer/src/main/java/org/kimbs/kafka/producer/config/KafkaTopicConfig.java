package org.kimbs.kafka.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic newTopic() {
        return TopicBuilder
                .name("kbs")
                .partitions(3)
                // TODO: 옵션값들 이해하기...
//                .assignReplicas()
//                .compact()
//                .configs()
//                .config()
//                .replicas()
//                .replicasAssignments()
                .build();
    }
}
