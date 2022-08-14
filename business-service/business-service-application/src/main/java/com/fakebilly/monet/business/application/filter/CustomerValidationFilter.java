package com.fakebilly.monet.business.application.filter;

import com.fakebilly.monet.business.domain.enums.CodeEnum;
import com.fakebilly.monet.business.domain.utils.LogUtil;
import com.fakebilly.monet.core.dto.Response;
import com.fakebilly.monet.log.ILogger;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.ConfigUtils;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.validation.Validation;
import org.apache.dubbo.validation.Validator;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

import static org.apache.dubbo.common.constants.CommonConstants.CONSUMER;
import static org.apache.dubbo.common.constants.CommonConstants.PROVIDER;
import static org.apache.dubbo.common.constants.FilterConstants.VALIDATION_KEY;

/**
 * CustomerValidationFilter
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Slf4j
@Activate(group = {PROVIDER, CONSUMER}, value = VALIDATION_KEY, order = 10000)
public class CustomerValidationFilter implements Filter {

    private ILogger logger = LogUtil.getLogger(log);

    private Validation validation;

    public void setValidation(Validation validation) {
        this.validation = validation;
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (validation == null ||
                invocation.getMethodName().startsWith("$") ||
                ConfigUtils.isEmpty(invoker.getUrl().getMethodParameter(invocation.getMethodName(), VALIDATION_KEY))) {
            return invoker.invoke(invocation);
        }
        String interfaceName = invoker.getInterface().getName();
        String methodName = invocation.getMethodName();
        String logPrefix = interfaceName + "." + methodName;
        Response response = new Response();
        try {
            Validator validator = validation.getValidator(invoker.getUrl());
            if (validator != null) {
                validator.validate(invocation.getMethodName(), invocation.getParameterTypes(), invocation.getArguments());
            }
        } catch (RpcException e) {
            logger.error("interface: {}, request:{}, rpcException: {}", logPrefix, invocation.getArguments(), e);
            throw e;
        } catch (ConstraintViolationException e) {
            Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
            logger.info("interface: {}, request:{}, detail: {}", logPrefix, invocation.getArguments(), constraintViolations);
            response.setCode(CodeEnum.INVALID.getCode());
            response.setSuccess(Boolean.FALSE);
            if (constraintViolations.size() <= 0) {
                response.setMessage(CodeEnum.INVALID.getMessage());
            } else {
                response.setMessage(constraintViolations.iterator().next().getMessage());
            }
            return AsyncRpcResult.newDefaultAsyncResult(response, invocation);
        } catch (Throwable t) {
            logger.error("interface: {}, request:{}, throwable: {}", logPrefix, invocation.getArguments(), t);
            response.setCode(CodeEnum.ERROR_DATA.getCode());
            response.setMessage(CodeEnum.ERROR_DATA.getMessage());
            response.setSuccess(Boolean.FALSE);
            return AsyncRpcResult.newDefaultAsyncResult(response, invocation);
        }
        return invoker.invoke(invocation);
    }

}