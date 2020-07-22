package com.humuson.imc.admin.web.dto;

import com.humuson.imc.admin.security.ImcUserDetails;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ImcLoginUserDto implements DtoConverter <ImcUserDetails, ImcLoginUserDto> {

    private final User user = new User();

    private Collection<? extends GrantedAuthority> authorities;

    @Getter
    public static class User {
        private Long id;
        private String userLogin;
        private String infoCp;
        private String infoEm;
        private String infoNa;
        private String activeYn;
        private String kakaoBizCenterId;
    }

    @Override
    public ImcLoginUserDto convertDto(ImcUserDetails entity) {
        this.user.id = entity.getUser().getId();
        this.user.userLogin = entity.getUsername();
        this.user.infoCp = entity.getUser().getInfoCp();
        this.user.infoEm = entity.getUser().getInfoEm();
        this.user.infoNa = entity.getUser().getInfoNa();
        this.user.activeYn = entity.getUser().getActiveYn();
        this.user.kakaoBizCenterId = entity.getUser().getKakaoBizCenterId();
        this.authorities = entity.getAuthorities();

        return this;
    }

    @Override
    public List<ImcLoginUserDto> convertDto(List<ImcUserDetails> entities) {
        return entities.stream().map(this::convertDto).collect(Collectors.toList());
    }

    @Override
    public ImcUserDetails convertEntity(ImcLoginUserDto dto) {
        // TODO:
        return new ImcUserDetails();
    }

    @Override
    public List<ImcUserDetails> convertEntity(List<ImcLoginUserDto> dtos) {
        return dtos.stream().map(this::convertEntity).collect(Collectors.toList());
    }
}
