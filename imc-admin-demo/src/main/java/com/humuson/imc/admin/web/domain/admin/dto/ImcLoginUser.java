package com.humuson.imc.admin.web.domain.admin.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Builder
public class ImcLoginUser {

    private final User user;

    private final Collection<? extends GrantedAuthority> authorities;

    @Getter
    @Builder
    public static class User {
        private final Long id;
        private final String userLogin;
        private final String infoCp;
        private final String infoEm;
        private final String infoNa;
        private final boolean activeYn;
        private final String kakaoBizCenterId;
    }
}
