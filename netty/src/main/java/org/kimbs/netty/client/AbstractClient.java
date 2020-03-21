package org.kimbs.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.kimbs.netty.client.config.ClientConfig;
import org.kimbs.netty.packet.Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;

import javax.annotation.PreDestroy;

@Slf4j
public abstract class AbstractClient {

    private ChannelFuture channelFuture;
    private EventLoopGroup group = new NioEventLoopGroup(1);

    @Autowired
    protected ClientConfig clientConfig;

    // 각 클라이언트 간 Event 처리
    protected abstract void onEventListener(ApplicationEvent event) throws Exception;

    // 각 클라이언트 연결시도
    protected void connect(String host, int port, ChannelInitializer<SocketChannel> initializer) throws Exception {
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, clientConfig.getTimeoutMs())
                    .handler(initializer);

            log.info("[TRY CONNECTION] HOST: {}, PORT: {}", host, port);

            ChannelFuture future = b.connect(host, port).await();
            if (future.isSuccess()) {
                this.channelFuture = future;

                // 각 클라이언트에서 구현한 hook 메서드
                this.authRequestHook();
            } else {
                this.connect(host, port, initializer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    // Bean 죽을때 소켓 close
    @PreDestroy
    protected void disconnect() throws Exception {
        this.channelFuture.channel().closeFuture().sync();
    };

    // 각 클라이언트 마다 인증방식을 구현 (connect 메서드에서 후킹)
    protected abstract void authRequestHook() throws Exception;

    // 소켓통신은 Packet 을 만들어서 이 메서드로만 실행, 클라이언트 측에서 sync 혹은 await 결정
    protected ChannelFuture sendPacket(Packet<?> packet) throws Exception {
        return this.channelFuture.channel().writeAndFlush(packet);
    };
}
