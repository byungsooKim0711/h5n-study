package org.kimbs.netty.packet.options.rs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ImcRsAtPushReq implements Serializable {

    private static final long serialVersionUID = -1411390204349667800L;

    @JsonProperty("REQ_UID")
    private String reqUid;

    @JsonProperty("SENDER_KEY")
    private String senderKey;

    @Builder
    public ImcRsAtPushReq(String reqUid, String senderKey) {
        this.reqUid = reqUid;
        this.senderKey = senderKey;
    }

    @JsonProperty("AT_REQ_LIST")
    private List<ImcRsAtPushOption> atReqList = new ArrayList<>();
}
