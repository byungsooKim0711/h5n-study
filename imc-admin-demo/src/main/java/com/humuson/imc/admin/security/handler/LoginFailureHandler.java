package com.humuson.imc.admin.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.humuson.imc.admin.domain.WebAdminUser;
import com.humuson.imc.admin.security.ImcUserDetails;
import com.humuson.imc.admin.security.ImcUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler, RemoteIpHandler {

    private final ImcUserDetailsService imcUserDetailsService;

    public LoginFailureHandler(ImcUserDetailsService imcUserDetailsService) {
        this.imcUserDetailsService = imcUserDetailsService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
        String remoteIp = this.getRemoteIp(request);
        String username = request.getParameter("username");
        log.warn("Login Failed. remote-ip: {}, username: {}, cause: {}", remoteIp, username, authenticationException.getMessage());


        // TODO: 성능이 별로일 것 같다..
        try {
            ImcUserDetails failureUserDetail = (ImcUserDetails) imcUserDetailsService.loadUserByUsername(username);
            WebAdminUser failureUser = failureUserDetail.getUser();
            failureUser.setFailCount(failureUser.getFailCount() + 1);
            imcUserDetailsService.updateWebAdminUser(failureUser, failureUser.getId());
        } catch (Exception exception) {
            log.warn("Login failed count update error. username: {}, cause: {}", username, exception.getMessage());
        }

        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
