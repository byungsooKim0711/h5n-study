package org.kimbs.netty.packet.options.rs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ImcRsAuthRes implements Serializable {

    private static final long serialVersionUID = -7507237433562865150L;

    @JsonProperty("RETURN_CODE")
    private String returnCode;
}
