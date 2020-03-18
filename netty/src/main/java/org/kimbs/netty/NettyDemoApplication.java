package org.kimbs.netty;

import org.kimbs.netty.client.MessageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class NettyDemoApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(NettyDemoApplication.class, args);
	}

	@Autowired
	private MessageClient messageClient;

	@EventListener(ApplicationReadyEvent.class)
	public void init() throws Exception {
		messageClient.connect("127.0.0.1", 9000);
//		messageClient.disconnect();
	}

}
