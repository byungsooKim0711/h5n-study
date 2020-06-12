package com.humuson.imc.crawler.model;

import lombok.Data;

@Data
public class BoardMessage {
    private String mallId;
    private String userId;
    private String registeredDate;
    private String username;
    private String phoneNumber;

    private String message;
}
