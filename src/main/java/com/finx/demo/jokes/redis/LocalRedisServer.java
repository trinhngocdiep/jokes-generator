package com.finx.demo.jokes.redis;

import io.dropwizard.lifecycle.Managed;
import redis.embedded.RedisServer;

/**
 * In-memory redis server for local development.
 */
@SuppressWarnings("unused")
public class LocalRedisServer implements Managed {

    private RedisServer redisServer;

    @Override
    public void start() {
        redisServer = new RedisServer();
        redisServer.start();
    }

    @Override
    public void stop() {
        redisServer.stop();
    }
}
