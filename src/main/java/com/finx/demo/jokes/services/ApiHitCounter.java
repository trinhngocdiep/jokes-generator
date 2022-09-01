package com.finx.demo.jokes.services;

import com.finx.demo.jokes.config.AppConfig;
import redis.clients.jedis.Jedis;

import javax.inject.Inject;

public class ApiHitCounter {

    private final AppConfig appConfig;
    private final Jedis jedis = new Jedis();

    @Inject
    public ApiHitCounter(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    /**
     * Increases by 1 and returns the count for the given keyword.
     */
    public long increase(String keyword) {
        String key = "jokes-api-hit-keyword-count-" + keyword;
        long count = jedis.incr(key);
        jedis.expire(key, appConfig.getRateLimitConfiguration().getKeywordRepeatWindowInSeconds());
        return count;
    }

}
