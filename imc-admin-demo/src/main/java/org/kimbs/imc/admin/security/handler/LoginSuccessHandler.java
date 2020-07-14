package org.kimbs.imc.admin.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.kimbs.imc.admin.security.ImcUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@Slf4j
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements RemoteIpHandler {

    private static final String REQUEST_PARAM_NAME = "remember_username";
    private static final String COOKIE_NAME = "saved_username";
    private static final int DEFAULT_MAX_AGE = 60 * 60 * 24 * 7;
    private int maxAge = DEFAULT_MAX_AGE;

    private final ObjectMapper mapper;

    public LoginSuccessHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String url = getTargetUrlParameter();

        if (url != null) {
            log.info("Url is not null: {}", url);
            super.onAuthenticationSuccess(request, response, authentication);
        } else {
            ImcUserDetails user = (ImcUserDetails) authentication.getPrincipal();
            // User 정보를 write 할 때 비밀번호 정보는 지운다.
            user.getUser().setPassword("");

            HttpSession session = request.getSession();

            // L4를 거쳐올 경우를 고려하여 IP를 얻어냄
            String remoteIp = this.getRemoteIp(request);

            // 실패 카운트 Update
//            URL obj = new URL( "http://" + hostIp + "/api/resetLoginFailCount?username=" + request.getParameter("j_username"));
//            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//            con.setRequestMethod("GET");
//            con.setRequestProperty("User-Agent", "Mozilla/5.0");
//            con.getResponseCode();

            // TODO: 나중에 기존 설정 따라가야한다.
            if (remoteIp.indexOf("xxx.xx.xx.") == 0 || remoteIp.indexOf("xx.xxx.xx.") == 0 || remoteIp.indexOf("0:0:0:0:0:0:0:1") == 0) {
                session.setAttribute("SUPER_ADMIN", "Y");
            } else {
                session.setAttribute("SUPER_ADMIN", "N");
            }

            String remember = request.getParameter(REQUEST_PARAM_NAME);
            if (remember != null) {
                String username = user.getUsername();
                Cookie cookie = new Cookie(COOKIE_NAME, username);
                cookie.setMaxAge(maxAge);
                response.addCookie(cookie);
            } else {
                Cookie cookie = new Cookie(COOKIE_NAME, "");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }

            // 비밀번호 수정 필요시 Redirect
//            ImcUserDetails adminUser = (ImcUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            if("Y".equals(adminUser.getDefaultPassYn())) {
//                response.sendRedirect(request.getContextPath() + "/myInfo/admin?type=d");
//            } else if("Y".equals(adminUser.getModifyDiffYn())) {
//                response.sendRedirect(request.getContextPath() + "/myInfo/admin?type=m");
//            } else {
//            response.sendRedirect(request.getContextPath() + "/dashboard");
//            }

            log.info("Login Success. remote-ip: {}, username: {}, granted-authorities: {}", remoteIp, user.getUsername(), user.getAuthorities());
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(mapper.writeValueAsString(user));
        }

//        httpServletResponse.getWriter().write(authentication.);
    }
}
