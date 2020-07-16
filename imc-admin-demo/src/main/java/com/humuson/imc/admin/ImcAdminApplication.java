package com.humuson.imc.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ImcAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImcAdminApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

    }

}