package com.humuson.imc.admin.domain.convertor;

import com.humuson.imc.admin.security.ImcUserDetails;
import com.humuson.imc.admin.web.dto.ImcUserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WebAdminUserDtoConverter implements Converter<ImcUserDetails, ImcUserDto> {

    @Override
    public ImcUserDto convert(ImcUserDetails source) {
        ImcUserDto dto = new ImcUserDto();
        dto.setUserId(source.getUser().getId());
        dto.setUserLogin(source.getUsername());
        dto.setInfoCp(source.getUser().getInfoCp());
        dto.setInfoEm(source.getUser().getInfoEm());
        dto.setInfoNa(source.getUser().getInfoNa());
        dto.setActiveYn(source.getUser().getActiveYn());
        dto.setKakaoBizCenterId(source.getUser().getKakaoBizCenterId());
        dto.setAuthorities(source.getAuthorities());

        return dto;
    }

    public List<ImcUserDto> convert(List<ImcUserDetails> sources) {
        return sources.stream().map(this::convert).collect(Collectors.toList());
    }
}
