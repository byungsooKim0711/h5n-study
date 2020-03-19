package org.kimbs.netty.client.auth;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kimbs.netty.client.AbstractClient;
import org.kimbs.netty.packet.Command;
import org.kimbs.netty.packet.Packet;
import org.kimbs.netty.packet.options.as.ImcAsAuthReq;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthClient extends AbstractClient {

    private final AuthInitializer authInitializer;
    private EventLoopGroup group = new NioEventLoopGroup(1);

    public void connect(String host, int port) throws Exception {
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(authInitializer);

            log.info("[AUTH SERVER] Connection Host: {}, Port: {}", host, port);

            ChannelFuture future = b.connect(host, port).await();
            if (future.isSuccess()) {
                this.setChannelFuture(future);
                this.authRequest();
            } else {
                log.info("[AUTH SERVER] Connection Fail, Retry[Host: {}, Port: {}]", host, port);
                this.connect(host, port);
            }
        } catch (Exception e) {
            // TODO: ERROR Handling
            e.printStackTrace();
        }
    }

    @Override
    protected void authRequest() throws Exception {
        ImcAsAuthReq option = ImcAsAuthReq.builder()
                .clientId("devimc")
                .clientPassword("pwdevimc")
                .build();

        Packet<ImcAsAuthReq> packet = Packet.<ImcAsAuthReq>builder()
                .command(Command.IMC_AS_AUTH_REQ)
                .options(option)
                .build();

        getChannelFuture().channel().writeAndFlush(packet).sync();
    }

    @Override
    protected void sendMessage(String contents) throws Exception {

    }

}
