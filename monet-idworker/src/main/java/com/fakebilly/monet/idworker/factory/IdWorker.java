package com.fakebilly.monet.idworker.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IdWorker
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class IdWorker {

    private static final Logger logger = LoggerFactory.getLogger(IdWorker.class);

    private long workerId;
    private long dataCenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public IdWorker(long workerId, long dataCenterId) {
        if (workerId <= 31L && workerId >= 0L) {
            if (dataCenterId <= 31L && dataCenterId >= 0L) {
                this.workerId = workerId;
                this.dataCenterId = dataCenterId;
            } else {
                throw new IllegalArgumentException(String.format("dataCenterId cannot be greater than %d or less than 0", 31L));
            }
        } else {
            throw new IllegalArgumentException(String.format("workerId cannot be greater than %d or less than 0", 31L));
        }
    }

    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (timestamp < this.lastTimestamp) {
            logger.info("clock moved backwards, refusing to generate id for {} milliseconds", this.lastTimestamp - timestamp);
        }

        if (this.lastTimestamp == timestamp) {
            this.sequence = this.sequence + 1L & 4095L;
            if (this.sequence == 0L) {
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0L;
        }

        this.lastTimestamp = timestamp;
        return timestamp - 1288834974657L << 22 | this.dataCenterId << 17 | this.workerId << 12 | this.sequence;
    }

    protected long tilNextMillis(long lastTimestampParam) {
        long timestamp;
        for (timestamp = this.timeGen(); timestamp <= lastTimestampParam; timestamp = this.timeGen()) {
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public long getDataCenterId() {
        return this.dataCenterId;
    }
}
