package com.fakebilly.monet.prometheus.http;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.fakebilly.monet.prometheus.monitor.MonetMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * MonetHTTPInterceptor
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Order(Integer.MIN_VALUE)
@Component
public class MonetHTTPInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MonetHTTPInterceptor.class);

    private static final ConcurrentHashMap<String, Boolean> MONITOR_MAP = new ConcurrentHashMap<>();

    private static ThreadLocal<Map<String, Object>> currentThreadLocals = new ThreadLocal<>();
    private static final int CURRENT_MAP_SIZE_DEFAULT = 16;
    private static final String START_TIME_KEY = "start";

    private static final String RECORD_COUNTER_EXCEPTION_KEY = "http_server_requests_method_exception";
    private static final String RECORD_TIMER_KEY = "http_server_requests_method_time";
    private static final String RECORD_TAG_KEY_METHOD = "method";
    private static final String RECORD_TAG_KEY_TYPE = "type";
    private static final int COUNTER_VALUE_DEFAULT = 1;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        long start = System.currentTimeMillis();
        Map<String, Object> currentMap = new HashMap<>(CURRENT_MAP_SIZE_DEFAULT);
        currentMap.put(START_TIME_KEY, start);
        currentThreadLocals.set(currentMap);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        Map<String, Object> currentMap = currentThreadLocals.get();
        long duration = -1L;
        Object start = currentMap.get(START_TIME_KEY);
        if (null != start && NumberUtil.isLong(String.valueOf(start))) {
            duration = System.currentTimeMillis() - Long.valueOf(String.valueOf(start));
        }
        String methodName = "";
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String className = handlerMethod.getBeanType().getName();
            methodName = className + "." + handlerMethod.getMethod().getName();
            this.initMonitorMap(handlerMethod, methodName);
            if (ex != null) {
                logger.info("HTTP afterCompletion counter, methodName {}, ex {}", methodName, ex);
                MonetMonitor.recordCounterWithTags(RECORD_COUNTER_EXCEPTION_KEY, COUNTER_VALUE_DEFAULT, RECORD_TAG_KEY_METHOD, methodName, RECORD_TAG_KEY_TYPE, request.getMethod());
            } else {
                Boolean needMonitor = MONITOR_MAP.get(methodName);
                if (needMonitor) {
                    MonetMonitor.recordTimeWithTags(RECORD_TIMER_KEY, duration, TimeUnit.MILLISECONDS, RECORD_TAG_KEY_METHOD, methodName, RECORD_TAG_KEY_TYPE, request.getMethod());
                }
            }
        }
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response,
                                               Object handler) throws Exception {
    }

    private void initMonitorMap(HandlerMethod handlerMethod, String methodName) {
        if (!MONITOR_MAP.containsKey(methodName)) {
            Annotation[] annotations = handlerMethod.getMethod().getAnnotations();
            Optional<Annotation> any = Arrays.stream(annotations).filter((o) ->
                    StrUtil.equalsAny(o.annotationType().getTypeName(),
                            RequestMapping.class.getTypeName(),
                            GetMapping.class.getTypeName(),
                            PostMapping.class.getTypeName(),
                            PutMapping.class.getTypeName(),
                            DeleteMapping.class.getTypeName(),
                            PatchMapping.class.getTypeName())
            ).findAny();
            MONITOR_MAP.put(methodName, any.isPresent());
        }

    }
}
