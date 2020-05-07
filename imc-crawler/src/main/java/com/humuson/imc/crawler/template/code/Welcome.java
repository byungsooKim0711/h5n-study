package com.humuson.imc.crawler.template.code;

import lombok.Getter;

@Getter
public enum Welcome {

    CUSTOMER_NAME("#\\{고객이름}"),
    CUSTOMER_ID("#\\{고객ID}"),
    ;

    private String regex;

    Welcome(String regex) {
        this.regex = regex;
    }
}
