package org.kimbs.uracker.handler;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.stereotype.Component;

@Component
public class CommonKafkaErrorHandler implements ErrorHandler {

    @Override
    public void handle(Exception e, ConsumerRecord<?, ?> consumerRecord) {

    }
}
