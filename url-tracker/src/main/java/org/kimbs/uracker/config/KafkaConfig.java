package org.kimbs.uracker.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

import javax.annotation.PostConstruct;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final KafkaProperties kafkaProperties;

    @PostConstruct
    public void init() {
        System.out.println(kafkaProperties.getAdmin().getClientId());
        System.out.println(kafkaProperties.getAdmin().getProperties());
        System.out.println(kafkaProperties.getAdmin().getSecurity().buildProperties());
        System.out.println(kafkaProperties.getAdmin().getSsl().buildProperties());

        System.out.println(kafkaProperties.getListener().getAckCount());
        System.out.println(kafkaProperties.getListener().getAckMode());
        System.out.println(kafkaProperties.getListener().getAckTime());
        System.out.println(kafkaProperties.getListener().getClientId());
        System.out.println(kafkaProperties.getListener().getConcurrency());
        System.out.println(kafkaProperties.getListener().getIdleEventInterval());
        System.out.println(kafkaProperties.getListener().getMonitorInterval());
        System.out.println(kafkaProperties.getListener().getLogContainerConfig());
        System.out.println(kafkaProperties.getListener().getNoPollThreshold());
        System.out.println(kafkaProperties.getListener().getPollTimeout());
        System.out.println(kafkaProperties.getListener().getType());

        System.out.println(kafkaProperties.getProducer().getTransactionIdPrefix());
        System.out.println(kafkaProperties.getProducer().getAcks());
        System.out.println(kafkaProperties.getProducer().getBatchSize());
        System.out.println(kafkaProperties.getProducer().getBootstrapServers());
        System.out.println(kafkaProperties.getProducer().getBufferMemory());
        System.out.println(kafkaProperties.getProducer().getClientId());
        System.out.println(kafkaProperties.getProducer().getCompressionType());
        System.out.println(kafkaProperties.getProducer().getKeySerializer());
        System.out.println(kafkaProperties.getProducer().getProperties());
        System.out.println(kafkaProperties.getProducer().getRetries());
        System.out.println(kafkaProperties.getProducer().getSecurity().buildProperties());
        System.out.println(kafkaProperties.getProducer().getValueSerializer());
        System.out.println(kafkaProperties.getProducer().getSsl().buildProperties());


        System.out.println(kafkaProperties.getConsumer().getAutoCommitInterval());
        System.out.println(kafkaProperties.getConsumer().getAutoOffsetReset());
        System.out.println(kafkaProperties.getConsumer().getBootstrapServers());
        System.out.println(kafkaProperties.getConsumer().getClientId());
        System.out.println(kafkaProperties.getConsumer().getEnableAutoCommit());
        System.out.println(kafkaProperties.getConsumer().getFetchMaxWait());
        System.out.println(kafkaProperties.getConsumer().getFetchMinSize());
        System.out.println(kafkaProperties.getConsumer().getGroupId());
        System.out.println(kafkaProperties.getConsumer().getHeartbeatInterval());
        System.out.println(kafkaProperties.getConsumer().getIsolationLevel());
        System.out.println(kafkaProperties.getConsumer().getKeyDeserializer());
        System.out.println(kafkaProperties.getConsumer().getValueDeserializer());
        System.out.println(kafkaProperties.getConsumer().getSecurity().buildProperties());
        System.out.println(kafkaProperties.getConsumer().getSsl().buildProperties());
        System.out.println(kafkaProperties.getConsumer().getProperties());
        System.out.println(kafkaProperties.getConsumer().getMaxPollRecords());

    }
}
