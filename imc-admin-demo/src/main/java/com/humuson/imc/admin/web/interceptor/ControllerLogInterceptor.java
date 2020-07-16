package com.humuson.imc.admin.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Slf4j
@Component
public class ControllerLogInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Method: {}, Url-Pattern: {}, timestamp: {}", request.getMethod(), (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE), LocalDateTime.now());
        return super.preHandle(request, response, handler);
    }
}
