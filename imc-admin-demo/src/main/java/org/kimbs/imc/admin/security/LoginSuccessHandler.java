package org.kimbs.imc.admin.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    static final String REQUEST_PARAM_NAME = "remember_username";
    static final String COOKIE_NAME = "saved_username";
    static final int DEFAULT_MAX_AGE = 60 * 60 * 24 * 7;
    private int maxAge = DEFAULT_MAX_AGE;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("SUCCESS");

        System.out.println(authentication.toString());
        System.out.println(authentication.getName());
        System.out.println(authentication.getPrincipal());
        System.out.println(authentication.getCredentials());
        System.out.println(authentication.getDetails());
        System.out.println(authentication.getAuthorities());

        String url = getTargetUrlParameter();

        if (url != null) {
            super.onAuthenticationSuccess(request, response, authentication);
        } else {
            UserDetails user = (UserDetails) authentication.getPrincipal();

            for (GrantedAuthority ga : user.getAuthorities()) {
                log.info("GrantedAuthority : " + ga.getAuthority());
            }

            HttpSession session = request.getSession();

            // L4를 거쳐올 경우를 고려하여 IP를 얻어냄
            String remoteIp = request.getHeader("X-Forwarded-For");
            if (remoteIp == null || remoteIp.length() == 0 || "unknown".equalsIgnoreCase(remoteIp)) {
                remoteIp = request.getHeader("Proxy-Client-IP");
            }
            if (remoteIp == null || remoteIp.length() == 0 || "unknown".equalsIgnoreCase(remoteIp)) {
                remoteIp = request.getHeader("WL-Proxy-Client-IP");
            }
            if (remoteIp == null || remoteIp.length() == 0 || "unknown".equalsIgnoreCase(remoteIp)) {
                remoteIp = request.getHeader("HTTP_CLIENT_IP");
            }
            if (remoteIp == null || remoteIp.length() == 0 || "unknown".equalsIgnoreCase(remoteIp)) {
                remoteIp = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (remoteIp == null || remoteIp.length() == 0 || "unknown".equalsIgnoreCase(remoteIp)) {
                remoteIp = request.getRemoteAddr();
            }

            log.info("User ID : " + user.getUsername() + ", Remote IP : " + remoteIp);
            // 실패 카운트 Update
//            URL obj = new URL( "http://" + hostIp + "/api/resetLoginFailCount?username=" + request.getParameter("j_username"));
//            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//            con.setRequestMethod("GET");
//            con.setRequestProperty("User-Agent", "Mozilla/5.0");
//            con.getResponseCode();

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
        }

//        httpServletResponse.getWriter().write(authentication.);
    }
}
