package org.kimbs.kafka.producer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private final MessageSender messageSender;

    public MessageController(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @GetMapping("/send/async/{count}")
    public void asyncSend(@PathVariable("count") int count) {
        messageSender.asyncSend(count);
    }

    @GetMapping("/send/sync/{count}")
    public void syncSend(@PathVariable("count") int count) {
        messageSender.syncSend(count);
    }
}
