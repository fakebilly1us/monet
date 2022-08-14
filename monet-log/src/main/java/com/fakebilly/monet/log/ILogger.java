package com.fakebilly.monet.log;

import com.fakebilly.monet.log.config.LogConfig;
import com.fakebilly.monet.log.utils.StrUtil;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.Marker;

/**
 * ILogger
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class ILogger {

    public ILogger() {
        this(null);
    }

    public ILogger(Logger logger) {
        this.logger = logger;
    }

    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    public void trace(String msg) {
        initLogParam();
        logger.trace(processMsg(msg));
        destroyLogParam();
    }

    public void trace(String format, Object... arguments) {
        initLogParam();
        logger.trace(processMsg(format), arguments);
        destroyLogParam();
    }

    public void trace(String msg, Throwable t) {
        initLogParam();
        logger.trace(processMsg(msg), t);
        destroyLogParam();
    }

    public boolean isTraceEnabled(Marker marker) {
        return logger.isTraceEnabled(marker);
    }

    public void trace(Marker marker, String msg) {
        initLogParam();
        logger.trace(marker, processMsg(msg));
        destroyLogParam();
    }

    public void trace(Marker marker, String format, Object... argArray) {
        initLogParam();
        logger.trace(marker, processMsg(format), argArray);
        destroyLogParam();
    }

    public void trace(Marker marker, String msg, Throwable t) {
        initLogParam();
        logger.trace(marker, processMsg(msg), t);
        destroyLogParam();
    }

    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    public void debug(String msg) {
        initLogParam();
        logger.debug(processMsg(msg));
        destroyLogParam();
    }

    public void debug(String format, Object... arguments) {
        initLogParam();
        logger.debug(processMsg(format), arguments);
        destroyLogParam();
    }

    public void debug(String msg, Throwable t) {
        initLogParam();
        logger.debug(processMsg(msg), t);
        destroyLogParam();
    }

    public boolean isDebugEnabled(Marker marker) {
        return logger.isDebugEnabled(marker);
    }

    public void debug(Marker marker, String msg) {
        initLogParam();
        logger.debug(marker, processMsg(msg));
        destroyLogParam();
    }

    public void debug(Marker marker, String format, Object... arguments) {
        initLogParam();
        logger.debug(marker, processMsg(format), arguments);
        destroyLogParam();
    }

    public void debug(Marker marker, String msg, Throwable t) {
        initLogParam();
        logger.debug(marker, processMsg(msg), t);
        destroyLogParam();
    }

    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    public void info(String msg) {
        initLogParam();
        logger.info(processMsg(msg));
        destroyLogParam();
    }

    public void info(String format, Object... arguments) {
        initLogParam();
        logger.info(processMsg(format), arguments);
        destroyLogParam();
    }

    public void info(String msg, Throwable t) {
        initLogParam();
        logger.info(processMsg(msg), t);
        destroyLogParam();
    }

    public boolean isInfoEnabled(Marker marker) {
        return logger.isInfoEnabled(marker);
    }

    public void info(Marker marker, String msg) {
        initLogParam();
        logger.info(marker, processMsg(msg));
        destroyLogParam();
    }

    public void info(Marker marker, String format, Object... arguments) {
        initLogParam();
        logger.info(marker, processMsg(format), arguments);
        destroyLogParam();
    }

    public void info(Marker marker, String msg, Throwable t) {
        initLogParam();
        logger.info(marker, processMsg(msg), t);
        destroyLogParam();
    }

    public void alarm(String msg) {
        initLogParam();
        logger.info(processMsg(msg));
        destroyLogParam();
    }

    public void alarm(String format, Object... arguments) {
        initLogParam();
        logger.info(processMsg(format), arguments);
        destroyLogParam();
    }

    public void alarm(String msg, Throwable t) {
        initLogParam();
        logger.info(processMsg(msg), t);
        destroyLogParam();
    }

    public void alarm(Marker marker, String msg) {
        initLogParam();
        logger.info(marker, processMsg(msg));
        destroyLogParam();
    }

    public void alarm(Marker marker, String format, Object... arguments) {
        initLogParam();
        logger.info(marker, processMsg(format), arguments);
        destroyLogParam();
    }

    public void alarm(Marker marker, String msg, Throwable t) {
        initLogParam();
        logger.info(marker, processMsg(msg), t);
        destroyLogParam();
    }

    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    public void warn(String msg) {
        initLogParam();
        logger.warn(processMsg(msg));
        destroyLogParam();
    }

    public void warn(String format, Object... arguments) {
        initLogParam();
        logger.warn(processMsg(format), arguments);
        destroyLogParam();
    }

    public void warn(String msg, Throwable t) {
        initLogParam();
        logger.warn(processMsg(msg), t);
        destroyLogParam();
    }

    public boolean isWarnEnabled(Marker marker) {
        return logger.isWarnEnabled(marker);
    }

    public void warn(Marker marker, String msg) {
        initLogParam();
        logger.warn(marker, processMsg(msg));
        destroyLogParam();
    }

    public void warn(Marker marker, String format, Object... arguments) {
        initLogParam();
        logger.warn(marker, processMsg(format), arguments);
        destroyLogParam();
    }

    public void warn(Marker marker, String msg, Throwable t) {
        initLogParam();
        logger.warn(marker, processMsg(msg), t);
        destroyLogParam();
    }

    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    public void error(String msg) {
        initLogParam();
        logger.error(processMsg(msg));
        destroyLogParam();
    }

    public void error(String format, Object... arguments) {
        initLogParam();
        logger.error(processMsg(format), arguments);
        destroyLogParam();
    }

    public void error(String msg, Throwable t) {
        initLogParam();
        logger.error(processMsg(msg), t);
        destroyLogParam();
    }

    public boolean isErrorEnabled(Marker marker) {
        return logger.isErrorEnabled(marker);
    }

    public void error(Marker marker, String msg) {
        initLogParam();
        logger.error(marker, processMsg(msg));
        destroyLogParam();
    }

    public void error(Marker marker, String format, Object... arguments) {
        initLogParam();
        logger.error(marker, processMsg(format), arguments);
        destroyLogParam();
    }

    public void error(Marker marker, String msg, Throwable t) {
        initLogParam();
        logger.error(marker, processMsg(msg), t);
        destroyLogParam();
    }

    /**
     * 获取最原始被调用的堆栈信息
     */
    private StackTraceElement findCaller() {
        // 获取堆栈信息
        StackTraceElement[] callStack = Thread.currentThread().getStackTrace();
        if (null == callStack) {
            return null;
        }
        // 最原始被调用的堆栈信息
        StackTraceElement caller = null;
        // 日志类名称
        String logClassName = LogConfig.EXT_LOGGER_CLASS_NAME;
        // 循环遍历到日志类标识
        boolean isEachLogClass = false;
        // 遍历堆栈信息，获取出最原始被调用的方法信息
        for (StackTraceElement s : callStack) {
            // 遍历到日志类
            if (logClassName.equals(s.getClassName())) {
                isEachLogClass = true;
            }
            // 下一个非日志类的堆栈，就是最原始被调用的方法
            if (isEachLogClass) {
                if (!logClassName.equals(s.getClassName())) {
                    isEachLogClass = false;
                    caller = s;
                    break;
                }
            }
        }
        return caller;
    }

    private String getCallerInfo() {
        StringBuilder sb = new StringBuilder();
        StackTraceElement caller = findCaller();
        if (null != caller) {
            sb.append(caller.getClassName()).append(".")
                    .append(caller.getMethodName()).append("(")
                    .append(caller.getFileName()).append(":")
                    .append(caller.getLineNumber()).append(") ");
        }
        sb.append("- ");
        return sb.toString();
    }

    private String processMsg(String msg) {
        return new StringBuilder(getCallerInfo()).append(msg).toString();
    }

    private void initLogParam() {
        if (StrUtil.isNotBlank(keywordFirst)) {
            MDC.put("keywordFirst", keywordFirst);
        }
        if (StrUtil.isNotBlank(keywordSecond)) {
            MDC.put("keywordSecond", keywordSecond);
        }
        if (StrUtil.isNotBlank(keywordThird)) {
            MDC.put("keywordThird", keywordThird);
        }
    }

    private void destroyLogParam() {
        MDC.remove("keywordFirst");
        MDC.remove("keywordSecond");
        MDC.remove("keywordThird");
    }

    private Logger logger;

    /**
     * 关键词 1
     */
    private String keywordFirst;

    /**
     * 关键词 2
     */
    private String keywordSecond;

    /**
     * 关键词 3
     */
    private String keywordThird;

    public ILogger setLogger(Logger logger) {
        this.logger = logger;
        return this;
    }

    public ILogger setKeywordFirst(String keywordFirst) {
        this.keywordFirst = keywordFirst;
        return this;
    }

    public ILogger setKeywordSecond(String keywordSecond) {
        this.keywordSecond = keywordSecond;
        return this;
    }

    public ILogger setKeywordThird(String keywordThird) {
        this.keywordThird = keywordThird;
        return this;
    }


}
