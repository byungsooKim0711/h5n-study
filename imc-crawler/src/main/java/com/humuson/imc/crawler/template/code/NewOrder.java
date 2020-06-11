package com.humuson.imc.crawler.template.code;

import lombok.Getter;

@Getter
public enum NewOrder {

    NEW_ORDER_NAME("#\\{NAME}"),
    NEW_ORDER_DATE("#\\{DATE}"),
    NEW_ORDER_ORDER_ID("#\\{ORDERID}"),
    NEW_ORDER_PRICE("#\\{PRICE}")
    ;

    private final String regex;

    NewOrder(String regex) {
        this.regex = regex;
    }
}
