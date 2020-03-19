package org.kimbs.netty.packet.options.rs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ImcRsAtPushReq implements Serializable {

    @JsonProperty("REQ_UID")
    private String reqUid;

    @JsonProperty("SENDER_KEY")
    private String senderKey;

    @JsonProperty("AT_REQ_LIST")
    private List<ImcRsAtPushOption> atReqList = new ArrayList<>();
}
