package org.kimbs.netty.packet.options.rs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ImcRsReportResOption implements Serializable {

    private static final long serialVersionUID = 3267544419908148323L;

    @JsonProperty("MSG_UID")
    private String msgUid;

    @JsonProperty("REPORT_TYPE")
    private String reportType;

    @JsonProperty("RETURN_CODE")
    private String returnCode;
}
