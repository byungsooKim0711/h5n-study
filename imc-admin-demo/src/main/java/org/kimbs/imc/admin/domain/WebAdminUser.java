package org.kimbs.imc.admin.domain;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;
import org.kimbs.imc.admin.config.BaseTimeEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "TB_WEB_ADMIN_USER",
        uniqueConstraints = {
            @UniqueConstraint(name = "IDX_WEB_ADMIN_USER_01", columnNames = {"USER_LOGIN"})
        }
)
public class WebAdminUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "AUTH_ID", referencedColumnName = "ID")
    private WebUserAuthor webUserAuthor;

    @Column(name = "USER_LOGIN")
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
    @ColumnDefault("'Y'")
    @Length(min = 1, max = 1)
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