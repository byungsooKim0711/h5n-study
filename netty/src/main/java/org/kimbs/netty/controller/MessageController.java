package org.kimbs.netty.controller;

import lombok.RequiredArgsConstructor;
import org.kimbs.netty.client.message.MessageClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    // 단순한 데모전용 Event Trigger.... 고정 내용 전송

    private final MessageClient messageClient;

    @GetMapping("/message/at")
    public void sendAtMessage() throws Exception {
        messageClient.atSendMessage();
    }

    @GetMapping("/message/ft/{message}")
    public void sendFtMessage(@PathVariable("message") String message) throws Exception {
        messageClient.ftSendMessage(message);
    }
}
