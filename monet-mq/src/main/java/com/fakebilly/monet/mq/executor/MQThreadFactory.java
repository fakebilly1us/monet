package com.fakebilly.monet.mq.executor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * MQThreadFactory
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class MQThreadFactory implements ThreadFactory {

    private static final long STACK_SIZE = 0;

    private static final String POOL_DEFAULT = "MQ_POOL-";
    private static final String THREAD_DEFAULT = "-MQ_THREAD-";
    private static final String SPILT_DEFAULT = "-";

    private static final int INITIAL_VALUE = 1;
    private static final AtomicInteger POOL_NUMBER = new AtomicInteger(INITIAL_VALUE);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(INITIAL_VALUE);
    private final String name;

    public MQThreadFactory() {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        name = POOL_DEFAULT + POOL_NUMBER.getAndIncrement() + THREAD_DEFAULT + threadNumber.getAndIncrement();
    }

    public MQThreadFactory(String namePrefix) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        name = POOL_DEFAULT + namePrefix + SPILT_DEFAULT + POOL_NUMBER.getAndIncrement() + THREAD_DEFAULT + threadNumber.getAndIncrement();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, name, STACK_SIZE);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
