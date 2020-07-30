package com.humuson.imc.admin.web.domain.stat;

import com.humuson.imc.admin.config.BaseTimeEntity;
import com.humuson.imc.admin.web.domain.system.ImcSystemCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@DynamicUpdate
@Table(name = "TB_BIZ_SEND_STAT", indexes = {
    @Index(name = "idx_biz_send_stat_01", columnList = "SEND_DATE,PROFILE_NAME,SEND_TYPE")}
)
@Entity
public class ImcBizSendStat extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "IMC_SYS_CODE", length = 5)
    private ImcSystemCode imcSysCode;

    @Column(name = "SEND_DATE", length = 8)
    private String sendDate;

    @Column(name = "SEND_TYPE", length = 2)
    private String sendType;

    @Column(name = "USER_ID", length = 20)
    private Long userId;

    @Column(name = "PROFILE_NAME", length = 128)
    private String profileName;

    @Column(name = "AGENT_ID", length = 45)
    private String agentId;

    @Column(name = "RESULT_CODE", length = 4)
    private String resultCode;

    @Column(name = "PRODUCT_TYPE", length = 1)
    private String productType;

    @Column(name = "SEND_COUNT")
    private int sendCount;
}
