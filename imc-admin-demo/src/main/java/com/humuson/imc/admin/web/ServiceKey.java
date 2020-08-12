package com.humuson.imc.admin.web;

import com.humuson.imc.admin.config.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Table(name = "TB_SERVICE_KEY_INFO")
public class ServiceKey extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USE_YN", length = 1)
    private boolean useYn;

    @Column(name = "SERVICE_TYPE", length = 5)
    private String serviceType;

    @Column(name = "API_KEY", length = 32)
    private String apiKey;

    // web_user_id
}
