package com.humuson.imc.admin.web.dto.converter;

import com.humuson.imc.admin.security.ImcUserDetails;
import com.humuson.imc.admin.web.domain.admin.repository.WebAdminUser;
import com.humuson.imc.admin.web.dto.ImcLoginUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.humuson.imc.admin.web.dto.ImcLoginUser.User;

@Component
public class ImcLoginUserConverter implements Converter<ImcUserDetails, ImcLoginUser> {

    @Override
    public ImcLoginUser toDto(ImcUserDetails entity) {
        WebAdminUser user = entity.getUser();
        return ImcLoginUser.builder()
            .user(User.builder()
                .id(user.getId())
                .activeYn(user.isActiveYn())
                .infoCp(user.getInfoCp())
                .infoEm(user.getInfoEm())
                .infoNa(user.getInfoNa())
                .kakaoBizCenterId(user.getKakaoBizCenterId())
                .userLogin(user.getUserLogin())
                .build())
            .authorities(entity.getAuthorities())
            .build();
    }

    @Override
    public List<ImcLoginUser> toDto(List<ImcUserDetails> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
}
