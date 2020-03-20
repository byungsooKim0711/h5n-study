package org.kimbs.netty.client.message;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kimbs.netty.client.AbstractClient;
import org.kimbs.netty.client.auth.AuthSuccessEvent;
import org.kimbs.netty.packet.Command;
import org.kimbs.netty.packet.Packet;
import org.kimbs.netty.packet.options.rs.ImcRsAtPushOption;
import org.kimbs.netty.packet.options.rs.ImcRsAtPushReq;
import org.kimbs.netty.packet.options.rs.ImcRsAuthReq;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageClient extends AbstractClient {

    private final MessageInitializer messageInitializer;
    private EventLoopGroup group = new NioEventLoopGroup(1);

    @EventListener
    public void onAuthSuccessEvent(AuthSuccessEvent event) throws Exception {
        this.connect(clientConfig.getImcAsAuthRes().getRsList().get(0).getRsHost(), clientConfig.getImcAsAuthRes().getRsList().get(0).getRsSendPort());
    }

    @Override
    protected void connect(String host, int port) throws Exception {
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(this.messageInitializer);

            log.info("[MESSAGE SERVER] Connection Host: {}, Port: {}", host, port);

            ChannelFuture future = b.connect(host, port).await();
            if (future.isSuccess()) {
                this.setChannelFuture(future);
                this.authRequest();
            } else {
                log.info("[MESSAGE CLIENT] Connection Fail, Retry[Host: {}, Port: {}]", host, port);
                this.connect(host, port);
            }
        } catch (Exception e) {
            // TODO: ERROR Handling
            e.printStackTrace();
        }
    }

    @Override
    protected void authRequest() throws Exception {
        ImcRsAuthReq option = ImcRsAuthReq.builder()
                .clientId(clientConfig.getId())
                .authKey(clientConfig.getImcAsAuthRes().getAuthKey())
                .build();

        Packet<ImcRsAuthReq> packet = Packet.<ImcRsAuthReq>builder()
                .command(Command.IMC_RS_AUTH_REQ)
                .options(option)
                .build();

        getChannelFuture().channel().writeAndFlush(packet).sync();
    }

    @Override
    public void sendMessage(String contents) throws Exception {

        ImcRsAtPushReq request = new ImcRsAtPushReq();
        request.setReqUid(UUID.randomUUID().toString());
        request.setSenderKey("289ec651a6f90359068ed3a9c0a03dd0e607b0a4");

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

        ChannelFuture future = getChannelFuture().channel().writeAndFlush(packet).sync();
    }
}
