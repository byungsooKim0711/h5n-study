package org.kimbs.netty.client.report;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kimbs.netty.client.AbstractClient;
import org.kimbs.netty.client.auth.AuthSuccessEvent;
import org.kimbs.netty.client.config.ClientConfig;
import org.kimbs.netty.packet.Command;
import org.kimbs.netty.packet.Packet;
import org.kimbs.netty.packet.options.rs.ImcRsAuthReq;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReportClient extends AbstractClient {

    private final ReportInitializer reportInitializer;
    private final ClientConfig clientConfig;
    private final EventLoopGroup group = new NioEventLoopGroup(1);

    @EventListener
    public void onAuthSuccessEvent(AuthSuccessEvent event) throws Exception {
        this.connect(clientConfig.getImcAsAuthRes().getRsList().get(0).getRsHost(), clientConfig.getImcAsAuthRes().getRsList().get(0).getRsReportPort());
    }

    @Override
    protected void connect(String host, int port) throws Exception {
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(this.reportInitializer);

            log.info("[REPORT SERVER] Connection Host: {}, Port: {}", host, port);

            ChannelFuture future = b.connect(host, port).await();
            this.setChannelFuture(future);
            if (future.isSuccess()) {
                this.authRequest();
            } else {
                log.info("[REPORT CLIENT] Connection Fail, Retry[Host: {}, Port: {}]", host, port);
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
                .clientId("devimc")
                .authKey(clientConfig.getImcAsAuthRes().getAuthKey())
                .build();

        Packet<ImcRsAuthReq> packet = Packet.<ImcRsAuthReq>builder()
                .command(Command.IMC_RS_AUTH_REQ)
                .options(option)
                .build();

        getChannelFuture().channel().writeAndFlush(packet).sync();
    }

    @Override
    protected void sendMessage(String contents) throws Exception {
        // TODO:
    }
}
