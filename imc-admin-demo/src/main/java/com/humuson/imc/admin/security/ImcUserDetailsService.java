package com.humuson.imc.admin.security;

import com.humuson.imc.admin.web.domain.admin.repository.WebAdminUser;
import com.humuson.imc.admin.web.domain.admin.repository.WebAdminUserRepository;
import com.humuson.imc.admin.web.domain.user.WebUserAuthor;
import com.humuson.imc.admin.web.dto.WebAdminUserDto;
import com.humuson.imc.admin.web.dto.converter.WebAdminUserConverter;
import lombok.RequiredArgsConstructor;
import com.humuson.imc.admin.web.domain.admin.repository.WebUserAuthorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional // TODO:
@Service
@RequiredArgsConstructor
public class ImcUserDetailsService implements UserDetailsService {

    private final WebUserAuthorRepository webUserAuthorRepository;
    private final WebAdminUserRepository adminUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final WebAdminUserConverter adminUserConverter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<WebAdminUser> user = adminUserRepository.findByUserLogin(username);

        user.orElseThrow(() -> new UsernameNotFoundException("User not exist with name: " + username));

        return user.map(ImcUserDetails::new).get();
    }

    public List<WebAdminUserDto> selectAllWebAdminUser() {
        List<WebAdminUser> adminList = adminUserRepository.findAll();

        return adminUserConverter.toDto(adminList);
    }

    public List<WebUserAuthor> selectAllWebUserAuthor() {
        List<WebUserAuthor> authorList = webUserAuthorRepository.findAll();

        return authorList;
    }

    public WebAdminUserDto insertWebAdminUser(WebAdminUserDto dto) throws Exception {
        WebAdminUser created = WebAdminUser.builder()
            .kakaoBizCenterId(dto.getKakaoBizCenterId())
            .infoNa(dto.getInfoNa())
            .infoEm(dto.getInfoEm())
            .infoCp(dto.getInfoCp())
            .userLogin(dto.getUserLogin())
            .password(passwordEncoder.encode(dto.getUserLogin() + "!@#$"))
            .build();

        int authId = dto.getAuthId();
        Optional<WebUserAuthor> author = webUserAuthorRepository.findById(authId);

        created.setWebUserAuthor(author.orElseThrow(() -> new Exception("Unknown authority id: " + authId)));

        return adminUserConverter.toDto(adminUserRepository.save(created));
    }

    public WebAdminUserDto updateWebAdminUser(WebAdminUserDto dto, Long id) throws Exception {
        WebAdminUser updated = adminUserRepository.findById(id).orElseThrow(() -> new Exception("Unknown admin user id: " + id));
        WebUserAuthor author = webUserAuthorRepository.findById(dto.getAuthId()).orElseGet(updated::getWebUserAuthor);

        updated.setWebUserAuthor(author);
        if (dto.isActiveYn()) {
            updated.enableUser();
        } else if (!dto.isActiveYn()) {
            updated.disableUser();
        }

        return adminUserConverter.toDto(updated);
    }
}
