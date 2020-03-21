package org.kimbs.netty;

import org.kimbs.netty.client.auth.event.AuthFailureEvent;
import org.kimbs.netty.client.config.ClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableConfigurationProperties(ClientConfig.class)
public class NettyDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NettyDemoApplication.class, args);
	}

	@EventListener
	public void onShutdownEvent(AuthFailureEvent event) {
		// TODO: Catch Event, Shutdown Application ...
	}

}
