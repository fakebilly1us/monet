package com.fakebilly.monet.log.runner;

import com.fakebilly.monet.log.config.LogConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * LogRunner
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Component
public class LogRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(LogRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("LogRunner.run");
        LogConfig.initPattern();
    }
}
