package org.kimbs.netty.client.auth;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.RequiredArgsConstructor;
import org.kimbs.netty.client.decoder.MessageDecoder;
import org.kimbs.netty.client.encoder.MessageEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthInitializer extends ChannelInitializer<SocketChannel> {

    private final AuthHandler authHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        // decoder, encoder, handler
        socketChannel.pipeline().addLast(new MessageDecoder());
        socketChannel.pipeline().addLast(new MessageEncoder());
        socketChannel.pipeline().addLast(authHandler);
    }
}
