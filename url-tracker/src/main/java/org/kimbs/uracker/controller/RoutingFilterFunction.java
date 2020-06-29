package org.kimbs.uracker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;

@Slf4j
@Component
public class RoutingFilterFunction implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    @Override
    public Mono<ServerResponse> filter(ServerRequest serverRequest, HandlerFunction<ServerResponse> next) {
        log.info("HTTP METHOD: {}, URL PATTERN: {}, REQUEST AT: {}",
                serverRequest.method(),
                serverRequest.attribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).orElse("Unknown"),
                ZonedDateTime.now().toString());
        return next.handle(serverRequest);
    }
}
