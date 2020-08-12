package com.humuson.imc.admin.web;

import com.humuson.imc.admin.config.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Table(name = "TB_UNIT_PRICE")
public class UnitPrice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // web_user_id
    //...
}
