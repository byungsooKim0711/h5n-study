package org.kimbs.netty.packet.options.rs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.kimbs.netty.packet.options.Attachment;

import java.io.Serializable;
import java.util.Map;

@Data
public class ImcRsAtPushOption implements Serializable {

    private static final long serialVersionUID = -8763534623906657747L;

    @JsonProperty("MSG_UID")
    private String msgUid;

    @JsonProperty("TEMPLATE_CODE")
    private String templateCode;

    @JsonProperty("PHONE_NUMBER")
    private String phoneNumber;

    @JsonProperty("APP_USER_ID")
    private String appUserId;

    @JsonProperty("CONTENTS")
    private String contents;

    @JsonProperty("BUTTON_URL")
    private String buttonUrl;

    @JsonProperty("ATTACHMENT")
    private Attachment attachment;

    @JsonProperty("RESEND_TYPE")
    private String resendType;

    @JsonProperty("RESEND_TITLE")
    private String resendTitle;

    @JsonProperty("RESEND_MESSAGE")
    private String resendMessage;

    @JsonProperty("RESEND_FROM")
    private String resendFrom;

    @JsonProperty("RESEND_TO")
    private String resendTo;

    @JsonProperty("RESEND_MESSAGE_REUSE")
    private boolean resendMessageReuse;

    @JsonProperty("BILL_CODE")
    private String billCode;

    @JsonProperty("USER_MAP")
    private Map<String, String> userMap;
}
