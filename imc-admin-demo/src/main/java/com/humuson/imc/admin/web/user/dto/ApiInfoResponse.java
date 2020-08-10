package com.humuson.imc.admin.web.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class ApiInfoResponse {
    private final long id;
    private final String apiKey;
    private final boolean useYn;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;

    private final Long webUserId;
    private final String company;
}
