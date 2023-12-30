package com.fakebilly.monet.basic.domain.utils;


import cn.hutool.core.util.NumberUtil;
import com.fakebilly.monet.basic.domain.enums.CodeEnum;
import com.fakebilly.monet.core.dto.Response;
import com.fakebilly.monet.core.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Assert
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class Assert {

    public static void isNull(Object oj, CodeEnum codeEnum) {
        isTrue(oj == null, codeEnum);
    }

    public static void isNull(Object oj, CodeEnum codeEnum, String msg) {
        isTrue(oj == null, codeEnum, msg);
    }

    public static void notNull(Object oj, CodeEnum codeEnum) {
        if (oj == null) {
            throw new BusinessException(codeEnum.getCode(), codeEnum.getMessage());
        }
    }

    public static void notNull(Object oj, CodeEnum codeEnum, String msg) {
        if (oj == null) {
            throw new BusinessException(codeEnum.getCode(), msg);
        }
    }

    public static void notNull(Object oj, String msg) {
        if (oj == null) {
            throw new BusinessException(CodeEnum.ERROR.getCode(), msg);
        }
    }

    public static void notBlank(CharSequence cs, CodeEnum codeEnum) {
        if (StringUtils.isBlank(cs)) {
            throw new BusinessException(codeEnum.getCode(), codeEnum.getMessage());
        }
    }

    public static void notBlank(CharSequence cs, CodeEnum codeEnum, String msg) {
        if (StringUtils.isBlank(cs)) {
            throw new BusinessException(codeEnum.getCode(), msg);
        }
    }

    public static void notBlank(CharSequence cs, String msg) {
        if (StringUtils.isBlank(cs)) {
            throw new BusinessException(CodeEnum.ERROR.getCode(), msg);
        }
    }

    public static void notEmpty(Collection<?> collection, CodeEnum codeEnum) {
        if (collection == null || collection.isEmpty()) {
            throw new BusinessException(codeEnum.getCode(), codeEnum.getMessage());
        }
    }

    public static void notEmpty(Collection<?> collection, CodeEnum codeEnum, String msg) {
        if (collection == null || collection.isEmpty()) {
            throw new BusinessException(codeEnum.getCode(), msg);
        }
    }

    public static void notEmpty(Collection<?> collection, String msg) {
        if (collection == null || collection.isEmpty()) {
            throw new BusinessException(CodeEnum.ERROR.getCode(), msg);
        }
    }

    public static void notEmpty(Map<?, ?> map, CodeEnum codeEnum, String msg) {
        if (map == null || map.isEmpty()) {
            throw new BusinessException(codeEnum.getCode(), msg);
        }
    }

    public static void isTrue(boolean condition, CodeEnum respEnum) {
        if (!condition) {
            throw new BusinessException(respEnum.getCode(), respEnum.getMessage());
        }
    }

    public static void isTrue(boolean condition, CodeEnum codeEnum, String msg) {
        if (!condition) {
            throw new BusinessException(codeEnum.getCode(), msg);
        }
    }

    public static void isTrue(boolean condition, String msg) {
        if (!condition) {
            throw new BusinessException(CodeEnum.ERROR.getCode(), msg);
        }
    }

    public static void isTrue(Boolean condition, CodeEnum respEnum) {
        if (null == condition || !condition) {
            throw new BusinessException(respEnum.getCode(), respEnum.getMessage());
        }
    }

    public static void isTrue(Boolean condition, CodeEnum codeEnum, String msg) {
        if (null == condition || !condition) {
            throw new BusinessException(codeEnum.getCode(), msg);
        }
    }

    public static void isTrue(Boolean condition, String msg) {
        if (null == condition || !condition) {
            throw new BusinessException(CodeEnum.ERROR.getCode(), msg);
        }
    }

    public static void maxSize(CharSequence cs, int maxSize, CodeEnum respEnum) {
        notBlank(cs, respEnum);
        if (cs.length() >= maxSize) {
            throw new BusinessException(respEnum.getCode(), respEnum.getMessage());
        }
    }

    public static void maxSize(CharSequence cs, int maxSize, CodeEnum respEnum, String msg) {
        notBlank(cs, respEnum, msg);
        if (cs.length() >= maxSize) {
            throw new BusinessException(respEnum.getCode(), msg);
        }
    }

    public static void maxSize(CharSequence cs, int maxSize, String msg) {
        notBlank(cs, msg);
        if (cs.length() >= maxSize) {
            throw new BusinessException(CodeEnum.ERROR.getCode(), msg);
        }
    }

    public static <T> void success(Response<T> response, CodeEnum respEnum) {
        if (response == null || !response.isSuccess()) {
            throw new BusinessException(respEnum.getCode(), respEnum.getMessage());
        }
    }

    public static <T> void success(Response<T> response, String msg) {
        if (response == null || !response.isSuccess()) {
            throw new BusinessException(CodeEnum.ERROR.getCode(), msg);
        }
    }

    public static <T> void success(Response<T> response, CodeEnum respEnum, String msg) {
        if (response == null || !response.isSuccess()) {
            throw new BusinessException(respEnum.getCode(), msg);
        }
    }

    public static void isNum(String carrierId, CodeEnum codeEnum) {
        if (!NumberUtils.isDigits(carrierId)) {
            throw new BusinessException(codeEnum.getCode(), codeEnum.getMessage());
        }
    }

    public static void notZero(int tenantId, CodeEnum invalid, String msg) {
        if (tenantId == 0) {
            throw new BusinessException(invalid.getCode(), msg);
        }
    }

    public static void isNumLong(String id, CodeEnum codeEnum, String msg) {
        if (!NumberUtil.isLong(id)) {
            throw new BusinessException(codeEnum.getCode(), msg);
        }
    }

    public static void isLongId(Long id, CodeEnum codeEnum, String msg) {
        if (null == id || 0 >= id) {
            throw new BusinessException(codeEnum.getCode(), msg);
        }
    }
}
