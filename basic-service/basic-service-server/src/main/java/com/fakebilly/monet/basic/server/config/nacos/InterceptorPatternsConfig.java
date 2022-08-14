package com.fakebilly.monet.basic.server.config.nacos;

import com.fakebilly.monet.log.utils.StrUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * InterceptorPatternsConfig From Nacos
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Data
@Component
@ConfigurationProperties(prefix = "interceptor")
public class InterceptorPatternsConfig {

    /**
     * patterns
     */
    private String pathPatterns = "/**";
    /**
     * patterns exclude
     */
    private String excludePathPatterns;

    /**
     * patterns
     */
    private String restPathPatterns = "/**";
    /**
     * patterns exclude
     */
    private String restExcludePathPatterns;


    public List<String> getExcludePathPatternsList() {
        if (StrUtil.isBlank(excludePathPatterns)) {
            return new ArrayList<>();
        }
        String[] split = excludePathPatterns.split(",");
        return Arrays.stream(split).collect(Collectors.toList());
    }

    public List<String> getRestExcludePathPatternsList() {
        if (StrUtil.isBlank(restExcludePathPatterns)) {
            return new ArrayList<>();
        }
        String[] split = restExcludePathPatterns.split(",");
        return Arrays.stream(split).collect(Collectors.toList());
    }
}
