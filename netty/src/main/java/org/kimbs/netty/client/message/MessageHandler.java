package org.kimbs.netty.client.message;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.kimbs.netty.packet.Packet;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageHandler extends SimpleChannelInboundHandler<Packet> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        log.info("[CHANNEL READ0] CTX: {}, PACKET: {}", ctx, packet);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("[CHANNEL REGISTERED] CTX: {}", ctx);
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("[CHANNEL UNREGISTERED] CTX: {}", ctx);
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("[CHANNEL ACTIVE] CTX: {}", ctx);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("[CHANNEL INACTIVE] CTX: {}", ctx);
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("[CHANNEL EXCEPTION CAUGHT] CTX: {}", ctx, cause);
        super.exceptionCaught(ctx, cause);
    }
}
