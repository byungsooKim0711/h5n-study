package com.humuson.imc.admin.web.domain.system;

import com.humuson.imc.admin.config.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@ToString
@DynamicUpdate
@Table(name = "TB_IMC_SYSTEM")
@Entity
public class ImcSystem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "IMC_SYS_CODE", length = 5)
    private ImcSystemCode imcSysCode;

    @Column(name = "IMC_SYS_NAME", length = 128)
    private String imcSysName;

    @Column(name = "API_HOST", length = 128)
    private String apiHost;
}
