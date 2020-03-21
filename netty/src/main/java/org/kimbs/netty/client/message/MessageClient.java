package org.kimbs.netty.client.message;

import lombok.RequiredArgsConstructor;
import org.kimbs.netty.client.AbstractClient;
import org.kimbs.netty.client.auth.event.AuthSuccessEvent;
import org.kimbs.netty.packet.Command;
import org.kimbs.netty.packet.Packet;
import org.kimbs.netty.packet.options.as.ImcAsAuthRes;
import org.kimbs.netty.packet.options.rs.*;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MessageClient extends AbstractClient {

    private final MessageInitializer messageInitializer;
    private ImcAsAuthRes authRes;

    @Override
    @EventListener(AuthSuccessEvent.class)
    public void onEventListener(ApplicationEvent event) throws Exception {
        this.authRes = (ImcAsAuthRes) event.getSource();
        super.connect(authRes.getRsList().get(0).getRsHost(), authRes.getRsList().get(0).getRsSendPort(), messageInitializer);
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

        this.sendPacket(packet).sync();
    }


    public void atSendMessage(String contents) throws Exception {
        ImcRsAtPushReq request = ImcRsAtPushReq.builder()
                .reqUid(UUID.randomUUID().toString())
                .senderKey("289ec651a6f90359068ed3a9c0a03dd0e607b0a4")
                .build();

        ImcRsAtPushOption option1 = new ImcRsAtPushOption();
        option1.setMsgUid(UUID.randomUUID().toString().substring(0, 10));
        option1.setTemplateCode("A_HO_019_02_14613");
        option1.setPhoneNumber("821049492891");
        option1.setContents("안녕하세요 휴머스온 매장입니다. 일시적으로 주문이 급증하여 배달이 지연되고 있습니다. 최대한 빠른 시간 내에 주문하신 제품이 도착할 수 있도록 준비하겠습니다. 불편함을 드려 죄송합니다. 감사합니다.");
        option1.setResendType("NO");

        List<ImcRsAtPushOption> options = request.getAtReqList();
        options.add(option1);

        Packet<ImcRsAtPushReq> packet = Packet.<ImcRsAtPushReq>builder()
                .command(Command.IMC_RS_AT_PUSH_REQ)
                .options(request)
                .build();

        super.sendPacket(packet).sync();
    }

    public void ftSendMessage(String message) throws Exception {
        ImcRsFtPushReq request = ImcRsFtPushReq.builder()
                .reqUid(UUID.randomUUID().toString())
                .senderKey("289ec651a6f90359068ed3a9c0a03dd0e607b0a4")
                .build();

        ImcRsFtPushOption option1 = new ImcRsFtPushOption();
        option1.setMsgUid(UUID.randomUUID().toString().substring(0, 10));
        option1.setPhoneNumber("821049492891");
        option1.setContents(message);

        List<ImcRsFtPushOption> options = request.getFtReqList();
        options.add(option1);

        Packet<ImcRsFtPushReq> packet = Packet.<ImcRsFtPushReq>builder()
                .command(Command.IMC_RS_FT_PUSH_REQ)
                .options(request)
                .build();

        super.sendPacket(packet).sync();
    }
}
