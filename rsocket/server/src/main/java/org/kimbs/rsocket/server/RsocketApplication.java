package org.kimbs.rsocket.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@SpringBootApplication
public class RsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(RsocketApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

    }
}


@AllArgsConstructor
@NoArgsConstructor
@Data
class GreetingResponse {
    private String message;
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class GreetingRequest {
    private String name;
}

@Controller
class GreetingController {

    /**
     * fire-and-forget
     * request-response
     * request-stream
     * channel
     */

    @MessageMapping("greetings")
    Flux<GreetingResponse> greet(GreetingRequest request) {
        Stream<GreetingResponse> stream = Stream.generate(() -> new GreetingResponse("Kimbs " + request.getName() + " @ " + Instant.now() + "!"));

        return Flux.fromStream(stream)
                .delayElements(Duration.ofSeconds(1));
    }
}