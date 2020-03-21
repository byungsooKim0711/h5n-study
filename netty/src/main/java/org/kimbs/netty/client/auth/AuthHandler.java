package org.kimbs.netty.client.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kimbs.netty.client.auth.event.AuthFailureEvent;
import org.kimbs.netty.client.auth.event.AuthSuccessEvent;
import org.kimbs.netty.client.config.ClientConfig;
import org.kimbs.netty.packet.Command;
import org.kimbs.netty.packet.Packet;
import org.kimbs.netty.packet.exception.UnknownCommandException;
import org.kimbs.netty.packet.options.as.ImcAsAuthRes;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import static io.netty.channel.ChannelHandler.Sharable;

@Slf4j
@Component
@RequiredArgsConstructor
@Sharable
public class AuthHandler extends SimpleChannelInboundHandler<Packet<?>> {

    private final ObjectMapper mapper;
    private final ClientConfig clientConfig;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        log.info("[AUTH CHANNEL READ0] CTX: {}, PACKET: {}", ctx, packet);

        Command command = packet.getCommand();
        switch (command) {
            case IMC_AS_AUTH_RES:
                ImcAsAuthRes response = mapper.readValue(packet.getOptions().toString(), ImcAsAuthRes.class);

                if (response.getReturnCode() == 1000) {
                    // Auth Server 인증 성공시 Event 발생시켜 MessageClient, ReportClient 도 인증작업 시작시킨다.
                    log.info("[SUCCESS AUTHENTICATING]");
                    AuthSuccessEvent event = new AuthSuccessEvent(response);
                    applicationEventPublisher.publishEvent(event);
                } else {
                    // Auth Server 인증 실패시 fail event 발생시켜 application 종료시킬 예정..
                    log.info("[FAILURE AUTHENTICATING] WITH [CLIENT_ID: {}, CLIENT_PASSWORD: {}]", clientConfig.getAuthServer().getId(), clientConfig.getAuthServer().getPassword());
                    AuthFailureEvent event = new AuthFailureEvent(response.getReturnCode());
                    applicationEventPublisher.publishEvent(event);
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
