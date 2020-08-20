package org.kimbs.rsocket.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class RsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(RsocketApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

    }
}