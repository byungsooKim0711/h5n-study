package com.humuson.imc.admin.domain.convertor;

import com.humuson.imc.admin.domain.WebAdminUser;
import com.humuson.imc.admin.security.ImcUserDetails;
import com.humuson.imc.admin.web.dto.ImcLoginUserDto;
import org.springframework.core.convert.converter.Converter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Deprecated
public class ImcDtoConverter {

    private static final List<Class<?>> CLASSES = Arrays.asList(ImcUserDetails.class, WebAdminUser.class);

    public static boolean supports(Class<?> type) {
        return CLASSES.contains(type);
    }

    public enum Details implements Converter<ImcUserDetails, ImcLoginUserDto> {

        INSTANCE
        ;

        @Override
        public ImcLoginUserDto convert(ImcUserDetails source) {
            ImcLoginUserDto dto = new ImcLoginUserDto();
            return dto;
        }

        public List<ImcLoginUserDto> convert(List<ImcUserDetails> sources) {
            return sources.stream().map(this::convert).collect(Collectors.toList());
        }
    }
}
