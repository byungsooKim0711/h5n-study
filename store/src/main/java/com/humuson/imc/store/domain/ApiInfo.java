package com.humuson.imc.store.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TB_API_INFO")
public class ApiInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "API_KEY", nullable = false, length = 32)
    private String apiKey;

    @Column(name = "WEB_USER_ID")
    private Long webUserId;

    @Column(name = "USE_YN", nullable = false, length = 1)
    private String useYn = "Y";

    @Column(name = "CREATE_AT", nullable = false, length = 19)
    private String createAt;

    @Column(name = "MODIFIED_AT", nullable = false, length = 19)
    private String modifiedAt;
}
