package com.humuson.imc.admin.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.humuson.imc.admin.web.common.code.ImcCommonCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class ImcInvalidSessionStrategy implements InvalidSessionStrategy {

    private final ObjectMapper mapper;

    public ImcInvalidSessionStrategy(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ImcCommonCode exception = ImcCommonCode.USER_INVALID_SESSION;
        Object exceptionObject = exception.toBody(LocalDateTime.now(), exception);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(mapper.writeValueAsString(exceptionObject));
        response.sendRedirect("/");
//        response.sendRedirect("/#/");
    }
}
