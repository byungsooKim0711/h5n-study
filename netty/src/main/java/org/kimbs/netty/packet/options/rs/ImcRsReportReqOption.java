package org.kimbs.netty.packet.options.rs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class ImcRsReportReqOption implements Serializable {

    private static final long serialVersionUID = -9217066300414678981L;

    @JsonProperty("MSG_UID")
    private String msgUid;

    @JsonProperty("REPORT_TYPE")
    private String reportType;

    @JsonProperty("ORI_REPORT_TYPE")
    private String oriReportType;

    @JsonProperty("REPORT_CODE")
    private String reportCode;

    @JsonProperty("RECEIVED_AT")
    private String receivedAt;

    @JsonProperty("NET_INFO")
    private String netInfo;

    @JsonProperty("RESEND")
    private boolean resend;

    @JsonProperty("USER_MAP")
    private Map<String, String> userMap;
}
