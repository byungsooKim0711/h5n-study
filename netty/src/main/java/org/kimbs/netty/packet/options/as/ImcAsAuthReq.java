package org.kimbs.netty.packet.options.as;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class ImcAsAuthReq implements Serializable {

    @JsonProperty("CLIENT_ID")
    private String clientId;

    @JsonProperty("CLIENT_PASSWORD")
    private String clientPassword;

    @Builder
    public ImcAsAuthReq(String clientId, String clientPassword) {
        this.clientId = clientId;
        this.clientPassword = clientPassword;
    }
}
