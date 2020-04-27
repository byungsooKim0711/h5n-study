package com.humuson.imc.crawler.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "MALL_ADMIN")
public class MallAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "LOGIN_ID")
    private String loginId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "MALL_URL")
    private String mallUrl;

    @Column(name = "MALL_TEL_NUMBER")
    private String mallTelNumber;

    @Column(name = "MALL_NAME")
    private String mallName;
}
