package org.kimbs.kafka.consumer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReportConsumer {

    @KafkaListener(topics = "kbs", containerFactory = "kafkaListenerContainerFactory")
    public void consume(ConsumerRecords<String, String> list, Acknowledgment acknowledgment) throws Exception {

        list.forEach(record -> {
            log.info("[Consumed Message Info] Header: {}, Partition: {}, Offset: {}, Topic: {},  Key: {}, Value: {}", record.headers(), record.partition(), record.offset(), record.topic(), record.key(), record.value());
        });
        log.info("Consumed message size: {}", list.count());

        acknowledgment.acknowledge();
    }
}
