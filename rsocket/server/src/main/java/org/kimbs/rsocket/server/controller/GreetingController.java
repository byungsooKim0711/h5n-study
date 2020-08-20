package org.kimbs.rsocket.server.controller;

import org.kimbs.rsocket.protocol.GreetingRequest;
import org.kimbs.rsocket.protocol.GreetingResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@Controller
public class GreetingController {

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
