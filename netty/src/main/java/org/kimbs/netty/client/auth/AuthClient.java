package org.kimbs.netty.client.auth;

import lombok.RequiredArgsConstructor;
import org.kimbs.netty.client.AbstractClient;
import org.kimbs.netty.packet.Command;
import org.kimbs.netty.packet.Packet;
import org.kimbs.netty.packet.options.as.ImcAsAuthReq;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthClient extends AbstractClient {

    private final AuthInitializer authInitializer;

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void onEventListener(ApplicationEvent event) throws Exception {
        super.connect(clientConfig.getAuthServer().getHost(), clientConfig.getAuthServer().getPort(), authInitializer);
    }

    @Override
    protected void authRequestHook() throws Exception {
        ImcAsAuthReq option = ImcAsAuthReq.builder()
                .clientId(clientConfig.getAuthServer().getId())
                .clientPassword(clientConfig.getAuthServer().getPassword())
                .build();

        Packet<ImcAsAuthReq> packet = Packet.<ImcAsAuthReq>builder()
                .command(Command.IMC_AS_AUTH_REQ)
                .options(option)
                .build();

        super.sendPacket(packet).sync();
    }
}
