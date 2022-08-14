package com.fakebilly.monet.basic.server.config;

import com.fakebilly.monet.basic.server.config.nacos.InterceptorPatternsConfig;
import com.fakebilly.monet.basic.server.interceptor.RestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * InterceptorConfig
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Component
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private InterceptorPatternsConfig interceptorPatternsConfig;

    @Autowired
    private RestInterceptor restInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(restInterceptor).addPathPatterns(interceptorPatternsConfig.getRestPathPatterns())
                .excludePathPatterns(interceptorPatternsConfig.getRestExcludePathPatternsList())
                .order(1);
    }
}
