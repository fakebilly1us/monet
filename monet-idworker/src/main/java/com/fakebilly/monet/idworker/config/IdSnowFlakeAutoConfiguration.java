package com.fakebilly.monet.idworker.config;

import com.fakebilly.monet.idworker.factory.IdWorkerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * IdSnowFlakeAutoConfiguration
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Configuration
public class IdSnowFlakeAutoConfiguration {

    @Bean
    public IdWorkerFactory getIdWorkerFactory() {
        return new IdWorkerFactory();
    }
}
