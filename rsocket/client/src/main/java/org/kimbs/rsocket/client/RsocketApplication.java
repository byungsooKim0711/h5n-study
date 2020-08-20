package org.kimbs.rsocket.client;

import io.rsocket.SocketAcceptor;
import io.rsocket.metadata.WellKnownMimeType;
import lombok.extern.slf4j.Slf4j;
import org.kimbs.rsocket.client.controller.HealthController;
import org.kimbs.rsocket.protocol.GreetingResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.security.rsocket.metadata.SimpleAuthenticationEncoder;
import org.springframework.security.rsocket.metadata.UsernamePasswordMetadata;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Mono;

@Slf4j
@SpringBootApplication
public class RsocketApplication {

    private final UsernamePasswordMetadata credentials = new UsernamePasswordMetadata("kimbs", "kimbs");
    private final MimeType mimeType = MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.getString());

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RsocketApplication.class, args);
        System.in.read();
    }

    @Bean
    SocketAcceptor socketAcceptor(RSocketStrategies strategies, HealthController healthController) {
        return RSocketMessageHandler.responder(strategies, healthController);
    }

    @Bean
    RSocketRequester rSocketRequester(RSocketRequester.Builder builder, SocketAcceptor socketAcceptor) {
        return builder
                .setupMetadata(this.credentials, this.mimeType)
                .rsocketConnector(connector -> connector.acceptor(socketAcceptor))
                .connectTcp("localhost", 8888)
                .block();
    }

    @Bean
    RSocketStrategiesCustomizer rSocketStrategiesCustomizer() {
        return strategies -> strategies.encoder(new SimpleAuthenticationEncoder());
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> client(RSocketRequester client) {
        return arguments -> {
            client.route("greetings")
                    .metadata(this.credentials, this.mimeType)
                    .data(Mono.empty())
                    .retrieveFlux(GreetingResponse.class)
            .subscribe(data -> log.info("Subscribe data. message: {}", data.getMessage()));
        };
    }
}


