package org.kimbs.batch.event.publisher;

import lombok.RequiredArgsConstructor;
import org.kimbs.batch.event.BatchStartEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Scheduled(cron = "*/10 * * * * *")
    public void scheduled() {
        applicationEventPublisher.publishEvent(new BatchStartEvent("START"));
    }
}
