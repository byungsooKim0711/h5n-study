package org.kimbs.netty.controller;

import lombok.RequiredArgsConstructor;
import org.kimbs.netty.client.MessageClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageClient messageClient;


    @GetMapping("/message/{message}")
    public String sendMessage(@PathVariable(name = "message") String message) {
        messageClient.sendMessage(message);
        return message;
    }
}
