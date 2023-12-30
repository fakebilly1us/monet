package com.fakebilly.monet.redis.service;

import com.fakebilly.monet.redis.exception.RedissonException;
import com.fakebilly.monet.redis.worker.AcquiredLockWorker;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * IRedisService
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public interface IRedisService {
    
    /**
     * 分布式锁
     * 加锁以后 最多等待{maxWaitTime}, 上锁以后{lockTime}自动解锁
     * @param lockKey
     * @param lockTime 自动解锁时间 时间单位: 秒
     * @param maxWaitTime 最多等待时间 时间单位: 秒
     * @return
     * @throws InterruptedException
     */
    boolean lock(String lockKey, long lockTime, long maxWaitTime) throws InterruptedException;

    /**
     * 分布式锁
     * 加锁以后 最多等待{maxWaitTime}, 上锁以后{lockTime}自动解锁
     * @param lockKey
     * @param worker  业务逻辑处理
     * @param lockTime 自动解锁时间 时间单位: 秒
     * @param maxWaitTime 最多等待时间 时间单位: 秒
     * @param <T>
     * @return
     */
    <T> T lock(String lockKey, AcquiredLockWorker<T> worker, long lockTime, long maxWaitTime) throws RedissonException;


    /**
     *解锁
     * @param lockKey
     * @return
     */
    void unlock(String lockKey);

    /**
     * 设置缓存
     * @param key
     * @param value
     * @param timeToLiveInSeconds 时间单位: 秒
     */
    void set(String key, Serializable value, long timeToLiveInSeconds);

    /**
     * 设置缓存
     * @param key
     * @param value
     */
    void set(String key, Serializable value);

    /**
     * 获取缓存
     * @param key
     * @param <T>
     * @return
     */
    <T extends Serializable> T get(String key);

    /**
     * 指定的key 是否存在
     * @param key
     * @return
     */
    boolean exists(String key);

    /**
     * 删除一个缓存 不存在的 key 会被忽略
     * @param key
     */
    boolean del(String key);

    /**
     * 删除多个缓存 不存在的 key 会被忽略
     * @param keys
     * @return 返回实际删除缓存的个数
     */
    long del(String... keys);

    /**
     * 给定模式 pattern 的 key 删除缓存
     * KEYS * 匹配数据库中所有 key
     * KEYS h?llo 匹配 hello ,  hallo 和 hxllo 等
     * KEYS h*llo 匹配 hllo 和 heeeeello 等
     * KEYS h[ae]llo 匹配 hello 和 hallo , 但不匹配 hillo
     * 特殊符号用 \ 隔开
     * @param pattern 正则表达式
     * @return 返回实际删除缓存的个数
     */
    long delByByPattern(String pattern);

    /**
     * 为给定 key 设置生存时间, 当 key 过期时(生存时间为 0 ), 它会被自动删除
     * @param key
     * @param seconds
     */
    void expire(String key, long seconds);

    /**
     * 以秒为单位, 返回给定 key 的剩余生存时间(TTL, time to live)
     * @param key
     * @return
     */
    Long ttl(String key);

    /**
     * 查找所有符合给定模式 pattern 的 key
     * KEYS * 匹配数据库中所有 key
     * KEYS h?llo 匹配 hello ,  hallo 和 hxllo 等
     * KEYS h*llo 匹配 hllo 和 heeeeello 等
     * KEYS h[ae]llo 匹配 hello 和 hallo , 但不匹配 hillo
     * 特殊符号用 \ 隔开
     * @param pattern
     * @return
     */
    Set<String> keys(String pattern);

    /**
     * 将 key 中储存的数字值增一
     * 如果 key 不存在, 那么 key 的值会先被初始化为 0 , 然后再执行 INCR 操作
     * 如果值包含错误的类型, 或字符串类型的值不能表示为数字, 那么返回一个错误
     *本操作的值限制在 64 位(bit)有符号数字表示之内
     * @param key
     * @return
     */
    Long incr(String key);

    /**
     * 将 key 中储存的数字值减一
     * 如果 key 不存在, 那么 key 的值会先被初始化为 0 , 然后再执行 DECR 操作
     * 如果值包含错误的类型, 或字符串类型的值不能表示为数字, 那么返回一个错误
     * 本操作的值限制在 64 位(bit)有符号数字表示之内
     * 关于递增(increment) / 递减(decrement)操作的更多信息, 请参见 INCR 命令
     * @param key
     * @return
     */
    Long decr(String key);

    /**
     * 将 key 所储存的值加上增量 increment
     * 如果 key 不存在, 那么 key 的值会先被初始化为 0 , 然后再执行 INCRBY 命令
     * 如果值包含错误的类型, 或字符串类型的值不能表示为数字, 那么返回一个错误
     * 本操作的值限制在 64 位(bit)有符号数字表示之内
     * 关于递增(increment) / 递减(decrement)操作的更多信息, 参见 INCR 命令
     * @param key
     * @param longValue
     * @return
     */
    Long incrBy(String key, long longValue);


    /**
     * 将给定 key 的值设为 value , 并返回 key 的旧值(old value)
     * 当 key 存在但不是字符串类型时, 返回一个错误
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    <T extends Serializable> T getSet(String key, T value);

    /**
     * 将哈希表 key 中的域 field 的值设为 value
     * 如果 key 不存在, 一个新的哈希表被创建并进行 HSET 操作
     * 如果域 field 已经存在于哈希表中, 旧值将被覆盖
     * @param key
     * @param field
     * @param value
     */
    void hset(String key, Object field, Object value);
    
    /**
     * 返回哈希表 key 中给定域 field 的值
     * @param key
     * @param field
     * @return
     */
    Object hget(String key, Object field);

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中
     * 此命令会覆盖哈希表中已存在的域
     * 如果 key 不存在, 一个空哈希表被创建并执行 HMSET 操作
     * @param key
     * @param hash
     * @return
     */
    void hmset(String key, Map<Object, Object> hash);

    /**
     * 返回哈希表 key 中, 一个或多个给定域的值
     * 如果给定的域不存在于哈希表, 那么返回一个 nil 值
     * 因为不存在的 key 被当作一个空哈希表来处理, 所以对一个不存在的 key 进行 HMGET 操作将返回一个只带有 nil 值的表
     * @param key
     * @param fields
     * @return
     */
    List<Object> hmget(String key, Object... fields);

    /**
     * 删除哈希表 key 中的一个或多个指定域, 不存在的域将被忽略
     * @param key
     * @param fields
     * @return
     */
    void hdel(String key, Object... fields);

    /**
     * 查看哈希表 key 中, 给定域 field 是否存在
     * @param key
     * @param field
     * @return
     */
    boolean hexists(String key, Object field);

    /**
     * 为哈希表 key 中的域 field 的值加上增量 increment
     * 增量也可以为负数, 相当于对给定域进行减法操作
     * 如果 key 不存在, 一个新的哈希表被创建并执行 HINCRBY 命令
     * 如果域 field 不存在, 那么在执行命令前, 域的值被初始化为 0
     * 对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误
     * 本操作的值被限制在 64 位(bit)有符号数字表示之内
     * @param key
     * @param field
     * @param value
     * @return
     */
    Long hincrBy(String key, Object field, long value);

    /**
     * 为哈希表 key 中的域 field 加上浮点数增量 increment
     * 如果哈希表中没有域 field , 那么 HINCRBYFLOAT 会先将域 field 的值设为 0 , 然后再执行加法操作
     * 如果键 key 不存在, 那么 HINCRBYFLOAT 会先创建一个哈希表, 再创建域 field , 最后再执行加法操作
     * 当以下任意一个条件发生时, 返回一个错误: 
     * 1:域 field 的值不是字符串类型(因为 redis 中的数字和浮点数都以字符串的形式保存, 所以它们都属于字符串类型）
     * 2:域 field 当前的值或给定的增量 increment 不能解释(parse)为双精度浮点数(double precision
     * floating point number)
     * HINCRBYFLOAT 命令的详细功能和 INCRBYFLOAT 命令类似, 请查看 INCRBYFLOAT 命令获取更多相关信息
     * @param key
     * @param field
     * @param value
     * @return
     */
    Double hincrByFloat(String key, Object field, double value);

    /**
     * 移除并返回列表 key 的头元素
     * @param key
     * @param <T>
     * @return
     */
    <T> T lpop(String key);

    /**
     * 将一个或多个值 value 插入到列表 key 的表头
     * 如果有多个 value 值, 那么各个 value 值按从左到右的顺序依次插入到表头:  比如说, 
     * 对空列表 mylist 执行命令 LPUSH mylist a b c , 列表的值将是 c b a , 
     * 这等同于原子性地执行 LPUSH mylist a LPUSH mylist b 和 LPUSH mylist c 三个命令
     * 如果 key 不存在, 一个空列表会被创建并执行 LPUSH 操作
     * 当 key 存在但不是列表类型时, 返回一个错误
     * @param key
     * @param values
     * @return
     */
    void lpush(String key, Object... values);

    /**
     * 移除并返回列表 key 的尾元素
     * @param key
     * @param <T>
     * @return
     */
    <T> T rpop(String key);

    /**
     * 命令 RPOPLPUSH 在一个原子时间内, 执行以下两个动作: 
     * 将列表 source 中的最后一个元素(尾元素)弹出, 并返回给客户端
     * 将 source 弹出的元素插入到列表 destination , 作为 destination 列表的的头元素
     * @param srcKey
     * @param dstKey
     * @param <T>
     * @return
     */
    <T> T rpoplpush(String srcKey, String dstKey);

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)
     * 如果有多个 value 值, 那么各个 value 值按从左到右的顺序依次插入到表尾: 比如
     * 对一个空列表 mylist 执行 RPUSH mylist a b c , 得出的结果列表为 a b c , 
     * 等同于执行命令 RPUSH mylist a RPUSH mylist b RPUSH mylist c
     * 如果 key 不存在, 一个空列表会被创建并执行 RPUSH 操作
     * 当 key 存在但不是列表类型时, 返回一个错误
     * @param key
     * @param values
     * @return
     */
    void rpush(String key, Object... values);

    /**
     * 将一个或多个 member 元素加入到集合 key 当中, 已经存在于集合的 member 元素将被忽略
     * 假如 key 不存在, 则创建一个只包含 member 元素作成员的集合
     * 当 key 不是集合类型时, 返回一个错误
     * @param key
     * @param members
     */
    void sadd(String key, Object... members);

    /**
     * 返回集合 key 的基数(集合中元素的数量)
     * @param key
     * @return
     */
    Integer scard(String key);

    /**
     * 返回集合 key 中的所有成员
     * 不存在的 key 被视为空集合
     * @param key
     * @return
     */
    Set<Object> smembers(String key);

    /**
     * 判断 member 元素是否集合 key 的成员
     * @param key
     * @param member
     * @return
     */
    boolean sismember(String key, Object member);


    /**
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中
     * 如果某个 member 已经是有序集的成员, 那么更新这个 member 的 score 值, 
     * 并通过重新插入这个 member 元素, 来保证该 member 在正确的位置上
     * @param key
     * @param score
     * @param member
     * @return
     */
    boolean zadd(String key, double score, Object member);

    /**
     *
     * @param key
     * @param scoreMembers
     * @return
     */
    Integer zadd(String key, Map<Object, Double> scoreMembers);

    /**
     *返回有序集 key 的基数
     * @param key
     * @return
     */
    Integer zcard(String key);

    /**
     * 返回有序集 key 中,  score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max)的成员的数量
     * 关于参数 min 和 max 的详细使用方法, 请参考 ZRANGEBYSCORE 命令
     * @param key
     * @param min
     * @param max
     * @return
     */
    Integer zcount(String key, double min, double max);

    /**
     *为有序集 key 的成员 member 的 score 值加上增量 increment
     * @param key
     * @param score
     * @param member
     * @return
     */
    Double zincrby(String key, double score, Object member);

    /**
     * 返回有序集 key 中, 指定区间内的成员
     * 其中成员的位置按 score 值递增(从小到大)来排序
     * 具有相同 score 值的成员按字典序(lexicographical order)来排列
     * 如果你需要成员按 score 值递减(从大到小)来排列, 请使用 ZREVRANGE 命令
     * @param key
     * @param start
     * @param end
     * @return
     */
    Set<Object> zrange(String key, int start, int end);

    /**
     * 返回有序集 key 中, 指定区间内的成员
     * 其中成员的位置按 score 值递减(从大到小)来排列
     * 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order)排列
     * 除了成员按 score 值递减的次序排列这一点外,  ZREVRANGE 命令的其他方面和 ZRANGE 命令一样
     * @param key
     * @param start
     * @param end
     * @return
     */
    Set<Object> zrevrange(String key, int start, int end);

    /**
     * 返回有序集 key 中, 所有 score 值介于 min 和 max 之间(包括等于 min 或 max)的成员
     * 有序集成员按 score 值递增(从小到大)次序排列
     * @param key
     * @param min
     * @param max
     * @return
     */
    Set<Object> zrangeByScore(String key, double min, double max);

    /**
     * 返回有序集 key 中成员 member 的排名其中有序集成员按 score 值递增(从小到大)顺序排列
     * 排名以 0 为底, 也就是说,  score 值最小的成员排名为 0
     * 使用 ZREVRANK 命令可以获得成员按 score 值递减(从大到小)排列的排名
     * @param key
     * @param member
     * @return
     */
    Integer zrank(String key, Object member);

    /**
     * 返回有序集 key 中成员 member 的排名其中有序集成员按 score 值递减(从大到小)排序
     * 排名以 0 为底, 也就是说,  score 值最大的成员排名为 0
     * 使用 ZRANK 命令可以获得成员按 score 值递增(从小到大)排列的排名
     * @param key
     * @param member
     * @return
     */
    Integer zrevrank(String key, Object member);

    /**
     * 移除有序集 key 中的一个或多个成员, 不存在的成员将被忽略
     * 当 key 存在但不是有序集类型时, 返回一个错误
     * @param key
     * @param members
     * @return
     */
    boolean zrem(String key, Object... members);

    /**
     * 返回有序集 key 中, 成员 member 的 score 值
     * 如果 member 元素不是有序集 key 的成员, 或 key 不存在, 返回 nil
     * @param key
     * @param member
     * @return
     */
    Double zscore(String key, Object member);

}
