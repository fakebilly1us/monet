package com.fakebilly.monet.idworker.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * IdWorkerFactory
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Component
public class IdWorkerFactory {

    private static final Logger logger = LoggerFactory.getLogger(IdWorkerFactory.class);

    private long workId = 1L;

    private final ConcurrentHashMap<String, IdWorker> localCache = new ConcurrentHashMap();

    private String host;

    public IdWorkerFactory() {
        try {
            this.host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.info("getHostAddress failed, host: {},e: {}", this.host, e);
            Random r = new Random();
            this.host = r.nextInt(100) + "." + r.nextInt(100) + "." + r.nextInt(100) + "." + r.nextInt(100);
            logger.info("getHostAddress failed, host: {}", this.host);
        }
    }

    public String nextStringId() {
        return String.valueOf(this.generateLocalIdWorker().nextId());
    }

    public long nextId() {
        return this.generateLocalIdWorker().nextId();
    }


    private IdWorker generateLocalIdWorker() {
        long dwId = System.currentTimeMillis() & 1023L;
        long workerId = dwId & 31L;
        long datacenterId = dwId >> 5 & 31L;
        if (workerId > 31L || workerId < 0L) {
            workerId %= 32L;
        }
        if (datacenterId > 31L || datacenterId < 0L) {
            datacenterId %= 32L;
        }
        this.setWorkId(workerId);
        String key = "workId".concat(":").concat(Long.toString(workerId));
        return this.putNxLocalCache(key, datacenterId, workerId);
    }


    private IdWorker putNxLocalCache(String key, long datacenterId, long workerId) {
        if (!this.localCache.containsKey(key)) {
            this.localCache.put(key, new IdWorker(workerId, datacenterId));
        }

        return (IdWorker) this.localCache.get(key);
    }

    public long getWorkId() {
        return this.workId;
    }

    public void setWorkId(long workId) {
        this.workId = workId;
    }
}
