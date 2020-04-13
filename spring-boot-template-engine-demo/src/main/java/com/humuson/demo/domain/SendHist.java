package com.humuson.demo.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TB_SEND_HIST")
public class SendHist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "REQ_DATE")
    private String reqDate;

    @Column(name = "SERIAL_NUMBER")
    private String serialNumber;

    @Column(name = "AGENT_ID")
    private String agentId;

    @Column(name = "PROFILE_ID")
    private long profileId;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "TEMPLATE_ID")
    private long templateId;

    @Column(name = "SEND_TYPE")
    private String sendType;

    @Column(name = "CONTENTS")
    private String contents;

    @Column(name = "RESEND_TYPE")
    private String resendType;

    @Column(name = "RESEND_TITLE")
    private String resendTitle;

    @Column(name = "RESEND_MESSAGE")
    private String resendMessage;

    @Column(name = "BILL_CODE")
    private String billCode;

    @Column(name = "MSG_UID")
    private String msgUid;

    @Column(name = "RS_RECEIVED_AT")
    private String rsReceivedAt;

    @Column(name = "DISTRIBUTION_AT")
    private String distributionAt;

    @Column(name = "BIZ_SENT_AT")
    private String bizSentAt;

    @Column(name = "BIZ_REPORT_AT")
    private String bizReportAt;

    @Column(name = "REPORT_TYPE")
    private String reportType;

    @Column(name = "REPORT_CODE")
    private String reportCode;

    @Column(name = "PROCESS_STAT")
    private String processStat;

    @Column(name = "CREATE_AT")
    private String createAt;

    @Column(name = "MODIFIED_AT")
    private String modifiedAt;

    @Column(name = "PRODUCT_TYPE")
    private String productType;
}
