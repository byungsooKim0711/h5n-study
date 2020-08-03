package com.humuson.imc.admin.web.domain.admin.repository;

import com.humuson.imc.admin.config.BaseTimeEntity;
import com.humuson.imc.admin.web.domain.convertor.BooleanYNConverter;
import com.humuson.imc.admin.web.domain.user.WebUserAuthor;
import com.humuson.imc.admin.web.common.Exception.InvalidParameterException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.persistence.*;


@Getter
@ToString
@NoArgsConstructor
@Audited(targetAuditMode = RelationTargetAuditMode.AUDITED)
@DynamicUpdate
@Table(name = "TB_WEB_ADMIN_USER",
    uniqueConstraints = {
        @UniqueConstraint(name = "IDX_WEB_ADMIN_USER_01", columnNames = {"USER_LOGIN"})
    }
)
@Entity
public class WebAdminUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @AuditJoinTable
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "AUTH_ID", referencedColumnName = "ID")
    private WebUserAuthor webUserAuthor;

    @Column(name = "USER_LOGIN", length = 45, nullable = false)
    private String userLogin;

    @Column(name = "PASSWORD", length = 128, nullable = false)
    private String password;

    @Column(name = "KAKAO_BIZ_CENTER_ID", length = 128)
    private String kakaoBizCenterId;

    @Convert(converter = BooleanYNConverter.class)
    @Column(name = "ACTIVE_YN", length = 1, nullable = false)
    @ColumnDefault("'Y'")
    private boolean activeYn = true;

    @Column(name = "INFO_CP", length = 64)
    private String infoCp;

    @Column(name = "INFO_EM", length = 64)
    private String infoEm;

    @Column(name = "INFO_NA", length = 64)
    private String infoNa;

    @NotAudited
    @Column(name = "FAIL_COUNT")
    @ColumnDefault("0")
    private int failCount = 0;

    @NotAudited
    @Column(name = "AUTHORITY", length = 1)
    private String authority;

    @Builder
    public WebAdminUser(String userLogin, String password, String kakaoBizCenterId, String infoNa, String infoCp, String infoEm) throws InvalidParameterException {
        if (StringUtils.isEmpty(userLogin) || StringUtils.isEmpty(password)) {
            throw new InvalidParameterException();
        }

        this.userLogin = userLogin;
        this.password = password;
        this.kakaoBizCenterId = kakaoBizCenterId;
        this.infoNa = infoNa;
        this.infoCp = infoCp;
        this.infoEm = infoEm;
    }

    public void updateBasicInfo(String kakaoBizCenterId, String infoNa, String infoCp, String infoEm) {
        this.kakaoBizCenterId = kakaoBizCenterId;
        this.infoNa = infoNa;
        this.infoCp = infoCp;
        this.infoEm = infoEm;
    }

    public void addFailCount() {
        if (this.failCount < Integer.MAX_VALUE) {
            this.failCount++;
        }
    }

    public void initializeFailCount() {
        this.failCount = 0;
    }

    public boolean changePassword(PasswordEncoder passwordEncoder, String newPassword) {
        if (!passwordEncoder.matches(newPassword, this.password)) {
            return false;
        }
        this.password = passwordEncoder.encode(newPassword);
        return true;
    }

    public void enableUser() {
        this.activeYn = true;
    }

    public void disableUser() {
        this.activeYn = false;
    }

    public void setWebUserAuthor(WebUserAuthor webUserAuthor) {
        this.webUserAuthor = webUserAuthor;
    }
}