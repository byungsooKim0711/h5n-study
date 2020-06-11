package com.humuson.imc.crawler.model;

import lombok.Data;

@Data
public class RefundMessage {

    private String mallId;
    private String orderId;
    private String refundId;
}
