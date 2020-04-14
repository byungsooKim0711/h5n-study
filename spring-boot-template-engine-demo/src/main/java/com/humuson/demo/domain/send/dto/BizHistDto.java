package com.humuson.demo.domain.send.dto;

import lombok.Data;

@Data
public class BizHistDto {

    private String reqDate;
    private String companyName;
    private String sendType;
    private String contents;
    private Long templateId;
}
