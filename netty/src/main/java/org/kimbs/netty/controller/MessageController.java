package org.kimbs.netty.controller;

import lombok.RequiredArgsConstructor;
import org.kimbs.netty.client.message.MessageClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageClient messageClient;

    @GetMapping("/message/at/{message}")
    public String sendAtMessage(@PathVariable(name = "message") String message) throws Exception {
        messageClient.sendMessage(message);
        return message;
    }

    @GetMapping("/message/ft/{message}")
    public String sendFtMessage(@PathVariable(name = "message") String message) throws Exception {

        return message;
    }
}
