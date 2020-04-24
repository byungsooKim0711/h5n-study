package com.humuson.imc.crawler.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Member {

    private String id;

    private String name;

    private String memo;

    private LocalDateTime joinInDate;

    private String grade;

}
