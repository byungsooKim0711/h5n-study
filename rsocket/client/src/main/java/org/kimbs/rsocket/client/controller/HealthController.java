package org.kimbs.rsocket.client.controller;

import org.kimbs.rsocket.protocol.ClientHealthState;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@Controller
public class HealthController {

    @MessageMapping("health")
    Flux<ClientHealthState> health() {
        Stream<ClientHealthState> stream = Stream.generate(() -> new ClientHealthState(Math.random() > .2));

        return Flux.fromStream(stream)
                .delayElements(Duration.ofSeconds(1));
    }
}
