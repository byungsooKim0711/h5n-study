package org.kimbs.netty.packet;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class Packet<OP> implements Serializable {

    private static final long serialVersionUID = 5855065985523715760L;

    @JsonProperty("Command")
    private Command command;

    @JsonProperty("Options")
    private OP options;

    @Builder
    public Packet(Command command, OP options) {
        this.options = options;
        this.command = command;
    }
}
