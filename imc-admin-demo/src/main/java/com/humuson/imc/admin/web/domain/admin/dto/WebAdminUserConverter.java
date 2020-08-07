package com.humuson.imc.admin.web.domain.admin.dto;

import com.humuson.imc.admin.web.common.converter.Converter;
import com.humuson.imc.admin.web.domain.admin.repository.WebAdminUser;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Component
public final class WebAdminUserConverter implements Converter<WebAdminUser, WebAdminUserDto> {

    @Override
    public WebAdminUserDto toDto(WebAdminUser entity) {
        return WebAdminUserDto.builder()
            // 유저 정보
            .id(entity.getId())
            .userLogin(entity.getUserLogin())
            .infoCp(entity.getInfoCp())
            .infoEm(entity.getInfoEm())
            .infoNa(entity.getInfoNa())
            .activeYn(entity.isActiveYn())
            .kakaoBizCenterId(entity.getKakaoBizCenterId())
            .failCount(entity.getFailCount())
            .createAt(entity.getCreateAt())
            .modifiedAt(entity.getModifiedAt())

            // 권한 정보
            .authId(entity.getWebUserAuthor().getId())
            .authName(entity.getWebUserAuthor().getAuthName())
            .authLevel(entity.getWebUserAuthor().getAuthLevel())

            .build();
    }

    @Override
    public @NotNull List<WebAdminUserDto> toDto(List<WebAdminUser> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
}
