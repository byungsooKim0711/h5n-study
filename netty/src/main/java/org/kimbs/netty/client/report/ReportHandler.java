package org.kimbs.netty.client.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kimbs.netty.code.ReturnCode;
import org.kimbs.netty.packet.Command;
import org.kimbs.netty.packet.Packet;
import org.kimbs.netty.packet.exception.UnknownCommandException;
import org.kimbs.netty.packet.options.rs.ImcRsAuthRes;
import org.kimbs.netty.packet.options.rs.ImcRsReportReq;
import org.kimbs.netty.packet.options.rs.ImcRsReportRes;
import org.kimbs.netty.packet.options.rs.ImcRsReportResOption;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.netty.channel.ChannelHandler.Sharable;

@Component
@RequiredArgsConstructor
@Slf4j
@Sharable
public class ReportHandler extends SimpleChannelInboundHandler<Packet<?>> {

    private final ObjectMapper mapper;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        log.info("[REPORT CHANNEL READ0] CTX: {}, PACKET: {}", ctx, packet);

        Command command = packet.getCommand();
        switch (command) {
            case IMC_RS_AUTH_RES:
                ImcRsAuthRes authRes = mapper.readValue(packet.getOptions().toString(), ImcRsAuthRes.class);

                if (authRes.getReturnCode().equals(ReturnCode.SUCCESS.getCode())) {
                    log.info("[Connection Success - REPORT] RETURN_CODE: {} ", authRes.getReturnCode());
                } else {
                    log.info("[Connection Fail - REPORT] RETURN_CODE: {}", authRes.getReturnCode());
                }
                break;
            case IMC_RS_REPORT_REQ:
                // 서버에서 내려주는 값
                ImcRsReportReq reportReq = mapper.readValue(packet.getOptions().toString(), ImcRsReportReq.class);

                // 서버에 응답해야 하는 값
                ImcRsReportRes reportRes = new ImcRsReportRes();
                List<ImcRsReportResOption> reportResOptions = reportRes.getReportResList();
                reportReq.getReportReqList().forEach(req -> {
                    ImcRsReportResOption resOption = ImcRsReportResOption.builder()
                            .msgUid(req.getMsgUid())
                            .reportType(req.getReportType())
                            .returnCode(ReturnCode.SUCCESS.getCode())
                            .build();

                    reportResOptions.add(resOption);
                });

                Packet<ImcRsReportRes> reportResPacket = Packet.<ImcRsReportRes>builder()
                        .command(Command.IMC_RS_REPORT_RES)
                        .options(reportRes)
                        .build();

                ctx.channel().writeAndFlush(reportResPacket).sync();
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
