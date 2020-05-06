package com.humuson.imc.crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ImcCrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImcCrawlerApplication.class, args);
	}
}
