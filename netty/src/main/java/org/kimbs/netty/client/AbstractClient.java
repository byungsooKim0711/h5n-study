package org.kimbs.netty.client;

import io.netty.channel.ChannelFuture;
import org.kimbs.netty.client.config.ClientConfig;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractClient {

    private ChannelFuture channelFuture;

    @Autowired
    protected ClientConfig clientConfig;

    protected abstract void connect(String host, int port) throws Exception;
    protected void disconnect() throws Exception {
        this.channelFuture.channel().closeFuture().sync();
    };

    protected abstract void authRequest() throws Exception;

    protected void setChannelFuture(ChannelFuture channelFuture) {
        this.channelFuture = channelFuture;
    }
    protected ChannelFuture getChannelFuture() {
        return this.channelFuture;
    }

    protected abstract void sendMessage(String contents) throws Exception;
}
