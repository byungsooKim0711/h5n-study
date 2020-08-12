package com.humuson.imc.admin.web.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class WebUserResponse {

    private final long id;
    private final String company;
    private final String bizNum;
    private final String infoNa;
    private final String infoCp;
    private final String infoEm;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;
}
