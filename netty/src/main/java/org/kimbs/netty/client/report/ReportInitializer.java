package org.kimbs.netty.client.report;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.RequiredArgsConstructor;
import org.kimbs.netty.client.decoder.MessageDecoder;
import org.kimbs.netty.client.encoder.MessageEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportInitializer extends ChannelInitializer<SocketChannel> {

    private final ReportHandler reportHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        // decoder, encoder, handler
        socketChannel.pipeline().addLast(new MessageDecoder());
        socketChannel.pipeline().addLast(new MessageEncoder());
        socketChannel.pipeline().addLast(this.reportHandler);
    }
}
