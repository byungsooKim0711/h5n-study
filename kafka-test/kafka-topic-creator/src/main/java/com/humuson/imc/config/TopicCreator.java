package com.humuson.imc.config;

import com.humuson.imc.config.KafkaTopicConfigurations.KafkaTopicConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class TopicCreator {

    private final KafkaTopicConfigurations topicConfig;
    private final GenericApplicationContext context;

    @PostConstruct
    public void createTopics() {
        topicConfig.getTopics().ifPresent(this::initializeBeans);
    }

    private void initializeBeans(List<KafkaTopicConfig> topics) {
        log.info("Configuring {} topics", topics.size());
        topics.forEach(topic -> {
            log.info("CREATION TOPIC='{}', PARTITION='{}', REPLICATION-FACTOR='{}'", topic.getName(), topic.getPartition(), topic.getReplicationFactor());
            context.registerBean(topic.getName(), NewTopic.class, topic::createNewTopic);
        });
    }
}
