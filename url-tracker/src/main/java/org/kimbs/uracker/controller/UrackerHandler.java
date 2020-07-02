package org.kimbs.uracker.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kimbs.uracker.model.UrackerError;
import org.kimbs.uracker.model.UrackerTarget;
import org.kimbs.uracker.service.UrackerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RequiredArgsConstructor
@Component
public class UrackerHandler {

    private final UrackerService urackerService;

    public Mono<ServerResponse> getUrl(ServerRequest request) {

        UrackerTarget target = null;

        String id = request.pathVariable("id");

        try {
            target = urackerService.getUrackerTarget(id);
        } catch (Exception e) {
            UrackerError error = UrackerError.builder()
                    .message("server error.")
                    .build();

            log.error("server error. {}", error, e);

            return ServerResponse
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(error, UrackerError.class);
        }

        if (target == null) {
            UrackerError error = UrackerError.builder()
                    .field("id")
                    .value(id)
                    .message("No such key exists")
                    .build();

            log.error("request error. {}", error);

            return ServerResponse
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(error, UrackerError.class);
        }

        log.info("[GET] id: {}, agentId: {}, serialNumber: {}", id, target.getAgentId(), target.getSn());

        URI redirectUri = null;
        try {
            redirectUri = new URI(target.getUrl());
        } catch (URISyntaxException e) {
            UrackerError error = UrackerError.builder()
                    .field("url")
                    .value(target.getUrl())
                    .message("URI Syntax Exception")
                    .build();

            log.error("request error. {}", error);

            return ServerResponse
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(error, UrackerError.class);
        }

        return ServerResponse
                .seeOther(redirectUri)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    public Mono<ServerResponse> postUrl(ServerRequest request) {
        // TODO: Apache UrlValidator 말고 스프링에서 제공하는게 있는지 확인 없으면 apache꺼 사용
//        UrlValidator validator = new UrlValidator(new String[] { "http", "https" });


        // TODO: 일단 block
        Mono<UrackerTarget> targetMono = request.body(BodyExtractors.toMono(UrackerTarget.class));
        UrackerTarget target = targetMono.block();

//        if (!validator.isValid(target.getUrl())) {
//            UrackerError error = UrackerError.builder()
//                    .field("url")
//                    .value(target.getUrl())
//                    .message("Invalid URL")
//                    .build();
//
//            log.error("request error. {}", error);
//
//            return ServerResponse
//                    .badRequest()
//                    .body(error, UrackerError.class);
//        }

        try {
            String id = urackerService.setTinyId(target);
            log.info("[SET] id: {}, target: {}", id, target);
        } catch (Exception e) {
            UrackerError error = UrackerError.builder()
                    .value(target.getUrl())
                    .build();

            log.error("server error. {}", error);

            return ServerResponse
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(error, UrackerError.class);
        }

        return ServerResponse
                .ok()
                .body(target, UrackerTarget.class);
    }
}
