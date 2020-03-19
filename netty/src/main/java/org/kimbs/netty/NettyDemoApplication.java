package org.kimbs.netty;

import lombok.RequiredArgsConstructor;
import org.kimbs.netty.client.auth.AuthClient;
import org.kimbs.netty.client.config.ClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
@SpringBootApplication
@EnableConfigurationProperties(ClientConfig.class)
public class NettyDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NettyDemoApplication.class, args);
	}

	private final AuthClient authClient;
	private final ClientConfig clientConfig;

	@EventListener(ApplicationReadyEvent.class)
	public void init() throws Exception {
		authClient.connect(clientConfig.getAuthServer().getHost(), clientConfig.getAuthServer().getPort());
	}

}
