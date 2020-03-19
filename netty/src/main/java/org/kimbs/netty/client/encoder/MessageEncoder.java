package org.kimbs.netty.client.encoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;
import org.kimbs.netty.packet.Command;
import org.kimbs.netty.packet.Packet;
import org.kimbs.netty.packet.PacketStructure;

import java.nio.CharBuffer;

public class MessageEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Command command = msg.getCommand();
        ByteBuf options = ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(mapper.writeValueAsString(msg.getOptions())), CharsetUtil.UTF_8);

        out.writeBytes(PacketStructure.STX.getByteBuf().copy());
        out.writeInt(command.getCode());
        out.writeBytes(options);
        out.writeBytes(PacketStructure.ETX.getByteBuf().copy());

        System.out.println(out.toString(CharsetUtil.UTF_8));
        options.release();
    }
}
