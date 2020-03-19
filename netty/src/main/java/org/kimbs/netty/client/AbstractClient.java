package org.kimbs.netty.client;

import io.netty.channel.ChannelFuture;

public abstract class AbstractClient {

    private ChannelFuture channelFuture;

    public abstract void connect(String host, int port) throws Exception;

    public void disconnect() throws Exception {
        this.channelFuture.channel().closeFuture().sync();
    };

    public void setChannelFuture(ChannelFuture channelFuture) {
        this.channelFuture = channelFuture;
    }

    public ChannelFuture getChannelFuture() {
        return this.channelFuture;
    }
}
