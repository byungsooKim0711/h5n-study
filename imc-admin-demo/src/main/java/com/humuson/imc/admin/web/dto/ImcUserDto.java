package com.humuson.imc.admin.web.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class ImcUserDto {

    private Long userId;
    private String userLogin;
    private String infoCp;
    private String infoEm;
    private String infoNa;
    private String activeYn;
    private String kakaoBizCenterId;

    private Collection<? extends GrantedAuthority> authorities;
}
