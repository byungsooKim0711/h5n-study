package com.humuson.imc.crawler.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "MALL_USER")
public class MallUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "LOGIN_ID", nullable = false)
    private String loginId;

    @Column(name = "REGISTERED_DATE")
    private LocalDateTime registeredDate;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "WELCOME_FLAG", columnDefinition = "CHAR(1) DEFAULT 'N'", nullable = false)
    private String welcomeFlag = "N";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MALL_ADMIN_ID", foreignKey = @ForeignKey(name = "FK_MALL_USER_MALL_ADMIN"))
    private MallAdmin mallAdmin;

}
