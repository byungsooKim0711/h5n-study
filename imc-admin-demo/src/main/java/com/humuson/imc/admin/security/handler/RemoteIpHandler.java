package com.humuson.imc.admin.security.handler;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public interface RemoteIpHandler {

    default String getRemoteIp(HttpServletRequest request) {

        String remoteIp = request.getHeader("X-Forwarded-For");

        if (checkRemoteIp(remoteIp)) {
            remoteIp = request.getHeader("Proxy-Client-IP");
        }
        if (checkRemoteIp(remoteIp)) {
            remoteIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (checkRemoteIp(remoteIp)) {
            remoteIp = request.getHeader("HTTP_CLIENT_IP");
        }
        if (checkRemoteIp(remoteIp)) {
            remoteIp = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (checkRemoteIp(remoteIp)) {
            remoteIp = request.getRemoteAddr();
        }

        return remoteIp;
    }

    default boolean checkRemoteIp(String remoteIp) {
        return StringUtils.isEmpty(remoteIp) || "unknown".equalsIgnoreCase(remoteIp);
    }
}
