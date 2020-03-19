package org.kimbs.netty.packet.options.as;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ImcAsAuthOption implements Serializable {

    @JsonProperty("SERVER_TYPE")
    private String serverType;

    @JsonProperty("RS_HOST")
    private String rsHost;

    @JsonProperty("RS_SEND_PORT")
    private int rsSendPort;

    @JsonProperty("RS_REPORT_PORT")
    private int rsReportPort;

    @JsonProperty("SEND_LINE_COUNT")
    private int sendLineCount;

    @JsonProperty("REPORT_LINE_COUNT")
    private int reportLineCount;
}
