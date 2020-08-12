package com.humuson.imc.admin.security;

import com.humuson.imc.admin.web.domain.admin.dto.PasswordChangeRequest;
import com.humuson.imc.admin.web.domain.admin.repository.WebAdminUser;
import com.humuson.imc.admin.web.domain.admin.repository.WebAdminUserRepository;
import com.humuson.imc.admin.web.domain.admin.repository.WebUserAuthorRepository;
import com.humuson.imc.admin.web.domain.user.repository.WebUserAuthor;
import com.humuson.imc.admin.web.domain.admin.dto.WebAdminUserDto;
import com.humuson.imc.admin.web.domain.admin.dto.WebAdminUserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@Service
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

    public WebAdminUserDto selectWebAdminUserById(long id) throws Exception {
        WebAdminUser myInfo = adminUserRepository.findById(id).orElseThrow(() -> new Exception("Unknown admin user id: " + id));

        return adminUserConverter.toDto(myInfo);
    }

    public List<WebUserAuthor> selectAllWebUserAuthor() {
        List<WebUserAuthor> authorList = webUserAuthorRepository.findAll();

        return authorList;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
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

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public WebAdminUserDto updateWebAdminUser(WebAdminUserDto dto, Long id) throws Exception {
        WebAdminUser updated = adminUserRepository.findById(id).orElseThrow(() -> new Exception("Unknown admin user id: " + id));
        WebUserAuthor author = webUserAuthorRepository.findById(dto.getAuthId()).orElseGet(updated::getWebUserAuthor);

        updated.updateBasicInfo(dto.getKakaoBizCenterId(), dto.getInfoNa(), dto.getInfoCp(), dto.getInfoEm());

        updated.setWebUserAuthor(author);
        if (dto.isActiveYn()) {
            updated.enableUser();
        } else if (!dto.isActiveYn()) {
            updated.disableUser();
        }

        return adminUserConverter.toDto(updated);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public WebAdminUserDto updatePassword(PasswordChangeRequest request, Long id) throws Exception {
        WebAdminUser updated = adminUserRepository.findById(id).orElseThrow(() -> new Exception("Unknown admin user id : " + id));

        updated.changePassword(passwordEncoder, request);

        return adminUserConverter.toDto(updated);
    }
}
