package org.kimbs.netty.client.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;
import org.kimbs.netty.packet.Command;
import org.kimbs.netty.packet.Packet;

import java.util.List;

public class MessageDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        int length = msg.readableBytes();

        if (length <= 3) {
            return;
        }

        msg.readSlice(3);
        int command = msg.readInt();
        ByteBuf options = msg.readSlice(length - (3 + 4 + 3));
        msg.readSlice(3);

        Packet<Object> packet = Packet.builder().command(Command.fromValue(command)).options(options.toString(CharsetUtil.UTF_8)).build();

        out.add(packet);
    }
}
