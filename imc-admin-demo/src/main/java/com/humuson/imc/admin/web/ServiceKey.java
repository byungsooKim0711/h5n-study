package com.humuson.imc.admin.web;

import com.humuson.imc.admin.config.BaseTimeEntity;
import com.humuson.imc.admin.web.domain.user.repository.WebUser;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TB_SERVICE_KEY_INFO")
public class ServiceKey extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USE_YN", length = 1)
    private boolean useYn;

    @Enumerated(EnumType.STRING)
    @Column(name = "SERVICE_TYPE", length = 5)
    private ServiceType serviceType;

    @Column(name = "API_KEY", length = 32)
    private String apiKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WEB_USER_ID", foreignKey = @ForeignKey(name = "FK_WEB_USER_SERVICE_KEY"))
    private WebUser webUser;

    public void setWebUser(WebUser webUser) {
        this.webUser = webUser;
    }
}
