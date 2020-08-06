package com.humuson.imc.admin.web.user.dto;

import com.humuson.imc.admin.web.domain.user.WebUser;
import com.humuson.imc.admin.web.dto.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WebUserConverter implements Converter<WebUser, WebUserResponse> {

    @Override
    public WebUserResponse toDto(WebUser entity) {
        return WebUserResponse.builder()
            .id(entity.getId())
            .bizNum(entity.getBizNum())
            .company(entity.getCompany())
            .infoCp(entity.getInfoCp())
            .infoEm(entity.getInfoEm())
            .infoNa(entity.getInfoNa())
            .createAt(entity.getCreateAt())
            .modifiedAt(entity.getModifiedAt())
            .build();
    }

    @Override
    public @NotNull List<WebUserResponse> toDto(List<WebUser> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
}
