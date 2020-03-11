package org.kimbs.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Slf4j
@RestController
public class KafkaProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);
	}

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@GetMapping("/send")
	public void sendMessage() {
		for (int i=0; i<1000; i++) {
			Message<String> message = MessageBuilder
					.withPayload(""+i) // value
					.setHeader(KafkaHeaders.TOPIC, "kbs") // topic
//					.setHeader(KafkaHeaders.MESSAGE_KEY, ""+i) // key 값이 동일하면 여러개의 컨슈머가 있어도 한쪽에서만 컨슘함..? 다르거나, null이면 여러 컨슈머가 컨슘함
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

}
