package org.kimbs.uracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class UrackerRouter implements WebFluxConfigurer {

    @Bean
    public RouterFunction<ServerResponse> customerRouterFunction(UrackerHandler urackerHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), urackerHandler::getUrl)
                .andRoute(RequestPredicates.POST("/").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), urackerHandler::postUrl);
    }
}