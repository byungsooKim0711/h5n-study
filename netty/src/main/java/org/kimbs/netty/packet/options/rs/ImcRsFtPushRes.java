package org.kimbs.netty.packet.options.rs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ImcRsFtPushRes implements Serializable {

    private static final long serialVersionUID = 3272377328745511620L;

    @JsonProperty("REQ_UID")
    private String reqUid;

    @JsonProperty("RETURN_CODE")
    private String returnCode;
}
