package com.humuson.imc.admin.web.domain.user.repository;

import com.humuson.imc.admin.config.BaseTimeEntity;
import com.humuson.imc.admin.web.common.converter.BooleanYNConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_API_INFO")
@Entity
public class ApiInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "API_KEY", length = 32)
    private String apiKey;

    @Convert(converter = BooleanYNConverter.class)
    @Column(name = "USE_YN", length = 1)
    @ColumnDefault("'Y'")
    private boolean useYn = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WEB_USER_ID", foreignKey = @ForeignKey(name = "FK_WEB_USER_API_INFO"))
    private WebUser webUser;

    public void setWebUser(WebUser webUser) {
        this.webUser = webUser;
    }

    @Builder
    public ApiInfo(String apiKey) {
        this.apiKey = apiKey;
    }

    public void disableApiKey() {
        this.useYn = false;
    }

    public void enableApiKey() {
        this.useYn = true;
    }
}
