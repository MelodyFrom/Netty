package com.gaop.redis;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

/**
 * redis 分布式锁
 * @author gaopeng@doctorwork.com
 * @date 2019-07-23 22:12
 **/
public class RedisDistributeLock {

    private Jedis jedis;

    private static final String DEFAULT_VALUE = "";

    private static final String SET_IF_NOT_EXIST = "NX";
    /**
     * redis 原子操作过期时间单位：秒
     */
    private static final String SET_WITH_EXPIRE_TIME = "EX";

    public RedisDistributeLock(Jedis jedis) {
        this.jedis = jedis;
    }

    /**
     * 尝试加锁
     * @param key redis key
     * @param value value 值，可选，可以用于保存有意义的值
     * @param expireTime 锁过期时间，单位：秒
     * @return 加锁成功返回 true，否则返回 false
     */
    public boolean tryLock(String key, String value, long expireTime) {
        return null != jedis.set(key, StringUtils.isBlank(value) ? DEFAULT_VALUE : value,
                SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
    }

    /**
     * 尝试解锁
     * @param key redis key
     * @return 解锁成功返回 true，否则返回 false
     */
    public boolean unLock(String key) {
        return 1L == jedis.del(key);
    }
}
