package org.kimbs.reactor.netty.client.auth;

import lombok.extern.slf4j.Slf4j;
import org.kimbs.reactor.netty.client.AbstractClient;
import org.kimbs.reactor.netty.packet.Command;
import org.kimbs.reactor.netty.packet.Packet;
import org.kimbs.reactor.netty.packet.options.as.ImcAsAuthReq;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthorizeClient extends AbstractClient {

    @Override
    @EventListener(ApplicationReadyEvent.class)
    protected void onEventListener(ApplicationEvent event) throws Exception {
        super.connect("127.0.0.1", 12345);
    }

    @Override
    protected void authRequestHook() throws Exception {
        ImcAsAuthReq option = ImcAsAuthReq.builder()
                .clientId("dev")
                .clientPassword("dev")
                .build();

        Packet<ImcAsAuthReq> packet = Packet.<ImcAsAuthReq>builder()
                .command(Command.IMC_AS_AUTH_REQ)
                .options(option)
                .build();

        log.info("[AUTHORIZE REQUEST]");

    }
}
