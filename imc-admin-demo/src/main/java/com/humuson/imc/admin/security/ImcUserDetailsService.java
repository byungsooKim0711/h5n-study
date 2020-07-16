package com.humuson.imc.admin.security;

import com.humuson.imc.admin.domain.WebAdminUser;
import com.humuson.imc.admin.domain.WebAdminUserRepository;
import com.humuson.imc.admin.domain.WebUserAuthor;
import lombok.RequiredArgsConstructor;
import com.humuson.imc.admin.domain.WebUserAuthorRepository;
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

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<WebAdminUser> user = adminUserRepository.findByUserLogin(s);

        user.orElseThrow(() -> new UsernameNotFoundException("User not exist with name: " + s));

        return user.map(ImcUserDetails::new).get();
    }

    public List<WebAdminUser> selectAllWebAdminUser() {
        List<WebAdminUser> adminList = adminUserRepository.findAll();
        return adminList;
    }

    public List<WebUserAuthor> selectAllWebUserAuthor() {
        List<WebUserAuthor> authorList = webUserAuthorRepository.findAll();

        return authorList;
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
        webAdminUser.setPassword(passwordEncoder.encode(webAdminUser.getUserLogin() + "!@#$"));

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
        long l = adminUserRepository.updateWebAdminUser(webAdminUser, id);

        return adminUserRepository.findById(id).orElseThrow(() -> new Exception("Unknown admin user id: " + id));
    }
}
