package org.kimbs.netty.packet.options.rs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ImcRsReportRes implements Serializable {

    @JsonProperty("REPORT_RES_LIST")
    private List<ImcRsReportResOption> reportResList;
}
