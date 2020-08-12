package com.humuson.imc.admin.web.domain.user.dto;

import com.humuson.imc.admin.web.common.converter.Converter;
import com.humuson.imc.admin.web.domain.user.repository.ApiInfo;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApiInfoConverter implements Converter<ApiInfo, ApiInfoResponse> {

    @Override
    public ApiInfoResponse toDto(ApiInfo entity) {
        return ApiInfoResponse.builder()
            // ApiInfo
            .id(entity.getId())
            .apiKey(entity.getApiKey())
            .useYn(entity.isUseYn())
            .createAt(entity.getCreateAt())
            .modifiedAt(entity.getModifiedAt())

            // WebUser
            .webUserId(entity.getWebUser().getId())
            .company(entity.getWebUser().getCompany())
            .build();
    }

    @Override
    public @NotNull List<ApiInfoResponse> toDto(List<ApiInfo> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
}
