package com.fakebilly.monet.business;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * TestApplication
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@SpringBootApplication(scanBasePackages = {"com.fakebilly.monet.business"})
@NacosPropertySource(dataId = "", groupId = "", autoRefreshed = true)
@MapperScan("com.fakebilly.monet.business.infrastructure.mapper")
@EnableTransactionManagement
@EnableConfigurationProperties
@EnableAsync
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
