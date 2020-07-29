package com.humuson.imc.admin.web.domain.user;

import com.humuson.imc.admin.config.BaseTimeEntity;
import com.humuson.imc.admin.web.domain.convertor.BooleanYNConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "TB_API_INFO")
@Entity
public class ApiInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "API_KEY", length = 32)
    private String apiKey;

    private Long webUserId;

    @Convert(converter = BooleanYNConverter.class)
    @Column(name = "USE_YN", length = 1)
    @ColumnDefault("'Y'")
    private boolean useYn = true;

    @Builder
    public ApiInfo(String apiKey, Long webUserId) {
        this.apiKey = apiKey;
        this.webUserId = webUserId;
    }

    public void disableApiKey() {
        this.useYn = false;
    }

    public void enableApiKey() {
        this.useYn = true;
    }
}
