package org.kimbs.imc.admin.security;

import lombok.RequiredArgsConstructor;
import org.kimbs.imc.admin.domain.WebAdminUser;
import org.kimbs.imc.admin.domain.WebAdminUserRepository;
import org.kimbs.imc.admin.domain.WebUserAuthor;
import org.kimbs.imc.admin.domain.WebUserAuthorRepository;
import org.kimbs.imc.admin.domain.code.ImcAuthLevel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional // TODO:
@Service
@RequiredArgsConstructor
public class ImcUserDetailsService implements UserDetailsService {

    private final WebUserAuthorRepository webUserAuthorRepository;
    private final WebAdminUserRepository adminUserRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<WebAdminUser> user = adminUserRepository.findByUserLogin(s);

        user.orElseThrow(() -> new UsernameNotFoundException("User not exist with name: " + s));

        return user.map(ImcUserDetails::new).get();
    }

    // 권한(1, 2, 3), 아이디, 카카오 아이디, 이름, 전화번호, 이메일 입력
    public WebAdminUser insertWebAdminUser(WebAdminUser webAdminUser) {
        webAdminUser.setPassword(passwordEncoder.encode(webAdminUser.getPassword()));

        ImcAuthLevel authLevel = webAdminUser.getWebUserAuthor().getAuthLevel();
        WebUserAuthor author = webUserAuthorRepository.findByAuthLevel(authLevel);

        if (author != null) {
            webAdminUser.setWebUserAuthor(author);
        }

        return adminUserRepository.save(webAdminUser);
    }

    // 권한만 변경 가능
    public WebAdminUser updateWebAdminUser(WebAdminUser webAdminUser, Long id) {
        return null;
    }

    // active_yn만 n으로 변경 한다.
    public WebAdminUser deleteWebAdminUser(Long id) {
        return null;
    }


    // 권한 생성, 조회, 기능
}
