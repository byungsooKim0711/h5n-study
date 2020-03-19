package org.kimbs.netty.client.message;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageInitializer extends ChannelInitializer<SocketChannel> {

    private final MessageHandler messageHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(this.messageHandler);
    }
}
