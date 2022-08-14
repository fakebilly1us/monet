package com.fakebilly.monet.basic.test;

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
 * @github https://github.com/fakebilly-dev/monet
 **/
@SpringBootApplication(scanBasePackages = {"com.fakebilly.monet"})
@NacosPropertySource(dataId = "system", groupId = "BASIC-SERVICE", autoRefreshed = true)
@MapperScan("com.fakebilly.monet.basic.infrastructure.mapper")
@EnableTransactionManagement
@EnableConfigurationProperties
@EnableAsync
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
