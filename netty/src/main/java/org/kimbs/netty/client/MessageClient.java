package org.kimbs.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.stereotype.Component;

@Component
public class MessageClient {

    private ChannelFuture channelFuture;

    public void connect(String host, int port) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new MessageHandler());
            this.channelFuture = b.connect(host, port).sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public void sendMessage(String message) {
        ChannelFuture c = channelFuture.channel().writeAndFlush(message);
        if (c.isSuccess()) {
            System.out.println("성공성공성공");
        } else {
            System.out.println("실패실패실패");
        }
    }

    public void disconnect() throws Exception {
        this.channelFuture.channel().closeFuture().sync();
    }
}
