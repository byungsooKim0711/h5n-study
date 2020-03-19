package org.kimbs.netty.packet.options.rs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ImcRsReportReq implements Serializable {

    @JsonProperty("REPORT_REQ_LIST")
    public List<ImcRsReportReqOption> reportReqList;
}
