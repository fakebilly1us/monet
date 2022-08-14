package com.fakebilly.monet.prometheus.rpc;

import com.fakebilly.monet.prometheus.monitor.MonetMonitor;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.support.RpcUtils;

import java.util.concurrent.TimeUnit;

import static org.apache.dubbo.common.constants.CommonConstants.CONSUMER;

@Activate(group = {CONSUMER}, order = Integer.MIN_VALUE)
public class MonetDubboConsumerMonitorFilter implements Filter {

    private static final String RECORD_COUNTER_EXCEPTION_KEY = "dubbo_consumer_method_exception";
    private static final String RECORD_COUNTER_STATUS_KEY = "dubbo_consumer_method_status";
    private static final String RECORD_COUNTER_TIME_KEY = "dubbo_consumer_method_timer";
    private static final String RECORD_TAG_METHOD_KEY = "method";
    private static final String RECORD_TAG_STATUS_KEY = "status";
    private static final String RECORD_TAG_EXCEPTION_KEY = "exception";
    private static final String RECORD_TAG_REMOTE_APPLICATION_KEY = "remote_application";
    private static final int COUNTER_VALUE_DEFAULT = 1;

    private static final String PARAMETER_REMOTE_APPLICATION_KEY = "remote.application";

    private static final String STATUS_SUCCESS = "success";
    private static final String STATUS_EXCEPTION = "exception";

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        long startTime = System.currentTimeMillis();
        boolean haveException = true;
        String methodName = "";
        Exception ex = null;
        Result result;
        try {
            methodName = invoker.getInterface().getName().concat(".").concat(RpcUtils.getMethodName(invocation));
            result = invoker.invoke(invocation);
            haveException = false;
        } catch (RpcException e) {
            ex = e;
            throw e;
        } finally {
            if (haveException) {
                try {
                    String remoteApplication = invoker.getUrl().getParameter(PARAMETER_REMOTE_APPLICATION_KEY, "");
                    String tagStatus = STATUS_SUCCESS;
                    String exception = "";
                    if (ex != null) {
                        tagStatus = STATUS_EXCEPTION;
                        exception = ex.getClass().getSimpleName();
                        MonetMonitor.recordCounterWithTags(RECORD_COUNTER_EXCEPTION_KEY, COUNTER_VALUE_DEFAULT, RECORD_TAG_METHOD_KEY, methodName, RECORD_TAG_STATUS_KEY, tagStatus, RECORD_TAG_REMOTE_APPLICATION_KEY, remoteApplication, RECORD_TAG_EXCEPTION_KEY, exception);
                    }
                    MonetMonitor.recordCounterWithTags(RECORD_COUNTER_STATUS_KEY, COUNTER_VALUE_DEFAULT, RECORD_TAG_METHOD_KEY, methodName, RECORD_TAG_STATUS_KEY, tagStatus, RECORD_TAG_REMOTE_APPLICATION_KEY, remoteApplication, RECORD_TAG_EXCEPTION_KEY, exception);
                    MonetMonitor.recordTimeWithTags(RECORD_COUNTER_TIME_KEY, System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS, RECORD_TAG_METHOD_KEY, methodName, RECORD_TAG_STATUS_KEY, tagStatus, RECORD_TAG_REMOTE_APPLICATION_KEY, remoteApplication);
                } catch (Exception e) {
                }
            }
        }
        return result;
    }
}
