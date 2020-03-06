package org.kimbs.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

import java.util.List;

@SpringBootApplication
@Slf4j
public class KafkaConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerApplication.class, args);
	}

	@KafkaListener(topics = "kbs-kafka", containerFactory = "kafkaListenerContainerFactory")
	public void consume(List<ConsumerRecord<String, String>> list, Acknowledgment acknowledgment) throws Exception {
		log.info("Consumed message size : {}, Message info : {}", list.size(), list.toString());

		acknowledgment.acknowledge();
	}
}
