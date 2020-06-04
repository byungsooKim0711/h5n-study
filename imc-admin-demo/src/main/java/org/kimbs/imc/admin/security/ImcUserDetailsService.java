package org.kimbs.imc.admin.security;

import lombok.RequiredArgsConstructor;
import org.kimbs.imc.admin.domain.WebAdminUser;
import org.kimbs.imc.admin.domain.WebAdminUserRepository;
import org.kimbs.imc.admin.domain.WebUserAuthor;
import org.kimbs.imc.admin.domain.WebUserAuthorRepository;
import org.kimbs.imc.admin.domain.code.AuthLevel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImcUserDetailsService implements UserDetailsService {

    private final WebUserAuthorRepository webUserAuthorRepository;
    private final WebAdminUserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<WebAdminUser> user = userRepository.findByUserLogin(s);

        user.orElseThrow(() -> new UsernameNotFoundException("User not exist with name: " + s));

        return user.map(ImcUserDetails::new).get();
    }

    public WebAdminUser insertWebAdminUser(WebAdminUser webAdminUser) {
        webAdminUser.setPassword(passwordEncoder.encode(webAdminUser.getPassword()));

        AuthLevel authLevel = webAdminUser.getWebUserAuthor().getAuthLevel();
        WebUserAuthor author = webUserAuthorRepository.findByAuthLevel(authLevel);

        if (author != null) {
            webAdminUser.setWebUserAuthor(author);
        }

        return userRepository.save(webAdminUser);
    }

    public WebAdminUser updateWebAdminUser(WebAdminUser webAdminUser, Long id) {
        return null;
    }

    public WebAdminUser deleteWebAdminUser(Long id) {
        return null;
    }
}
