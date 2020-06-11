package com.humuson.imc.crawler.template.code;

import lombok.Getter;

@Getter
public enum Board {
    BOARD_NAME("#\\{고객이름}"),
    ;

    private final String regex;

    Board(String regex) {
        this.regex = regex;
    }
}
