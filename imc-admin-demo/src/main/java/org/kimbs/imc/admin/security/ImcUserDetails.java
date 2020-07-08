package org.kimbs.imc.admin.security;

import lombok.Getter;
import lombok.Setter;
import org.kimbs.imc.admin.domain.WebAdminUser;
import org.kimbs.imc.admin.domain.WebUserAuthor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
public class ImcUserDetails implements UserDetails {

    private WebAdminUser user;

    public ImcUserDetails(WebAdminUser user) {
        this.user = user;
    }
    public ImcUserDetails() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<GrantedAuthority> authList = new HashSet<>();
        WebUserAuthor author = user.getWebUserAuthor();

        if ("Y".equals(author.getAuthBill())) {
            authList.add(new SimpleGrantedAuthority("AUTH_BILL"));
        }

        if ("Y".equals(author.getAuthManage())) {
            authList.add(new SimpleGrantedAuthority("AUTH_MANAGE"));
        }

        if ("Y".equals(author.getAuthOperation())) {
            authList.add(new SimpleGrantedAuthority("AUTH_OPERATION"));
        }

        if ("Y".equals(author.getAuthUser())) {
            authList.add(new SimpleGrantedAuthority("AUTH_USER"));
        }

        return authList;
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
