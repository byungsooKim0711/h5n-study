package org.kimbs.imc.admin.security;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.kimbs.imc.admin.domain.WebAdminUser;
import org.kimbs.imc.admin.domain.WebUserAuthor;
import org.kimbs.imc.admin.domain.code.ImcGrantedAuthority;
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

        // Default 권한
        Set<GrantedAuthority> authList = Sets.newHashSet(
                new SimpleGrantedAuthority(ImcGrantedAuthority.AUTH_DASHBOARD.name()),
                new SimpleGrantedAuthority(ImcGrantedAuthority.AUTH_STAT.name()));

        WebUserAuthor author = user.getWebUserAuthor();

        if ("Y".equals(author.getAuthBill())) {
            authList.add(new SimpleGrantedAuthority(ImcGrantedAuthority.AUTH_BILL.name()));
        }

        if ("Y".equals(author.getAuthManage())) {
            authList.add(new SimpleGrantedAuthority(ImcGrantedAuthority.AUTH_MANAGE.name()));
        }

        if ("Y".equals(author.getAuthOperation())) {
            authList.add(new SimpleGrantedAuthority(ImcGrantedAuthority.AUTH_OPERATION.name()));
        }

        if ("Y".equals(author.getAuthUser())) {
            authList.add(new SimpleGrantedAuthority(ImcGrantedAuthority.AUTH_USER.name()));
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
