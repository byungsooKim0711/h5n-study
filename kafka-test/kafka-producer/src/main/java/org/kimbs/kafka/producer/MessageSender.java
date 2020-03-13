package org.kimbs.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class MessageSender {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public MessageSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public void asyncSend(int count) {
        for (int i=0; i<count; i++) {
            Message<String> message = MessageBuilder
                    .withPayload(""+i) // value
                    .setHeader(KafkaHeaders.TOPIC, "kbs") // topic
//					.setHeader(KafkaHeaders.MESSAGE_KEY, ""+i) // key 값이 null이면 topic의 partition에 RoundRobin 형태로 전송, null이 아니면 key값을 해싱해서 partition에 매칭되는 것에 전송
                    .setHeader("X-Custom-Header", "WC1DdXN0b20tSGVhZGVy") // custom header -> spring_json_header_types 는 자동으로 딸려오는 것인가?
                    .build();

            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(message);
            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    log.error("Send fail topic : {}", "kbs", throwable.getCause());
                }

                @Override
                public void onSuccess(SendResult<String, String> sendResult) {
                    ProducerRecord<String, String> record = sendResult.getProducerRecord();
                    RecordMetadata metaData = sendResult.getRecordMetadata();
                    log.info("[Send Message Info] Offset: {}, Topic: {}, Key: {}, Value: {}", metaData.offset(), record.topic(), record.key(), record.value());
                }
            });
        }
    }


    // Do not working - Required @Transactional
    public void syncSend(int count) {
        for (int i=0; i<count; i++) {
            Message<String> message = MessageBuilder
                    .withPayload("" + i) // value
                    .setHeader(KafkaHeaders.TOPIC, "kbs")
                    .setHeader("X-Custom-Header", "WC1DdXN0b20tSGVhZGVy") // custom header -> spring_json_header_types 는 자동으로 딸려오는 것인가?
                    .build();

            try {
                // future에서 get을 호출하는 순간 blocking
                SendResult<String, String> sendResult = kafkaTemplate.send(message).get(10, TimeUnit.SECONDS);
                ProducerRecord<String, String> record = sendResult.getProducerRecord();
                RecordMetadata metaData = sendResult.getRecordMetadata();
                log.info("[Send Message Info] Offset: {}, Topic: {}, Key: {}, Value: {}", metaData.offset(), record.topic(), record.key(), record.value());
            } catch (ExecutionException e) {
                log.error("[ExecutionException] ", e.getCause());
            } catch (TimeoutException | InterruptedException e) {
                log.error("[TimeoutException | InterruptedException] ", e.getCause());
            }
        }
    }
}
