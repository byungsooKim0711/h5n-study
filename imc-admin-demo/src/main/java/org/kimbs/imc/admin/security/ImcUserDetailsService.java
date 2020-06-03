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
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&& : " + s);
        Optional<WebAdminUser> user = userRepository.findByUserLogin(s);

        System.out.println("###########################################");

        user.orElseThrow(() -> new UsernameNotFoundException("User not exist with name: " + s));

        return user.map(ImcUserDetails::new).get();
    }
}
