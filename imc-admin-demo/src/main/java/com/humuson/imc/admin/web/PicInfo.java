package com.humuson.imc.admin.web;

import com.humuson.imc.admin.config.BaseTimeEntity;
import com.humuson.imc.admin.web.domain.user.repository.WebUser;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TB_PIC_INFO")
public class PicInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PHONE", length = 20)
    private String phone;

    @Column(name = "EMAIL", length = 45)
    private String email;

    @Column(name = "NAME", length = 45)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "PIC_TYPE", length = 3)
    private PicType picType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WEB_USER_ID", foreignKey = @ForeignKey(name = "FK_WEB_USER_PIC_INFO"))
    private WebUser webUser;

    public void setWebUser(WebUser webUser) {
        this.webUser = webUser;
    }

    @Builder
    public PicInfo(String name, String email, String phone, PicType picType) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.picType = picType;
    }
}
