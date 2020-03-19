package org.kimbs.netty.packet;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;

@Getter
public enum PacketStructure {

    STX(Unpooled.wrappedBuffer(new byte[] { 0x24, 0x02, 0x24 })),
    ETX(Unpooled.wrappedBuffer(new byte[] { 0x24, 0x03, 0x24 })),
    ACK(Unpooled.wrappedBuffer(new byte[] { 0x24, 0x06, 0x24 })),
    NAK(Unpooled.wrappedBuffer(new byte[] { 0x24, 0x15, 0x24 }));

    private ByteBuf byteBuf;

    PacketStructure(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }
}
