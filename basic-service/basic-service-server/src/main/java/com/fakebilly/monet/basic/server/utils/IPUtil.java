package com.fakebilly.monet.basic.server.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * IPUtil
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class IPUtil {

    private static final String UNKNOWN_IP = "unknown";

    public static String getRequestIp(HttpServletRequest request) {
        if (request == null) {
            return "0.0.0.0";
        }
        String realIp = request.getHeader("X-Real-IP");
        String forwarded = request.getHeader("X-Forwarded-For");

        if (StringUtils.isNotEmpty(forwarded) && !UNKNOWN_IP.equalsIgnoreCase(forwarded)) {
            // 多次反向代理后会有多个ip值, 第一个ip才是真实ip
            int index = forwarded.indexOf(",");
            if (index != -1) {
                return forwarded.substring(0, index);
            } else {
                return forwarded;
            }
        }
        forwarded = realIp;
        if (StringUtils.isNotEmpty(forwarded) && !UNKNOWN_IP.equalsIgnoreCase(forwarded)) {
            return forwarded;
        }
        if (StringUtils.isBlank(forwarded) || UNKNOWN_IP.equalsIgnoreCase(forwarded)) {
            forwarded = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(forwarded) || UNKNOWN_IP.equalsIgnoreCase(forwarded)) {
            forwarded = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(forwarded) || UNKNOWN_IP.equalsIgnoreCase(forwarded)) {
            forwarded = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(forwarded) || UNKNOWN_IP.equalsIgnoreCase(forwarded)) {
            forwarded = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(forwarded) || UNKNOWN_IP.equalsIgnoreCase(forwarded)) {
            forwarded = request.getRemoteAddr();
        }
        return forwarded;
    }

}
