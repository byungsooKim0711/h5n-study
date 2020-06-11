package com.humuson.imc.crawler.template.code;

import lombok.Getter;

@Getter
public enum Deposit {

    DEPOSIT_USER("#\\{고객이름}"),
    DEPOSIT_PRICE("#\\{PRICE}"),
    DEPOSIT_BANK("#\\{BANK}"),
    DEPOSIT_ACCOUNT("#\\{ACCOUNT}"),
    DEPOSIT_DEPOSITOR("#\\{DEPOSITOR}")
    ;

    private final String regex;

    Deposit(String regex) {
        this.regex = regex;
    }
}
