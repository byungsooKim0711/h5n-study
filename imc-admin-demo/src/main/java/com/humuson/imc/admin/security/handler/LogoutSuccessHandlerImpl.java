package com.humuson.imc.admin.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.humuson.imc.admin.security.ImcUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler, RemoteIpHandler {

    private final ObjectMapper mapper;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            SecurityContext context = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

            if (context != null) {
                Authentication authentication = context.getAuthentication();
                log.info("Logout success. username: {}", ((ImcUserDetails)authentication.getPrincipal()).getUsername());
            }
        }
    }
}
