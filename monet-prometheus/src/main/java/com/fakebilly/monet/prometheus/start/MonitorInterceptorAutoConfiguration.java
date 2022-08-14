package com.fakebilly.monet.prometheus.start;

import com.fakebilly.monet.prometheus.http.MonetHTTPInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MonitorInterceptorAutoConfiguration
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Configuration
public class MonitorInterceptorAutoConfiguration implements WebMvcConfigurer {

    @Autowired
    private MonetHTTPInterceptor monetHTTPInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.monetHTTPInterceptor);
    }
}
