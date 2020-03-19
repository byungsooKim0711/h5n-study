package org.kimbs.netty.client.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kimbs.netty.code.ReturnCode;
import org.kimbs.netty.packet.Command;
import org.kimbs.netty.packet.Packet;
import org.kimbs.netty.packet.options.rs.ImcRsAuthRes;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@ChannelHandler.Sharable
public class ReportHandler extends SimpleChannelInboundHandler<Packet> {

    private final ObjectMapper mapper;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        log.info("[CHANNEL READ0] CTX: {}, PACKET: {}", ctx, packet);

        Command command = packet.getCommand();
        switch (command) {
            case IMC_RS_AUTH_RES:
                ImcRsAuthRes response = mapper.readValue(packet.getOptions().toString(), ImcRsAuthRes.class);

                if (response.getReturnCode().equals(ReturnCode.SUCCESS.getCode())) {
                    log.info("[Connection Success - REPORT] RETURN_CODE: {} ", response.getReturnCode());
                } else {
                    log.info("[Connection Fail - REPORT] RETURN_CODE: {}", response.getReturnCode());
                }
                break;
            default:
                throw new Exception("UnKnown Command Exception: " + command);
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
