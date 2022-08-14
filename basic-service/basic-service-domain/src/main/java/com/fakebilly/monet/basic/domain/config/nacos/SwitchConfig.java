package com.fakebilly.monet.basic.domain.config.nacos;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * SwitchConfig From Nacos
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Data
@Component
@ConfigurationProperties(prefix = "switch")
public class SwitchConfig {

    /**
     * limited
     */
    private Boolean limited = Boolean.FALSE;

}
