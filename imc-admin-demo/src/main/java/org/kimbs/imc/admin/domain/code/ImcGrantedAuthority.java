package org.kimbs.imc.admin.domain.code;

import lombok.Getter;

@Getter
public enum ImcGrantedAuthority {

    DASHBOARD,
    STAT,
    BILL,
    MANAGE,
    OPERATION,
    USER,
    ;

    private static final String PREFIX = "ROLE_";

    public String getRole() {
        return PREFIX + this.name();
    }

}
