package org.kimbs.netty.client.message;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kimbs.netty.client.AbstractClient;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageClient extends AbstractClient {

    private final MessageInitializer messageInitializer;

    @Override
//    @EventListener // TODO: AuthClient 성공 Event 발생시키면 connect 메서드 실행
    public void connect(String host, int port) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(this.messageInitializer);

            log.info("[MESSAGE SERVER] Connection Host: {}, Port: {}", host, port);

            ChannelFuture future = b.connect(host, port).await();
            this.setChannelFuture(future);
            if (future.isSuccess()) {
//                this.authRequest();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() throws Exception {

    }
}
