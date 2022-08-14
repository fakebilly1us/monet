package com.fakebilly.monet.prometheus.rpc;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.fakebilly.monet.prometheus.monitor.MonetMonitor;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.config.spring.extension.SpringExtensionFactory;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.support.RpcUtils;
import org.springframework.context.ApplicationContext;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.apache.dubbo.common.constants.CommonConstants.PROVIDER;

/**
 * MonetDubboMonitorFilter
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Activate(group = {PROVIDER}, order = Integer.MIN_VALUE)
public class MonetDubboMonitorFilter extends ListenableFilter {

    private static final String RECORD_COUNTER_EXCEPTION_KEY = "dubbo_requests_method_exception";
    private static final String RECORD_COUNTER_STATUS_KEY = "dubbo_requests_method_status";
    private static final String RECORD_COUNTER_TIME_KEY = "dubbo_requests_method_time";
    private static final String RECORD_TAG_METHOD_KEY = "method";
    private static final String RECORD_TAG_STATUS_KEY = "status";
    private static final String RECORD_TAG_EXCEPTION_KEY = "exception";
    private static final int COUNTER_VALUE_DEFAULT = 1;

    private static final String MONET_DUBBO_MONITOR_START = "monet.dubbo.monitor.start";

    private static final String STATUS_SUCCESS = "success";
    private static final String STATUS_EXCEPTION = "exception";

    public MonetDubboMonitorFilter() {
        super.listener = new MonetDubboMonitorFilterListener();
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            invocation.setAttachment(MONET_DUBBO_MONITOR_START, String.valueOf(System.currentTimeMillis()));
            return invoker.invoke(invocation);
        } catch (RpcException e) {
            throw e;
        }
    }

    static class MonetDubboMonitorFilterListener implements Filter.Listener {

        @Override
        public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
            Throwable t = null;
            if (appResponse.hasException()) {
                t = appResponse.getException();
            }
            this.record(t, invoker, invocation);
        }

        @Override
        public void onError(Throwable t, Invoker<?> invoker, Invocation invocation) {
            this.record(t, invoker, invocation);
        }

        private void record(Throwable ex, Invoker<?> invoker, Invocation invocation) {
            try {
                String startTimeStr = invocation.getAttachment(MONET_DUBBO_MONITOR_START);
                long startTime = StrUtil.isBlank(startTimeStr) ? System.currentTimeMillis() : Long.parseLong(startTimeStr);
                String methodName = "";
                Object targetObject = null;
                Set<ApplicationContext> applicationContexts = SpringExtensionFactory.getContexts();
                if (CollectionUtil.isNotEmpty(applicationContexts)) {
                    for (ApplicationContext applicationContext : applicationContexts) {
                        targetObject = applicationContext.getBean(invoker.getInterface());
                        if (null != targetObject) {
                            Class<?> targetClass = targetObject.getClass();
                            String targetClassName = targetClass.getName();
                            int index = targetClassName.indexOf("$$");
                            if (index > 0) {
                                targetClassName = targetClassName.substring(0, index);
                            }
                            methodName = targetClassName.concat(".").concat(RpcUtils.getMethodName(invocation));
                            break;
                        }
                    }
                }

                if (StrUtil.isBlank(methodName)) {
                    methodName = invoker.getInterface().getName().concat(".").concat(RpcUtils.getMethodName(invocation));
                }
                String tagStatus = STATUS_SUCCESS;
                String exception = "";
                if (ex != null) {
                    exception = ex.getClass().getSimpleName();
                    tagStatus = STATUS_EXCEPTION;
                    MonetMonitor.recordCounterWithTags(RECORD_COUNTER_EXCEPTION_KEY, COUNTER_VALUE_DEFAULT, RECORD_TAG_METHOD_KEY, methodName, RECORD_TAG_STATUS_KEY, tagStatus, RECORD_TAG_EXCEPTION_KEY, exception);
                }
                MonetMonitor.recordCounterWithTags(RECORD_COUNTER_STATUS_KEY, COUNTER_VALUE_DEFAULT, RECORD_TAG_METHOD_KEY, methodName, RECORD_TAG_STATUS_KEY, tagStatus, RECORD_TAG_EXCEPTION_KEY, exception);
                MonetMonitor.recordTimeWithTags(RECORD_COUNTER_TIME_KEY, System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS, RECORD_TAG_METHOD_KEY, methodName, RECORD_TAG_STATUS_KEY, tagStatus);
            } catch (Throwable t) {
            }
        }
    }
}
