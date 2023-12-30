package com.fakebilly.monet.log;

import com.fakebilly.monet.log.utils.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ILoggerFactory
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class ILoggerFactory {

    private String keywordFirst;

    private String keywordSecond;

    private String keywordThird;

    private Logger logger;

    public static ILoggerFactory create() {
        return new ILoggerFactory();
    }

    protected ILoggerFactory() {
        super();
    }

    public ILoggerFactory setKeywordFirst(String keywordFirst) {
        this.keywordFirst = keywordFirst;
        return this;
    }

    public ILoggerFactory setKeywordSecond(String keywordSecond) {
        this.keywordSecond = keywordSecond;
        return this;
    }

    public ILoggerFactory setKeywordThird(String keywordThird) {
        this.keywordThird = keywordThird;
        return this;
    }

    public ILoggerFactory setLogger(Logger logger) {
        this.logger = logger;
        return this;
    }

    public ILogger build() {
        if (null == logger) {
            logger = LoggerFactory.getLogger(ILogger.class);
        }
        ILogger iLogger = new ILogger(logger);
        if (StrUtil.isNotBlank(keywordFirst)) {
            iLogger.setKeywordFirst(keywordFirst);
        }
        if (StrUtil.isNotBlank(keywordSecond)) {
            iLogger.setKeywordSecond(keywordSecond);
        }
        if (StrUtil.isNotBlank(keywordThird)) {
            iLogger.setKeywordThird(keywordThird);
        }
        return iLogger;
    }

}
