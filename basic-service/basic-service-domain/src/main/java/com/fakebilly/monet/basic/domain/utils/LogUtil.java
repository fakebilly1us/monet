package com.fakebilly.monet.basic.domain.utils;

import com.fakebilly.monet.basic.domain.constants.GlobalConstant;
import com.fakebilly.monet.log.ILogger;
import com.fakebilly.monet.log.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LogUtil
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class LogUtil {

    public static ILogger getLogger(Logger logger, String... keywords) {
        return ILoggerFactory.create()
                .setKeywordFirst(null == keywords ? null : (keywords.length >= 1 ? keywords[0] : null))
                .setKeywordSecond(null == keywords ? null : (keywords.length >= 2 ? keywords[1] : null))
                .setKeywordThird(null == keywords ? null : (keywords.length >= 3 ? keywords[2] : null))
                .setLogger(logger)
                .build();
    }

    public static ILogger getLogger(Logger logger, String keywordFirst, String keywordSecond, String keywordThird) {
        return ILoggerFactory.create()
                .setKeywordFirst(keywordFirst)
                .setKeywordSecond(keywordSecond)
                .setKeywordThird(keywordThird)
                .setLogger(logger)
                .build();
    }

    public static ILogger getLogger(Logger logger, String keywordFirst) {
        return ILoggerFactory.create()
                .setKeywordFirst(keywordFirst)
                .setLogger(logger)
                .build();
    }

    public static ILogger getLogger(Logger logger) {
        return ILoggerFactory.create()
                .setKeywordFirst(GlobalConstant.LOG_BUSINESS_TYPE)
                .setKeywordSecond(GlobalConstant.LOG_SUB_BUSINESS_TYPE)
                .setLogger(logger)
                .build();
    }

    public static ILogger getLogger(Class<?> clazz) {
        return ILoggerFactory.create()
                .setKeywordFirst(GlobalConstant.LOG_BUSINESS_TYPE)
                .setKeywordSecond(GlobalConstant.LOG_SUB_BUSINESS_TYPE)
                .setLogger(LoggerFactory.getLogger(clazz))
                .build();

    }
}
