package org.kimbs.netty.packet.options.rs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ImcRsReportReq implements Serializable {

    private static final long serialVersionUID = 2878475437290445099L;

    @JsonProperty("REPORT_REQ_LIST")
    private List<ImcRsReportReqOption> reportReqList = new ArrayList<>();
}
