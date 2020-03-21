package org.kimbs.netty;

import org.kimbs.netty.client.auth.event.AuthFailureEvent;
import org.kimbs.netty.client.config.ClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableConfigurationProperties(ClientConfig.class)
public class NettyDemoApplication {

	public static void main(String[] args) {
//		SpringApplication.run(NettyDemoApplication.class, args);

		SpringApplication application = new SpringApplicationBuilder()
				.sources(NettyDemoApplication.class)
				.listeners(new ApplicationPidFileWriter())
				.build();
		application.run(args);
	}

	@EventListener(AuthFailureEvent.class)
	public void onShutdownEvent() {
		// TODO: Catch AuthFailureEvent, Shutdown Application ...

	}
}