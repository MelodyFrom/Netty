package com.gaop.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.IOException;
import java.security.Key;

/**
 * redis 版本的基础限流器，适合较小流量的场景
 * @author gaopeng@doctorwork.com
 * @date 2019-07-23 22:19
 **/
public class SimpleRateLimiter {

    private Jedis jedis;

    private static final String LIMIT_RATE_PREFIX = "actionLimit:";

    public SimpleRateLimiter(Jedis jedis) {
        this.jedis = jedis;
    }

    /**
     * 检查指定类型的操作是否被允许执行
     * @param userId 用户id
     * @param actionType 操作类型
     * @param period 时间周期
     * @param maxCount 时间周期内最大可被执行次数
     * @return 如果允许执行就返回 true
     */
    public boolean isActionllowed(String userId, String actionType, int period, int maxCount) throws IOException {
        Pipeline pipeline = jedis.pipelined();
        Long currentTimestamp = System.currentTimeMillis();
        String key = String.format("%s:%s:%s", LIMIT_RATE_PREFIX, userId, actionType);
        pipeline.multi();
        pipeline.zadd(key, currentTimestamp, currentTimestamp.toString());
        pipeline.zremrangeByScore(key, 0, currentTimestamp - (period*1000));
        Response<Long> countResp = pipeline.zcard(key);
        pipeline.expire(key, period);
        pipeline.exec();
        pipeline.close();
        return countResp.get() >= maxCount;
    }
}
