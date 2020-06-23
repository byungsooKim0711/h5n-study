package org.kimbs.uracker.handler;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.listener.BatchErrorHandler;
import org.springframework.stereotype.Component;

@Component
public class CommonKafkaBatchErrorHandler implements BatchErrorHandler {

    @Override
    public void handle(Exception e, ConsumerRecords<?, ?> consumerRecords) {

    }
}
