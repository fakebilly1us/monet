package com.fakebilly.monet.business.domain.utils;

import com.fakebilly.monet.core.exception.BusinessException;
import com.fakebilly.monet.core.exception.CommonExceptionCode;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * ApplicationUtil
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Component
public class ApplicationUtil implements ApplicationContextAware {

    private static final String NOT_EXIST_MSG = "bean.not.exist";
    private static final String NOT_REPO_MSG = "clazz.not.repository";
    private static final String NOT_SERVICE_MSG = "clazz.not.service";

    private static ApplicationContext applicationContext;

    /**
     * 获取一个Bean实例
     * @param clazz 目标Bean实例类型
     * @param <T>   目标Bean范型限制
     * @return Bean实例, 如果Bean不存在, 则抛出异常
     */
    @SuppressWarnings("unchecked")
    public static <T> T bean(Class<T> clazz) {
        if (applicationContext == null) {
            throw new BusinessException(CommonExceptionCode.DEFAULT_ERROR.getCode(), NOT_EXIST_MSG);
        }
        Map beans = applicationContext.getBeansOfType(clazz);
        T t = (T) beans.values().stream().findFirst().orElse(null);

        if (t == null) {
            throw new BusinessException(CommonExceptionCode.DEFAULT_ERROR.getCode(), NOT_EXIST_MSG);
        }
        return t;
    }

    /**
     * 获取一个Bean实例
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }
        Map beans = applicationContext.getBeansOfType(clazz);
        return (T) beans.values().stream().findFirst().orElse(null);
    }

    /**
     * 获取一个Bean实例
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T service(Class<T> clazz) {
        boolean isSpringService = Stream.of(clazz.getAnnotations()).anyMatch(item ->
                item.annotationType().getName().equals(Service.class.getName())
        );
        if (!isSpringService) {
            throw new BusinessException(CommonExceptionCode.DEFAULT_ERROR.getCode(), NOT_SERVICE_MSG);
        }
        return bean(clazz);
    }

    /**
     * 获取一个Bean实例
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T repository(Class<T> clazz) {
        boolean isSpringRepository = Stream.of(clazz.getAnnotations()).anyMatch(item ->
                item.annotationType().getName().equals(Repository.class.getName())
        );
        if (!isSpringRepository) {
            throw new BusinessException(CommonExceptionCode.DEFAULT_ERROR.getCode(), NOT_REPO_MSG);
        }
        return bean(clazz);
    }

    /**
     * 获取一个Bean实例
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> allBeans(Class<T> clazz) {
        if (applicationContext == null) {
            throw new BusinessException(CommonExceptionCode.DEFAULT_ERROR.getCode(), NOT_EXIST_MSG);
        }
        Map<String, T> beans = applicationContext.getBeansOfType(clazz);
        List<T> t = new ArrayList<>(beans.values());
        if (t == null) {
            throw new BusinessException(CommonExceptionCode.DEFAULT_ERROR.getCode(), NOT_EXIST_MSG);
        }
        return t;
    }

    /**
     * 获取一个Map类型的Bean实例
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> beanMap(Class<T> clazz) {
        if (applicationContext == null) {
            throw new BusinessException(CommonExceptionCode.DEFAULT_ERROR.getCode(), NOT_EXIST_MSG);
        }
        Map<String, T> beans = applicationContext.getBeansOfType(clazz);
        if (beans == null) {
            throw new BusinessException(CommonExceptionCode.DEFAULT_ERROR.getCode(), NOT_EXIST_MSG);
        }
        return beans;
    }

    /**
     * 获取一个Map类型的Bean实例
     * @param type
     * @param <T>
     * @return
     * @throws BeansException
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return applicationContext.getBeansOfType(type);
    }

    /**
     * 根据Bean名称获取一个实例
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (ApplicationUtil.applicationContext == null) {
            ApplicationUtil.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
