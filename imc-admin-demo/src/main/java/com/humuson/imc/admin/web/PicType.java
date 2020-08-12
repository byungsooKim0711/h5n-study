package com.humuson.imc.admin.web;

import lombok.Getter;

@Getter
public enum PicType {
    DEV("개발 담당자"),
    CAL("정산 담당자"),
    SAL("영업 담당자"),
    ETC("기타")
    ;

    private final String type;

    PicType(String type) {
        this.type = type;
    }
}
