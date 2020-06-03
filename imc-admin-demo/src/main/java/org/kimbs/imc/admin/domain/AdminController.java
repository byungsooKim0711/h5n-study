package org.kimbs.imc.admin.domain;

import lombok.extern.slf4j.Slf4j;
import org.kimbs.imc.admin.domain.code.AuthLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
public class AdminController {

    @Autowired
    private WebAdminUserRepository adminUserRepository;

    @Autowired
    private WebUserAuthorRepository webUserAuthorRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/admin")
    public WebAdminUser addWebAdminUser(@RequestBody WebAdminUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        AuthLevel authLevel = user.getWebUserAuthor().getAuthLevel();
        webUserAuthorRepository.findByAuthLevel(authLevel);

        return adminUserRepository.save(user);
    }
}
