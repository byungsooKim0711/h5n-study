package org.kimbs.netty.client.report;

import lombok.RequiredArgsConstructor;
import org.kimbs.netty.client.AbstractClient;
import org.kimbs.netty.client.auth.event.AuthSuccessEvent;
import org.kimbs.netty.packet.Command;
import org.kimbs.netty.packet.Packet;
import org.kimbs.netty.packet.options.as.ImcAsAuthRes;
import org.kimbs.netty.packet.options.rs.ImcRsAuthReq;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportClient extends AbstractClient {

    private final ReportInitializer reportInitializer;
    private ImcAsAuthRes authRes;

    @Override
    @EventListener(AuthSuccessEvent.class)
    public void onEventListener(ApplicationEvent event) throws Exception {
        this.authRes = (ImcAsAuthRes) event.getSource();
        super.connect(authRes.getRsList().get(0).getRsHost(), authRes.getRsList().get(0).getRsReportPort(), reportInitializer);
    }

    @Override
    protected void authRequestHook() throws Exception {
        ImcRsAuthReq option = ImcRsAuthReq.builder()
                .clientId(clientConfig.getAuthServer().getId())
                .authKey(this.authRes.getAuthKey())
                .build();

        Packet<ImcRsAuthReq> packet = Packet.<ImcRsAuthReq>builder()
                .command(Command.IMC_RS_AUTH_REQ)
                .options(option)
                .build();

        super.sendPacket(packet).sync();
    }
}
