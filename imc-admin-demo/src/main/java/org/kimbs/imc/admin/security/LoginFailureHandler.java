package org.kimbs.imc.admin.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//        URL obj = new URL("http://" + hostIp + "/api/updateLoginFailCount?username=" + request.getParameter("j_username"));
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//        con.setRequestMethod("GET");
//        con.setRequestProperty("User-Agent", "Mozilla/5.0");
//        con.getResponseCode();
//
//        String forwardUrl = request.getContextPath() + "/";
//        response.sendRedirect(forwardUrl);
//		request.setAttribute("errMsg", "로그인에 실패했습니다. 5회 실패 시 로그인이 차단됩니다. 차단될 경우 관리자에 문의해 주세요.");
//
//		request.getRequestDispatcher(forwardUrl).forward(request, response);
    }
}
