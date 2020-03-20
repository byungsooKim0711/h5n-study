package org.kimbs.netty.packet.options.rs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class ImcRsReportResOption implements Serializable {

    private static final long serialVersionUID = 3267544419908148323L;

    @JsonProperty("MSG_UID")
    private String msgUid;

    @JsonProperty("REPORT_TYPE")
    private String reportType;

    @JsonProperty("RETURN_CODE")
    private String returnCode;

    @Builder
    public ImcRsReportResOption(String msgUid, String reportType, String returnCode) {
        this.msgUid = msgUid;
        this.reportType = reportType;
        this.returnCode = returnCode;
    }
}
