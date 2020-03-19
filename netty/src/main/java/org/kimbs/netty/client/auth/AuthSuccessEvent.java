package org.kimbs.netty.client.auth;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@ToString
@Getter
public class AuthSuccessEvent extends ApplicationEvent {

    public AuthSuccessEvent(Object source) {
        super(source);
    }
}
