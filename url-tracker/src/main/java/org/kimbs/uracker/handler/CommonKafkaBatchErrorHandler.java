package org.kimbs.uracker.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.listener.BatchErrorHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
//public class CommonKafkaBatchErrorHandler extends RetryingBatchErrorHandler {
public class CommonKafkaBatchErrorHandler implements BatchErrorHandler {

    @Override
    public void handle(Exception thrownException, ConsumerRecords<?, ?> records) {
        for (TopicPartition partition : records.partitions()) {
            for (ConsumerRecord<?, ?> record : records.records(partition)) {
                String value = (String) record.value();
                log.error("error received message='{}' with partition-offset='{}', key='{}'", value, partition, record.key());
            }
        }
    }


}
