package org.kimbs.netty.client.auth.event;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@ToString
@Getter
public class AuthFailureEvent extends ApplicationEvent {

    private static final long serialVersionUID = 6280712335496686663L;

    public AuthFailureEvent(Object source) {
        super(source);
    }
}
