package com.humuson.imc.crawler.model.code;

import lombok.Getter;

@Getter
public enum MALL {
    MALL_NAME("\\$\\{쇼핑몰이름}"),
    MALL_TEL_NUMBER("\\$\\{쇼핑몰번호}"),
    MALL_URL("\\$\\{쇼핑몰URL}"),
    ;

    private String regex;

    MALL(String regex) {
        this.regex = regex;
    }
}
