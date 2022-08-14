package com.fakebilly.monet.redis.service.impl;

import com.fakebilly.monet.redis.client.RedisClient;
import com.fakebilly.monet.redis.exception.RedissonException;
import com.fakebilly.monet.redis.exception.UnableToAcquireLockException;
import com.fakebilly.monet.redis.service.IRedisService;
import com.fakebilly.monet.redis.worker.AcquiredLockWorker;
import org.redisson.api.*;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * IRedisService
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class RedisServiceImpl implements IRedisService {

    private static final long DEF_MAX_WAIT_TIME = 100;

    private RedissonClient getClient() {
        return RedisClient.getRedissonClient();
    }

    @Override
    public boolean lock(String lockKey, long lockTime, long maxWaitTime) throws InterruptedException {
        maxWaitTime = maxWaitTime <= 0 ? DEF_MAX_WAIT_TIME : maxWaitTime;
        RLock lock = getClient().getLock(lockKey);
        return lock.tryLock(maxWaitTime, lockTime, TimeUnit.SECONDS);
    }

    @Override
    public <T> T lock(String lockKey, AcquiredLockWorker<T> worker, long lockTime, long maxWaitTime) throws RedissonException {
        RLock lock = getClient().getLock(lockKey);
        maxWaitTime = maxWaitTime <= 0 ? DEF_MAX_WAIT_TIME : maxWaitTime;
        // (可重入锁)最多等待100秒，锁定后经过lockTime秒后自动解锁
        boolean success = false;
        try {
            success = lock.tryLock(maxWaitTime, lockTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RedissonException(e.getMessage());
        }
        if (success) {
            try {
                return worker.invokeAfterLockAcquire();
            } finally {
                lock.unlock();
            }
        } else {
            throw new RedissonException("lock is work, unable to acquire dict");
        }
    }

    @Override
    public void unlock(String lockKey) {
        RLock lock = getClient().getLock(lockKey);
        lock.unlock();
    }

    @Override
    public void set(String key, Serializable value, long timeToLiveInSeconds) {
        RBucket<Serializable> keyObj = getClient().getBucket(key);
        keyObj.set(value, timeToLiveInSeconds, TimeUnit.SECONDS);
    }

    @Override
    public void set(String key, Serializable value) {
        RBucket<Serializable> keyObj = getClient().getBucket(key);
        keyObj.set(value);
    }

    @Override
    public <T extends Serializable> T get(String key) {
        RBucket<T> keyObj = getClient().getBucket(key);
        return keyObj.get();
    }

    @Override
    public boolean exists(String key) {
        RBucket keyObj = getClient().getBucket(key);
        return keyObj.isExists();

    }

    @Override
    public boolean del(String key) {
        RBucket keyObj = getClient().getBucket(key);
        return keyObj.delete();

    }

    @Override
    public long del(String... keys) {
        RKeys keyObjs = getClient().getKeys();
        return keyObjs.delete(keys);
    }

    @Override
    public long delByByPattern(String pattern) {
        RKeys keyObjs = getClient().getKeys();
        return keyObjs.deleteByPattern(pattern);
    }

    @Override
    public void expire(String key, long seconds) {
        RBucket keyObj = getClient().getBucket(key);
        keyObj.expire(seconds, TimeUnit.SECONDS);
    }

    @Override
    public Long ttl(String key) {
        RBucket keyObj = getClient().getBucket(key);
        return keyObj.remainTimeToLive();
    }

    @Override
    public Set<String> keys(String pattern) {
        RKeys keyObjs = getClient().getKeys();
        return (Set<String>) keyObjs.getKeysByPattern(pattern);
    }

    @Override
    public Long incr(String key) {
        RAtomicLong rAtomicLong = getClient().getAtomicLong(key);
        return rAtomicLong.incrementAndGet();
    }

    @Override
    public Long decr(String key) {
        RAtomicLong rAtomicLong = getClient().getAtomicLong(key);
        return rAtomicLong.decrementAndGet();
    }

    @Override
    public Long incrBy(String key, long longValue) {
        RAtomicLong rAtomicLong = getClient().getAtomicLong(key);
        return rAtomicLong.addAndGet(longValue);
    }


    @Override
    public <T extends Serializable> T getSet(String key, T value) {
        RBucket<T> rBucket = getClient().getBucket(key);
        return rBucket.getAndSet(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void hset(String key, Object field, Object value) {
        RMap rMap = getClient().getMap(key);
        rMap.put(field, value);
    }

    @Override
    public Object hget(String key, Object field) {
        RMap<Object, Object> rMap = getClient().getMap(key);
        return rMap.get(field);
    }

    @Override
    public void hmset(String key, Map<Object, Object> hash) {
        RMap<Object, Object> rMap = getClient().getMap(key);
        rMap.putAll(hash);
    }

    @Override
    public List<Object> hmget(String key, Object... fields) {
        RMap<Object, Object> rMap = getClient().getMap(key);
        List<Object> list = new ArrayList<>();
        for (Object mkey : fields) {
            list.add(rMap.get(mkey));

        }
        return list;
    }

    @Override
    public void hdel(String key, Object... fields) {
        RMap<Object, Object> rMap = getClient().getMap(key);
        for (Object mkey : fields) {
            rMap.remove(mkey);
        }
    }

    @Override
    public boolean hexists(String key, Object field) {
        RMap<Object, Object> rMap = getClient().getMap(key);
        return rMap.containsKey(field);
    }

    @Override
    public Long hincrBy(String key, Object field, long value) {
        RMap<Object, Object> rMap = getClient().getMap(key);
        rMap.addAndGet(field, value);
        return (Long) rMap.addAndGet(field, value);
    }

    @Override
    public Double hincrByFloat(String key, Object field, double value) {
        RMap<Object, Object> rMap = getClient().getMap(key);
        rMap.addAndGet(field, value);
        return (Double) rMap.addAndGet(field, value);
    }

    @Override
    public <T> T lpop(String key) {
        RBlockingQueue<T> queue = getClient().getBlockingQueue(key);
        return queue.poll();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void lpush(String key, Object... values) {
        RBlockingDeque deque = getClient().getBlockingDeque(key);
        for (Object value : values) {
            deque.addFirst(value);
        }
    }

    @Override
    public <T> T rpop(String key) {
        RBlockingDeque<T> deque = getClient().getBlockingDeque(key);
        return deque.pollLast();
    }

    @Override
    public <T> T rpoplpush(String srcKey, String dstKey) {
        RBlockingDeque<T> deque = getClient().getBlockingDeque(srcKey);
        return deque.pollLastAndOfferFirstTo(dstKey);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void rpush(String key, Object... values) {
        RList rList = getClient().getList(key);
        rList.addAll(Arrays.asList(values));

    }

    @SuppressWarnings("unchecked")
    @Override
    public void sadd(String key, Object... members) {
        RSet rSet = getClient().getSet(key);
        rSet.addAll(Arrays.asList(members));
    }

    @Override
    public Integer scard(String key) {
        RSet rSet = getClient().getSet(key);
        return rSet.size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Object> smembers(String key) {
        RSet rSet = getClient().getSet(key);
        return rSet.readAll();
    }

    @Override
    public boolean sismember(String key, Object member) {
        RSet rSet = getClient().getSet(key);
        return rSet.contains(member);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean zadd(String key, double score, Object member) {
        RScoredSortedSet rScoredSortedSet = getClient().getScoredSortedSet(key);
        return rScoredSortedSet.add(score, member);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer zadd(String key, Map<Object, Double> scoreMembers) {
        RScoredSortedSet rScoredSortedSet = getClient().getScoredSortedSet(key);
        return rScoredSortedSet.addAll(scoreMembers);
    }

    @Override
    public Integer zcard(String key) {
        RScoredSortedSet rScoredSortedSet = getClient().getScoredSortedSet(key);
        return rScoredSortedSet.size();
    }

    @Override
    public Integer zcount(String key, double min, double max) {
        RScoredSortedSet rScoredSortedSet = getClient().getScoredSortedSet(key);
        // 包含 min 和 max
        return rScoredSortedSet.count(min, true, max, true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Double zincrby(String key, double score, Object member) {
        RScoredSortedSet rScoredSortedSet = getClient().getScoredSortedSet(key);
        return rScoredSortedSet.addScore(member, score);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Object> zrange(String key, int start, int end) {
        RScoredSortedSet rScoredSortedSet = getClient().getScoredSortedSet(key);
        return (Set<Object>) rScoredSortedSet.valueRange(start, end);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Object> zrevrange(String key, int start, int end) {
        RScoredSortedSet rScoredSortedSet = getClient().getScoredSortedSet(key);
        return (Set<Object>) rScoredSortedSet.valueRangeReversed(start, end);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Object> zrangeByScore(String key, double min, double max) {
        RScoredSortedSet rScoredSortedSet = getClient().getScoredSortedSet(key);
        return (Set<Object>) rScoredSortedSet.valueRange(min, true, max, true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer zrank(String key, Object member) {
        RScoredSortedSet rScoredSortedSet = getClient().getScoredSortedSet(key);
        return rScoredSortedSet.rank(member);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer zrevrank(String key, Object member) {
        RScoredSortedSet rScoredSortedSet = getClient().getScoredSortedSet(key);
        return rScoredSortedSet.revRank(member);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean zrem(String key, Object... members) {
        List<Object> list = Arrays.asList(members);
        RScoredSortedSet rScoredSortedSet = getClient().getScoredSortedSet(key);
        return rScoredSortedSet.removeAll(list);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Double zscore(String key, Object member) {
        RScoredSortedSet rScoredSortedSet = getClient().getScoredSortedSet(key);
        return rScoredSortedSet.getScore(member);
    }
}
