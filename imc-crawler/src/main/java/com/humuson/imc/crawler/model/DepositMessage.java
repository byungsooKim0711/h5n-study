package com.humuson.imc.crawler.model;

import lombok.Data;

@Data
public class DepositMessage {

    private String mallId;
    private String orderId;

    private long price;
    private String bank;
    private String account;
    private String depositor;
}
