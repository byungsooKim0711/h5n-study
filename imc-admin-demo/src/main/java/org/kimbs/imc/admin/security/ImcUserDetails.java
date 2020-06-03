package org.kimbs.imc.admin.security;

import lombok.Getter;
import lombok.Setter;
import org.kimbs.imc.admin.domain.WebAdminUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class ImcUserDetails implements UserDetails {

//    private String username;
//    private String password;
//    private boolean active;
//    private List<GrantedAuthority> authorities;

    private WebAdminUser user;


    public ImcUserDetails(WebAdminUser user) {
//        this.username = user.getUserLogin();
//        this.password = user.getPassword();
//        this.active = "Y".equals(user.getActiveYn());
//        this.authorities = user.getR
        this.user = user;
    }
    public ImcUserDetails() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return "Y".equals(user.getActiveYn());
    }
}
