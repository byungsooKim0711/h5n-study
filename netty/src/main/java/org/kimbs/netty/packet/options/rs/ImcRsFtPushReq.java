package org.kimbs.netty.packet.options.rs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ImcRsFtPushReq implements Serializable {

    @JsonProperty("REQ_UID")
    private String reqUid;

    @JsonProperty("SENDER_KEY")
    private String senderKey;

    @JsonProperty("FT_REQ_LIST")
    private List<ImcRsFtPushOption> ftReqList;
}
