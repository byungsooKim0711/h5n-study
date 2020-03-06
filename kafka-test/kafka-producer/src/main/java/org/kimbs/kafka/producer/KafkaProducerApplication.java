package org.kimbs.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
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
			ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("kbs-kafka", "" + i);
			future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
				@Override
				public void onFailure(Throwable throwable) {
					log.error("Send fail topic : {}", "kbs-kafka", throwable.getCause());
				}

				@Override
				public void onSuccess(SendResult<String, String> stringStringSendResult) {
					log.info("Send message : {}", stringStringSendResult.toString());
				}
			});
		}
	}

}
