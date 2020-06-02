package org.kimbs.imc.admin.security;

import lombok.RequiredArgsConstructor;
import org.kimbs.imc.admin.domain.WebAdminUser;
import org.kimbs.imc.admin.domain.WebAdminUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImcUserDetailsService implements UserDetailsService {

    private final WebAdminUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<WebAdminUser> user = userRepository.findByUserLogin(s);

        user.orElseThrow(() -> new UsernameNotFoundException("NOT FOUNT: " + s));

        return user.map(ImcUserDetails::new).get();
    }
}
