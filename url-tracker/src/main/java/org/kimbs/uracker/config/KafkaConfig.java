package org.kimbs.uracker.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.kimbs.uracker.handler.CommonKafkaBatchErrorHandler;
import org.kimbs.uracker.handler.CommonKafkaErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.producer.retries}")
    private int producerRetries = 0;

    @Value("${spring.kafka.producer.batch-size}")
    private int producerBatchSize = 20000;

    @Value("${spring.kafka.producer.compression-type}")
    private String producerCompressionType = "gzip";

    @Value("${spring.kafka.producer.linger-ms}")
    private long producerLingerMs = 50;

    @Value("${spring.kafka.producer.buffer-memory}")
    private long producerBufferMemory = 335544320;

    @Value("${spring.kafka.producer.key-serializer}")
    private String producerKeySerializer = "org.apache.kafka.common.serialization.StringSerializer";

    @Value("${spring.kafka.producer.value-serializer}")
    private String producerValueSerializer = "org.apache.kafka.common.serialization.StringSerializer";

    @Value("${spring.kafka.consumer.group-id}")
    private String consumerGroupId;

    @Value("${spring.kafka.consumer.enable-auto-commit}")
    private boolean consumerEnableAutoCommit = false;

    @Value("${spring.kafka.consumer.max-poll-records}")
    private int consumerMaxPollRecords = 500;

    @Value("${spring.kafka.consumer.key-deserializer}")
    private String consumerKeyDeserializer = "org.apache.kafka.common.serialization.StringDeserializer";

    @Value("${spring.kafka.consumer.value-deserializer}")
    private String consumerValueDeserializer = "org.apache.kafka.common.serialization.StringDeserializer";

    @Value("${spring.kafka.listener.concurrency}")
    private int listenerConcurrency = 5;

    @Value("${spring.kafka.listener.ack-mode}")
    private String listenerAckMode = "MANUAL_IMMEDIATE";

    @Value("${spring.kafka.listener.poll-timeout}")
    private int listenerPollTimeout = 3000;

    @Value("${spring.kafka.listener.batch}")
    private boolean listenerBatch;

    @Value("${spring.kafka.listener.auto-startup}")
    private boolean listenerAutoStartup;

    @Value("${spring.kafka.retry.max-attempts}")
    private int retryMaxAttempts;
    @Value("${spring.kafka.retry.initial-interval}")
    private int retryInitialInterval;
    @Value("${spring.kafka.retry.multiplier}")
    private int retryMultiplier;
    @Value("${spring.kafka.retry.max-interval}")
    private int retryMaxInterval;

    private final CommonKafkaErrorHandler commonErrorHandler;
    private final CommonKafkaBatchErrorHandler commonBatchErrorHandler;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setBatchListener(listenerBatch);

        // Container Error Handlers 사용시 false 설정
        factory.getContainerProperties().setAckOnError(false);

        if (listenerBatch) {
            // batch listener 를 사용할 경우, kafka에서 retry 를 지원하지 않음
            // TODO: RetryingBatchErrorHandler 를 사용하면?

            // batch error handler
            factory.setBatchErrorHandler(commonBatchErrorHandler);
        } else {
            factory.setRetryTemplate(retryTemplate());

            // error handler
            factory.setErrorHandler(commonErrorHandler);
        }

        factory.setAutoStartup(listenerAutoStartup);
        return factory;
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.RETRIES_CONFIG, producerRetries);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, producerBatchSize);
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, producerCompressionType);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producerKeySerializer);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerValueSerializer);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, producerBufferMemory);
        props.put(ProducerConfig.LINGER_MS_CONFIG, producerLingerMs);

        return props;
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, consumerEnableAutoCommit);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, consumerMaxPollRecords);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumerKeyDeserializer);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumerValueDeserializer);

        return props;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public ConsumerFactory<Object, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    protected RetryPolicy retryPolicy() {
        SimpleRetryPolicy policy = new SimpleRetryPolicy();
        policy.setMaxAttempts(retryMaxAttempts);
        return policy;
    }

    protected BackOffPolicy backOffPolicy() {
        ExponentialBackOffPolicy policy = new ExponentialBackOffPolicy();
        policy.setInitialInterval(retryInitialInterval);
        policy.setMultiplier(retryMultiplier);
        policy.setMaxInterval(retryMaxInterval);
        return policy;
    }

    protected RetryTemplate retryTemplate() {
        RetryTemplate template = new RetryTemplate();

        template.setRetryPolicy(retryPolicy());
        template.setBackOffPolicy(backOffPolicy());

        return template;
    }
}
