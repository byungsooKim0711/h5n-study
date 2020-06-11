package com.humuson.imc.crawler.model;

import lombok.Data;

@Data
public class OrderMessage {

    private String mallId;
    private String orderId;

    private long price;
    private String name;
    private String date;

}
