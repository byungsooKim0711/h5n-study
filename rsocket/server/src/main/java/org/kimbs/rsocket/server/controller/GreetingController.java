package org.kimbs.rsocket.server.controller;

import org.kimbs.rsocket.protocol.ClientHealthState;
import org.kimbs.rsocket.protocol.GreetingRequest;
import org.kimbs.rsocket.protocol.GreetingResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    Flux<GreetingResponse> greet(RSocketRequester connection, @AuthenticationPrincipal Mono<UserDetails> user) {

        return user.map(UserDetails::getUsername)
                .map(GreetingRequest::new)
                .flatMapMany(gr -> this.greet(connection, gr));
    }

    private Flux<GreetingResponse> greet(RSocketRequester connection, GreetingRequest request) {
        Stream<GreetingResponse> stream = Stream.generate(() -> new GreetingResponse("Client: " + request.getName() + " @ " + Instant.now() + "!"));

        Flux<ClientHealthState> in = connection.route("health")
                .retrieveFlux(ClientHealthState.class)
                .filter(chs -> !chs.isHealthy()); // ClientHealthState#healthy == false: NotEmpty;

        Flux<GreetingResponse> out = Flux.fromStream(stream)
                .take(100)
                .delayElements(Duration.ofSeconds(1));

        return out.takeUntilOther(in);
    }
}
