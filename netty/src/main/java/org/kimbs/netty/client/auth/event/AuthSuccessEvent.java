package org.kimbs.netty.client.auth.event;

import lombok.Getter;
import lombok.ToString;
import org.kimbs.netty.packet.options.as.ImcAsAuthRes;
import org.springframework.context.ApplicationEvent;

@ToString
@Getter
public class AuthSuccessEvent extends ApplicationEvent {

    private static final long serialVersionUID = 6748935287735795648L;

    public AuthSuccessEvent(ImcAsAuthRes source) {
        super(source);
    }
}
