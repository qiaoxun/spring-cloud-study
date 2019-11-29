package com.study.distribute.lock.redis.lock;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class SimpleTimeoutRedisLock extends RedisLock {

    public SimpleTimeoutRedisLock(StringRedisTemplate stringRedisTemplate, String resourceName) {
        super(stringRedisTemplate, resourceName);
    }

    @Override
    public void lock() {
        while (true) {
//            stringRedisTemplate.con
            if (stringRedisTemplate.opsForValue().setIfAbsent(this.resourceName, "lock")) {
                System.out.println("Got the lock of " + this.resourceName);
                break;
            }
            // sleep 3 ms
            LockSupport.parkNanos(3 * 1000 * 1000);
        }
    }

    @Override
    public void unlock() {
        stringRedisTemplate.delete(this.resourceName);
    }

    public void setIfAbsent(String resourceName, long time, TimeUnit timeUnit) {
        stringRedisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Object nativeConnection = connection.getNativeConnection();
                if (nativeConnection instanceof JedisConnection) {

                }

                return null;
            }
        });
    }
}