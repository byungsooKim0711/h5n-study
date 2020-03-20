package org.kimbs.netty.packet.options.rs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ImcRsFtPushReq implements Serializable {

    private static final long serialVersionUID = 6828021967401301859L;

    @JsonProperty("REQ_UID")
    private String reqUid;

    @JsonProperty("SENDER_KEY")
    private String senderKey;

    @Builder
    public ImcRsFtPushReq(String reqUid, String senderKey) {
        this.reqUid = reqUid;
        this.senderKey = senderKey;
    }

    @JsonProperty("FT_REQ_LIST")
    private List<ImcRsFtPushOption> ftReqList = new ArrayList<>();
}
