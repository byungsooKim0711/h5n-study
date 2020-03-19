package org.kimbs.netty;

import org.kimbs.netty.client.auth.AuthClient;
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
	private AuthClient authClient;

	@EventListener(ApplicationReadyEvent.class)
	public void init() throws Exception {
		authClient.connect("119.207.76.90", 32000);
	}

}
