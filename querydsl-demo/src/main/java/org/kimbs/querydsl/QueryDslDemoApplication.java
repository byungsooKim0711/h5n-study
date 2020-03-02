package org.kimbs.querydsl;

import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class QueryDslDemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ac = SpringApplication.run(QueryDslDemoApplication.class, args);
		ac.publishEvent(new MyEventObject(ac, "My custom event"));
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		System.out.println("ApplicationReadyEvent!!");
	}

	@EventListener
	public void onCustomEvent(MyEventObject event) {
		System.out.println("Custom event: " + event);
	}


	@Getter
	@ToString
	static class MyEventObject extends ApplicationEvent {
		private String message;
		public MyEventObject(Object source, String message) {
			super(source);
			this.message = message;
		}
	}

}
