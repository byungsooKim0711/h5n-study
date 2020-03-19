package org.kimbs.netty.controller;

import lombok.RequiredArgsConstructor;
import org.kimbs.netty.client.config.ClientConfig;
import org.kimbs.netty.client.message.MessageClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageClient messageClient;
    private final ClientConfig clientConfig;

    @GetMapping("/message/auth")
    public void auth() throws Exception {
        messageClient.connect(clientConfig.getImcAsAuthRes().getRsList().get(0).getRsHost(), clientConfig.getImcAsAuthRes().getRsList().get(0).getRsReportPort());
    }

    @GetMapping("/message/{message}")
    public String sendMessage(@PathVariable(name = "message") String message) throws Exception {
        return message;
    }
}
