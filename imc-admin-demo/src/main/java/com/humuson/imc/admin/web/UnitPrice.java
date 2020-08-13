package com.humuson.imc.admin.web;

import com.humuson.imc.admin.config.BaseTimeEntity;
import com.humuson.imc.admin.web.domain.user.repository.WebUser;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TB_UNIT_PRICE")
public class UnitPrice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WEB_USER_ID", foreignKey = @ForeignKey(name = "FK_WEB_USER_UNIT_PRICE"))
    private WebUser webUser;

    public void setWebUser(WebUser webUser) {
        this.webUser = webUser;
    }
}
