package org.kimbs.uracker.controller;

import lombok.RequiredArgsConstructor;
import org.kimbs.uracker.service.UrackerService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class UrackerHandler {

    private final UrackerService urackerService;

    public Mono<ServerResponse> getUrl(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("테스트임당"));
    }

    public Mono<ServerResponse> postUrl(ServerRequest request) {
        return Mono.empty();
    }
}
