package com.fakebilly.monet.basic.server.interceptor;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.NumberUtil;
import com.fakebilly.monet.basic.domain.utils.LogUtil;
import com.fakebilly.monet.basic.server.utils.IPUtil;
import com.fakebilly.monet.log.ILogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * RestInterceptor
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Slf4j
@Component
public class RestInterceptor implements HandlerInterceptor {

    private ILogger logger = LogUtil.getLogger(log);

    private static ThreadLocal<Map<String, Object>> currentThreadLocals = new ThreadLocal<>();
    private static final int CURRENT_MAP_SIZE_DEFAULT = 16;
    private static final String START_TIME_KEY = "start";

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestURI = request.getRequestURI();
        String requestURL = null == request.getRequestURL() ? "" : request.getRequestURL().toString();
        long start = System.currentTimeMillis();
        String method = request.getMethod();
        String ip = IPUtil.getRequestIp(request);
        logger.info("RestInterceptor preHandle, requestURI: {}, method: {}, requestURL: {}, ip: {}", requestURI, method, requestURL, ip);
        Map<String, Object> currentMap = new HashMap<>(CURRENT_MAP_SIZE_DEFAULT);
        currentMap.put(START_TIME_KEY, start);
        currentThreadLocals.set(currentMap);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        Map<String, Object> currentMap = currentThreadLocals.get();
        if (MapUtil.isEmpty(currentMap)) {
            return;
        }
        String requestURL = null == request.getRequestURL() ? "" : request.getRequestURL().toString();
        String method = request.getMethod();
        String ip = IPUtil.getRequestIp(request);
        long duration = -1L;
        Object start = currentMap.get(START_TIME_KEY);
        if (null != start && NumberUtil.isLong(String.valueOf(start))) {
            duration = System.currentTimeMillis() - Long.valueOf(String.valueOf(start));
        }
        currentThreadLocals.remove();
        logger.info("RestInterceptor afterCompletion, requestURI: {}, method: {}, requestURL: {}, ip: {}, duration: {}ms", requestURI, method, requestURL, ip, duration);
    }


}
