package com.humuson.imc.admin.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.humuson.imc.admin.config.ImcAdminConfig;
import com.humuson.imc.admin.security.ImcUserDetails;
import com.humuson.imc.admin.web.domain.admin.repository.WebAdminUser;
import com.humuson.imc.admin.web.domain.admin.repository.WebAdminUserRepository;
import com.humuson.imc.admin.web.domain.admin.dto.ImcLoginUser;
import com.humuson.imc.admin.web.domain.admin.dto.ImcLoginUserConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements RemoteIpHandler {

    private static final String REQUEST_PARAM_NAME = "remember_username";
    private static final String COOKIE_NAME = "saved_username";
    private static final int DEFAULT_MAX_AGE = 60 * 60 * 24 * 7;

    private final ObjectMapper mapper;
    private final WebAdminUserRepository adminUserRepository;
    private final ImcLoginUserConverter loginUserConverter;

    // configuration
    private final ImcAdminConfig config;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String url = getTargetUrlParameter();

        if (url != null) {
            log.info("Url is not null: {}", url);
            super.onAuthenticationSuccess(request, response, authentication);
        } else {
            ImcUserDetails user = (ImcUserDetails) authentication.getPrincipal();
            HttpSession session = request.getSession();

            // L4를 거쳐올 경우를 고려하여 IP를 얻어냄
            String remoteIp = this.getRemoteIp(request);

            if (this.checkSuperAdmin(remoteIp, config.getSuperAdminIpList())) {
                session.setAttribute("SUPER_ADMIN", "Y");
            } else {
                session.setAttribute("SUPER_ADMIN", "N");
            }

            String remember = request.getParameter(REQUEST_PARAM_NAME);
            if (remember != null) {
                String username = user.getUsername();
                Cookie cookie = new Cookie(COOKIE_NAME, username);
                cookie.setMaxAge(DEFAULT_MAX_AGE);
                response.addCookie(cookie);
            } else {
                Cookie cookie = new Cookie(COOKIE_NAME, "");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }

            WebAdminUser successUser = user.getUser();

            // Set login failure count to zero.
            try {
                successUser.initializeFailCount();
                adminUserRepository.save(successUser);
            } catch (Exception exception) {
                log.warn("Login failed count update error. username: {}, cause: {}", successUser.getUserLogin(), exception.getMessage());
            }

            log.info("Login Success. remote-ip: {}, username: {}, granted-authorities: {}", remoteIp, user.getUsername(), user.getAuthorities());

            response.setCharacterEncoding("UTF-8");
            ImcLoginUser loginUser = loginUserConverter.toDto(user);
            response.getWriter().write(mapper.writeValueAsString(loginUser));
        }
    }

    private boolean checkSuperAdmin(String remoteIp, List<String> ipList) {
        return ipList.stream().anyMatch(ip -> this.checkSuperAdmin(remoteIp, ip));
    }

    private boolean checkSuperAdmin(String remoteIp, String ip) {
        return ip.indexOf(remoteIp) == 0;
    }
}
