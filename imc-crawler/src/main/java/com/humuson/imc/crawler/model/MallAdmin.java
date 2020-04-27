package com.humuson.imc.crawler.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "MALL_ADMIN")
public class MallAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "LOGIN_ID", nullable = false)
    private String loginId;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "MALL_URL")
    private String mallUrl;

    @Column(name = "MALL_TEL_NUMBER")
    private String mallTelNumber;

    @Column(name = "MALL_NAME")
    private String mallName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "mallAdmin")
    private List<MallUser> mallUsers = new ArrayList<>();

    public void addMallUsers(List<MallUser> mallUsers) {
        for (MallUser mallUser : mallUsers) {
            this.addMallUser(mallUser);
        }
    }

    public void addMallUser(MallUser mallUser) {
        this.mallUsers.add(mallUser);
        mallUser.setMallAdmin(this);
    }
}
