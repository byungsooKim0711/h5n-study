package com.humuson.imc.admin.web.domain.admin.dto;

import com.humuson.imc.admin.web.domain.code.ImcAuthLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class WebAdminUserDto {

    private final Long id;
    private final String userLogin;
    private final String infoCp;
    private final String infoEm;
    private final String infoNa;
    private final boolean activeYn;
    private final String kakaoBizCenterId;
    private final int failCount;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;

    private final Integer authId;
    private final ImcAuthLevel authLevel;
    private final String authName;
}
