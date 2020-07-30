package com.humuson.imc.admin.web.domain.user;

import com.humuson.imc.admin.config.BaseTimeEntity;
import com.humuson.imc.admin.web.domain.code.ImcAuthLevel;
import com.humuson.imc.admin.web.domain.convertor.BooleanYNConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Audited(targetAuditMode = RelationTargetAuditMode.AUDITED)
@Table(name = "TB_WEB_USER_AUTHOR")
@Entity
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

    @Convert(converter = BooleanYNConverter.class)
    @Column(name = "AUTH_BILL", nullable = false, length = 1)
    @ColumnDefault(value = "'N'")
    private boolean authBill = false;

    @Convert(converter = BooleanYNConverter.class)
    @Column(name = "AUTH_OPERATION", nullable = false, length = 1)
    @ColumnDefault(value = "'N'")
    private boolean authOperation = false;

    @Convert(converter = BooleanYNConverter.class)
    @Column(name = "AUTH_USER", nullable = false, length = 1)
    @ColumnDefault(value = "'N'")
    private boolean authUser = false;

    @Convert(converter = BooleanYNConverter.class)
    @Column(name = "AUTH_MANAGE", nullable = false, length = 1)
    @ColumnDefault(value = "'N'")
    private boolean authManage = false;
}