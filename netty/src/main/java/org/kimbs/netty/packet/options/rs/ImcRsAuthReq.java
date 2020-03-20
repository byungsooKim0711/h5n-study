package org.kimbs.netty.packet.options.rs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class ImcRsAuthReq implements Serializable {

    private static final long serialVersionUID = 204555769236705890L;

    @JsonProperty("CLIENT_ID")
    private String clientId;

    @JsonProperty("AUTH_KEY")
    private String authKey;

    @JsonProperty("REPORT_RES")
    private String reportRes = "Y";

    @JsonProperty("CLIENT_VERSION")
    private String clientVersion = "1.5.0";

    @Builder
    public ImcRsAuthReq(String clientId, String authKey) {
        this.clientId = clientId;
        this.authKey = authKey;
    }
}
