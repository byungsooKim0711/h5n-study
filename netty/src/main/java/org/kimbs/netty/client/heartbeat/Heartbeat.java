package org.kimbs.netty.client.heartbeat;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Heartbeat {

    // SET, [ACK / NAK] PACKET
    private ByteBuf byteBuf;
}
