package com.fakebilly.monet.basic.application.filter;

import com.alibaba.fastjson.JSONObject;
import com.fakebilly.monet.basic.domain.enums.CodeEnum;
import com.fakebilly.monet.basic.domain.utils.LogUtil;
import com.fakebilly.monet.core.dto.Response;
import com.fakebilly.monet.core.exception.BusinessException;
import com.fakebilly.monet.log.ILogger;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * MonitorServiceFilter
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Slf4j
@Activate(group = {CommonConstants.PROVIDER}, order = 10000)
public class MonitorServiceFilter implements Filter {

    private ILogger logger = LogUtil.getLogger(log);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = null;
        long spendTime = 0L;
        try {
            String interfaceName = invoker.getInterface().getName();
            String methodName = invocation.getMethodName();
            String logPrefix = interfaceName + "." + methodName;
            long startTime = System.currentTimeMillis();
            logger.info("interface: {}, request: {}", logPrefix, invocation.getArguments());
            result = invoker.invoke(invocation);
            if (result.hasException()) {
                String code;
                String msg;
                Throwable e = result.getException();
                if (e instanceof BusinessException) {
                    logger.info("interface: {}, BusinessException: {}", logPrefix, e);
                    code = ((BusinessException) e).getErrCode();
                    code = StringUtils.isBlank(code) ? CodeEnum.ERROR_BIZ_LOGIC.getCode() : code;
                    msg = e.getMessage();
                } else if (e instanceof RuntimeException) {
                    logger.error("interface: {}, RuntimeException: {}", logPrefix, e);
                    code = CodeEnum.ERROR_BIZ_LOGIC.getCode();
                    msg = e.getMessage();
                } else {
                    logger.error("interface: {}, Exception: {}", logPrefix, e);
                    code = CodeEnum.ERROR.getCode();
                    msg = CodeEnum.ERROR.getMessage();
                }
                result = AsyncRpcResult.newDefaultAsyncResult(Response.fail(code, msg), invocation);
            } else {
                spendTime = System.currentTimeMillis() - startTime;
                logger.info("interface: {}, response: {}, spendTime: {}ms", logPrefix, JSONObject.toJSONString(result.getValue()), spendTime);
            }
        } catch (Exception e) {
            logger.error("Exception: {}, request: {}, error: {}, msg: {}", invocation.getClass(), invocation.getArguments(),
                    e, ExceptionUtils.getRootCause(e));
            return result;
        }
        return result;
    }
}