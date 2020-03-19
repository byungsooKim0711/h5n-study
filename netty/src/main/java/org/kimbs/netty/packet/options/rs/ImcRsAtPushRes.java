package org.kimbs.netty.packet.options.rs;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ImcRsAtPushRes implements Serializable {

    @JsonProperty("REQ_UID")
    private String reqUid;

    @JsonProperty("RETURN_CODE")
    private String returnCode;
}
