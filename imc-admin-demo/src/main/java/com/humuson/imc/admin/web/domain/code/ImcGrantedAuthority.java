package com.humuson.imc.admin.web.domain.code;

import lombok.Getter;

@Getter
public enum ImcGrantedAuthority {

    DASHBOARD,
    STAT,
    BILL,
    MANAGE,
    OPERATION,
    USER,

    @Deprecated
    ANONYMOUS,
    ;

    private static final String PREFIX = "ROLE_";

    public String getRole() {
        return PREFIX + this.name();
    }

}
