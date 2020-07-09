package org.kimbs.imc.admin.domain;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.kimbs.imc.admin.config.BaseTimeEntity;

import javax.persistence.*;

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

    @Column(name = "USER_LOGIN", length = 45, nullable = false)
    private String userLogin;

    @Column(name = "PASSWORD", length = 128, nullable = false)
    private String password;

    @Column(name = "KAKAO_BIZ_CENTER_ID", length = 128)
    private String kakaoBizCenterId;

    @Column(name = "ACTIVE_YN", length = 1, nullable = false)
    @ColumnDefault("'Y'")
    private String activeYn = "Y";

    @Column(name = "INFO_CP", length = 64)
    private String infoCp;

    @Column(name = "INFO_EM", length = 64)
    private String infoEm;

    @Column(name = "INFO_NA", length = 64)
    private String infoNa;

    @Column(name = "FAIL_COUNT")
    @ColumnDefault("0")
    private int failCount = 0;

    @Column(name = "AUTHORITY", length = 1)
    private String authority;
}