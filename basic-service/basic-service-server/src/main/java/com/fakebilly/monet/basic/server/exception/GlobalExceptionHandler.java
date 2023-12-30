package com.fakebilly.monet.basic.server.exception;

import cn.hutool.core.collection.CollectionUtil;
import com.fakebilly.monet.basic.domain.utils.LogUtil;
import com.fakebilly.monet.core.dto.Response;
import com.fakebilly.monet.core.emuns.CommonCode;
import com.fakebilly.monet.core.exception.BusinessException;
import com.fakebilly.monet.log.ILogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * 全局异常处理
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private ILogger logger = LogUtil.getLogger(log);

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Response exceptionHandler(Exception e) {
        logger.error("GlobalExceptionHandler Exception : {}", e);
        return Response.fail(CommonCode.ERROR.getCode(), CommonCode.ERROR.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(BindException.class)
    public Response validatedBindException(BindException e) {
        logger.info(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return Response.fail(CommonCode.INVALID_PARAMETER.getCode(), message);
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public Response ConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> set = e.getConstraintViolations();
        if (CollectionUtil.isNotEmpty(set)) {
            StringBuilder sb = new StringBuilder();
            set.forEach(error -> {
                sb.append(error.getMessage()).append(';');
            });
            return Response.fail(CommonCode.INVALID_PARAMETER.getCode(), sb.toString());
        }
        logger.info(e.getMessage(), e);
        return Response.fail(CommonCode.INVALID_PARAMETER.getCode(), "参数验证错误");
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response validatedMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        String message = "";
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            errors.forEach(p -> {
                FieldError fieldError = (FieldError) p;
                logger.info("数据校验失败 : object{" + fieldError.getObjectName() + "},字段{" + fieldError.getField() +
                        "},errorMessage{" + fieldError.getDefaultMessage() + "}");

            });
            if (errors.size() > 0) {
                FieldError fieldError = (FieldError) errors.get(0);
                message = fieldError.getDefaultMessage();
            }
        }
        return Response.fail(CommonCode.INVALID_PARAMETER.getCode(), "".equals(message) ? "请填写正确信息" : message);
    }

    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public Response businessException(BusinessException e) {
        logger.info(e.getMessage(), e);
        return Response.fail(e.getErrCode(), e.getMessage());
    }
}
