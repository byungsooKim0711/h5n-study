package org.kimbs.batch.event;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Getter
@ToString
public class BatchStartEvent extends ApplicationEvent {

    public BatchStartEvent(Object source) {
        super(source);
    }
}
