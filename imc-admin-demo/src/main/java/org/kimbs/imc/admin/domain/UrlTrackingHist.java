package org.kimbs.imc.admin.domain;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.kimbs.imc.admin.config.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "TB_URL_TRACKING_HIST", indexes = {
        @Index(name = "idx_send_hist_01", columnList = "REQ_DATE,AGENT_ID, TYPE, PROCESS_STAT") })
@Data
public class UrlTrackingHist extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "SERIAL_NUMBER", nullable = false, length = 30)
    private String serialNumber;

    @Column(name = "TINY_ID", nullable = false, length = 45)
    private String tinyId;

    @Column(name = "REQ_DATE", nullable = false, length = 8)
    private String reqDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

    @Column(name = "AGENT_ID", nullable = false, length = 45)
    private String agentId;

    @Column(name = "TYPE", length = 2)
    private String type;

    @Column(name = "ORIGINAL_URL", length = 3000)
    private String originalUrl;

    @Column(name = "USER_ID", length = 45)
    private String userId;

    @Column(name = "PHONE_NUMBER", length = 64)
    private String phoneNumber;

    @Column(name = "EMAIL", length = 64)
    private String email;

    @Column(name = "UUID", length = 64)
    private String uuid;

    @Column(name = "TTL")
    private Long ttl;

    @Column(name = "CALLBACK_URL", length = 3000)
    private String callbackUrl;

    @Column(name = "CLICK_AT", nullable = false, length = 19)
    private String clickAt;

    @Column(name = "PROCESS_STAT", length = 1, nullable = false)
    @ColumnDefault("'N'")
    private String processStat = "N";

}
