package org.kimbs.imc.admin.domain;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "TB_WEB_ADMIN_USER")
public class WebAdminUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "AUTH_ID")
    private Long authId;

    @Column(name = "USER_LOGIN", unique = true)
    @NotNull
    @Length(max = 45)
    private String userLogin;

    @Column(name = "PASSWORD")
    @NotNull
    @Length(max = 128)
    private String password;

    @Column(name = "KAKAO_BIZ_CENTER_ID")
    @Length(max = 128)
    private String kakaoBizCenterId;

    @Column(name = "ACTIVE_YN")
    @NotNull
    @ColumnDefault("Y")
    private String activeYn;

    @Column(name = "INFO_CP")
    @Length(max = 64)
    private String infoCp;

    @Column(name = "INFO_EM")
    @Length(max = 64)
    private String infoEm;

    @Column(name = "INFO_NA")
    @Length(max = 64)
    private String infoNa;

    @Column(name = "FAIL_COUNT")
    @ColumnDefault("0")
    private int failCount;

    @Column(name = "AUTHORITY")
    @Length(max = 1)
    private String authority;
}


/*
CREATE TABLE `tb_web_admin_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `auth_id` bigint(20) NOT NULL,
  `user_login` varchar(45) NOT NULL,
  `password` varchar(128) NOT NULL,
  `kakao_biz_center_id` varchar(128) DEFAULT NULL,
  `active_yn` varchar(1) NOT NULL DEFAULT 'Y',
  `info_cp` varchar(64) DEFAULT NULL,
  `info_em` varchar(64) DEFAULT NULL,
  `info_na` varchar(64) DEFAULT NULL,
  `fail_count` int(11) DEFAULT '0',
  `create_at` varchar(19) NOT NULL,
  `modified_at` varchar(19) NOT NULL,
  `authority` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_web_admin_user_01` (`user_login`),
  KEY `idx_web_admin_user_02` (`auth_id`)
) ENGINE=InnoDB CHARSET=utf8
*/