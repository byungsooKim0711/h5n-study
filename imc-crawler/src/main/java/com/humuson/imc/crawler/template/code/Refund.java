package com.humuson.imc.crawler.template.code;

import lombok.Getter;

@Getter
public enum Refund {

    ;

    private final String regex;

    Refund(String regex) {
        this.regex = regex;
    }
}
