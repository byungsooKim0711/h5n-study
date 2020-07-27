package com.humuson.imc.admin.security.handler;

import com.humuson.imc.admin.security.ImcUserDetails;
import com.humuson.imc.admin.security.ImcUserDetailsService;
import com.humuson.imc.admin.web.domain.admin.repository.WebAdminUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler, RemoteIpHandler {

    private final ImcUserDetailsService imcUserDetailsService;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
        String remoteIp = this.getRemoteIp(request);
        String username = request.getParameter("username");
        log.warn("Login Failed. remote-ip: {}, username: {}, cause: {}", remoteIp, username, authenticationException.getMessage());


        // login failure count++
        try {
            ImcUserDetails failureUserDetail = (ImcUserDetails) imcUserDetailsService.loadUserByUsername(username);
            WebAdminUser failureUser = failureUserDetail.getUser();
            failureUser.addFailCount();
        } catch (UsernameNotFoundException e) {
            
        } catch (Exception e) {
            log.warn("Login failed count update error. username: {}, cause: {}", username, e.getMessage());
        }

        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
