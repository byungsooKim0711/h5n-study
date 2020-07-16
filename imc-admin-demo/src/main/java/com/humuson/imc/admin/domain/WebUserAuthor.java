package com.humuson.imc.admin.domain;

import com.humuson.imc.admin.domain.code.ImcAuthLevel;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import com.humuson.imc.admin.config.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Table(name = "TB_WEB_USER_AUTHOR")
@Data
public class WebUserAuthor extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "AUTH_LEVEL", nullable = false, length = 1)
    @ColumnDefault(value = "'S'")
    @Enumerated(EnumType.STRING)
    private ImcAuthLevel authLevel = ImcAuthLevel.S;

    @Column(name = "AUTH_NAME", nullable = false, length = 20)
    private String authName;

    @Column(name = "AUTH_BILL", nullable = false, length = 1)
    @ColumnDefault(value = "'N'")
    private String authBill = "N";

    @Column(name = "AUTH_OPERATION", nullable = false, length = 1)
    @ColumnDefault(value = "'N'")
    private String authOperation = "N";

    @Column(name = "AUTH_USER", nullable = false, length = 1)
    @ColumnDefault(value = "'N'")
    private String authUser = "N";

    @Column(name = "AUTH_MANAGE", nullable = false, length = 1)
    @ColumnDefault(value = "'N'")
    private String authManage = "N";
}