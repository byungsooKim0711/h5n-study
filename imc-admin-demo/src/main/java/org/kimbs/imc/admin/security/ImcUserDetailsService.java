package org.kimbs.imc.admin.security;

import lombok.RequiredArgsConstructor;
import org.kimbs.imc.admin.domain.WebAdminUser;
import org.kimbs.imc.admin.domain.WebAdminUserRepository;
import org.kimbs.imc.admin.domain.WebUserAuthor;
import org.kimbs.imc.admin.domain.WebUserAuthorRepository;
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
    /*  Json Format
        {
            "userLogin": "test",
            "password": "test",
            "kakaoBizCenterId": "test@test.test",
            "infoCp": "test",
            "infoEm": "test@test.test",
            "infoNa": "test",
            "webUserAuthor": {
                "id": 2
            }
        }
    */
    public WebAdminUser insertWebAdminUser(WebAdminUser webAdminUser) throws Exception {
        webAdminUser.setPassword(passwordEncoder.encode(webAdminUser.getPassword()));

        int authId = webAdminUser.getWebUserAuthor().getId();
        Optional<WebUserAuthor> author = webUserAuthorRepository.findById(authId);

        webAdminUser.setWebUserAuthor(author.orElseThrow(() -> new Exception("Unknown authority id: " + authId)));

        return adminUserRepository.save(webAdminUser);
    }


    /*  Json Format
        {
            "userLogin": "test",
            "password": "test",
            "kakaoBizCenterId": "test@test.test",
            "infoCp": "test",
            "activeYn": "Y",
            "infoEm": "test@test.test",
            "infoNa": "test",
            "webUserAuthor": {
                "id": 3 // 권한 변경
            }
        }
    */
    public WebAdminUser updateWebAdminUser(WebAdminUser webAdminUser, Long id) throws Exception {
//        Optional<WebAdminUser> adminUser = adminUserRepository.findById(id);
//        WebAdminUser admin = adminUser.orElseThrow(() -> new Exception("Unknown admin user id: " + id));
//
//        int authId = webAdminUser.getWebUserAuthor().getId();
//        Optional<WebUserAuthor> adminAuthor = webUserAuthorRepository.findById(authId);
//        WebUserAuthor author = adminAuthor.orElseThrow(() -> new Exception("Unknown authority id: " + authId));
//
//        admin.setWebUserAuthor(author);


//        Optional<WebAdminUser> adminUser = adminUserRepository.findById(id);
//        WebAdminUser admin = adminUser.orElseThrow(() -> new Exception("Unknown admin user id: " + id));
//        return adminUserRepository.save(admin);

        long l = adminUserRepository.updateWebAdminUser(webAdminUser, id);
        if (l > 0) {
            return adminUserRepository.findById(id).orElseThrow(() -> new Exception("Unknown admin user id: " + id));
        }

        throw new Exception("Unknown admin user id: " + id);
    }
}
