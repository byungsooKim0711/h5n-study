package org.kimbs.uracker.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommonKafkaErrorHandler implements ErrorHandler {

    @Override
    public void handle(Exception thrownException, ConsumerRecord<?, ?> record) {
        String value = (String) record.value();
        int partition = record.partition();

        log.error("error received message='{}' with partition-offset='{}', key='{}'", value, partition, record.key());
    }
}
