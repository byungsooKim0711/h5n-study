package com.humuson.imc.admin.web;

import com.humuson.imc.admin.config.BaseTimeEntity;

import javax.persistence.*;

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

    @Column(name = "PIC_TYPE", length = 3)
    private String picType;

    // web_user_id
}
