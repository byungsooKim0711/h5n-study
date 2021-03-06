package org.kimbs.netty.client.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kimbs.netty.code.ReturnCode;
import org.kimbs.netty.packet.Command;
import org.kimbs.netty.packet.Packet;
import org.kimbs.netty.packet.exception.UnknownCommandException;
import org.kimbs.netty.packet.options.rs.ImcRsAtPushRes;
import org.kimbs.netty.packet.options.rs.ImcRsAuthRes;
import org.kimbs.netty.packet.options.rs.ImcRsFtPushRes;
import org.springframework.stereotype.Component;

import static io.netty.channel.ChannelHandler.Sharable;

@Component
@RequiredArgsConstructor
@Slf4j
@Sharable
public class MessageHandler extends SimpleChannelInboundHandler<Packet<?>> {

    private final ObjectMapper mapper;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        log.info("[MESSAGE CHANNEL READ0] CTX: {}, PACKET: {}", ctx, packet);

        Command command = packet.getCommand();
        switch (command) {
            case IMC_RS_AUTH_RES:
                ImcRsAuthRes authRes = mapper.readValue(packet.getOptions().toString(), ImcRsAuthRes.class);

                if (authRes.getReturnCode().equals(ReturnCode.SUCCESS.getCode())) {
                    log.info("[Connection Success - MESSAGE] RETURN_CODE: {} ", authRes.getReturnCode());
                } else {
                    log.info("[Connection Fail - MESSAGE] RETURN_CODE: {}", authRes.getReturnCode());
                }
                break;
            case IMC_RS_AT_PUSH_RES:
                ImcRsAtPushRes atPushRes = mapper.readValue(packet.getOptions().toString(), ImcRsAtPushRes.class);
                if (atPushRes.getReturnCode().equals(ReturnCode.SUCCESS.getCode())) {
                    log.info("[AT MESSAGE SEND] SUCCESS: {}", atPushRes.getReturnCode());
                } else {
                    log.info("[AT MESSAGE SEND] FAIL: {}", atPushRes.getReturnCode());
                }
                break;
            case IMC_RS_FT_PUSH_RES:
                ImcRsFtPushRes ftPushRes = mapper.readValue(packet.getOptions().toString(), ImcRsFtPushRes.class);
                if (ReturnCode.SUCCESS.getCode().equals(ftPushRes.getReturnCode())) {
                    log.info("[FT MESSAGE SEND] SUCCESS: {}", ftPushRes.getReturnCode());
                } else {
                    log.info("[FT MESSAGE SEND] FAIL: {}", ftPushRes.getReturnCode());
                }
                break;

            default:
                throw new UnknownCommandException("UnKnown Command Exception With: " + command);
        }
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
