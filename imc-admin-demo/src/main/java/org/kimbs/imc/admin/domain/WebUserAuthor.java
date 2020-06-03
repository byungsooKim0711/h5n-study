package org.kimbs.imc.admin.domain;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.kimbs.imc.admin.config.BaseTimeEntity;
import org.kimbs.imc.admin.domain.code.AuthLevel;

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
    private AuthLevel authLevel;

    @Column(name = "AUTH_NAME", nullable = false, length = 20)
    private String authName;

    @Column(name = "AUTH_BILL", nullable = false, length = 1)
    private String authBill;

    @Column(name = "AUTH_OPERATION", nullable = false, length = 1)
    private String authOperation;

    @Column(name = "AUTH_USER", nullable = false, length = 1)
    private String authUser;

    @Column(name = "AUTH_MANAGE", nullable = false, length = 1)
    private String authManage;
}