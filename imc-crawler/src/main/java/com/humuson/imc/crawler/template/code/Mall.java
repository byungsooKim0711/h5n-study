package com.humuson.imc.crawler.template.code;

import lombok.Getter;

@Getter
public enum Mall {
    MALL_NAME("#\\{쇼핑몰이름}"),
    MALL_TEL_NUMBER("#\\{쇼핑몰번호}"),
    MALL_URL("#\\{쇼핑몰URL}"),
    ;

    private String regex;

    Mall(String regex) {
        this.regex = regex;
    }
}
