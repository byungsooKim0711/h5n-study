package com.humuson.imc.admin.web.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@ToString
public class WebUserDetailResponse {

    private final Long id;
    private final String company;
    private final String bizNum;
    private final String infoNa;
    private final String infoCp;
    private final String infoEm;

    private final List<ApiInfoResponse> apiInfos;

    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;
}
