package com.humuson.imc.admin.web.domain.user.dto;

import com.humuson.imc.admin.web.common.converter.Converter;
import com.humuson.imc.admin.web.domain.user.repository.WebUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class WebUserDetailConverter implements Converter<WebUser, WebUserDetailResponse> {

    private final ApiInfoConverter apiInfoConverter;

    @Override
    public WebUserDetailResponse toDto(WebUser entity) {
        return WebUserDetailResponse.builder()
            // WebUser
            .id(entity.getId())
            .bizNum(entity.getBizNum())
            .company(entity.getCompany())
            .infoCp(entity.getInfoCp())
            .infoEm(entity.getInfoEm())
            .infoNa(entity.getInfoNa())
            .createAt(entity.getCreateAt())
            .modifiedAt(entity.getModifiedAt())

            // ApiInfo
            .apiInfos(apiInfoConverter.toDto(entity.getApiInfos()))
            .build();
    }

    @Override
    public @NotNull List<WebUserDetailResponse> toDto(List<WebUser> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
}
