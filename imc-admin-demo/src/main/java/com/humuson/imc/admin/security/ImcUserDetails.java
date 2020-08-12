package com.humuson.imc.admin.security;

import com.google.common.collect.Sets;
import com.humuson.imc.admin.web.domain.admin.repository.WebAdminUser;
import com.humuson.imc.admin.web.domain.code.ImcGrantedAuthority;
import com.humuson.imc.admin.web.domain.user.repository.WebUserAuthor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ImcUserDetails implements UserDetails {

    private WebAdminUser user;

    public ImcUserDetails(WebAdminUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        // Default 권한
        Set<GrantedAuthority> authList = Sets.newHashSet(
                new SimpleGrantedAuthority(ImcGrantedAuthority.DASHBOARD.getRole()),
                new SimpleGrantedAuthority(ImcGrantedAuthority.STAT.getRole()));

        WebUserAuthor author = user.getWebUserAuthor();

        if (author.isAuthBill()) {
            authList.add(new SimpleGrantedAuthority(ImcGrantedAuthority.BILL.getRole()));
        }

        if (author.isAuthManage()) {
            authList.add(new SimpleGrantedAuthority(ImcGrantedAuthority.MANAGE.getRole()));
        }

        if (author.isAuthOperation()) {
            authList.add(new SimpleGrantedAuthority(ImcGrantedAuthority.OPERATION.getRole()));
        }

        if (author.isAuthUser()) {
            authList.add(new SimpleGrantedAuthority(ImcGrantedAuthority.USER.getRole()));
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
        return user.getFailCount() < 5;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isActiveYn();
    }
}
