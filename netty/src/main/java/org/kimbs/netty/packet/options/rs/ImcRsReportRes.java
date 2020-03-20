package org.kimbs.netty.packet.options.rs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ImcRsReportRes implements Serializable {

    private static final long serialVersionUID = -250973988975900847L;

    @JsonProperty("REPORT_RES_LIST")
    private List<ImcRsReportResOption> reportResList = new ArrayList<>();
}
