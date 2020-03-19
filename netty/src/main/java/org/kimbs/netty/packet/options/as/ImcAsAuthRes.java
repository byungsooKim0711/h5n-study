package org.kimbs.netty.packet.options.as;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ImcAsAuthRes implements Serializable {

    @JsonProperty("RETURN_CODE")
    private int returnCode;

    @JsonProperty("AUTH_KEY")
    private String authKey;

    @JsonProperty("ENC_METHOD")
    private String encMethod;

    @JsonProperty("RS_LIST")
    private List<ImcAsAuthOption> rsList;
}
