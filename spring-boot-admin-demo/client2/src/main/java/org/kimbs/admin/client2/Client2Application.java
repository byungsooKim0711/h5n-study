package org.kimbs.admin.client2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Client2Application {

	public static void main(String[] args) {
		SpringApplication.run(Client2Application.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void init() throws Exception {

	}

}
