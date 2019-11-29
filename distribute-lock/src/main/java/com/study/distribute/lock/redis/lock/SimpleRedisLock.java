package com.study.distribute.lock.redis.lock;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.locks.LockSupport;

public class SimpleRedisLock extends RedisLock {

    public SimpleRedisLock(StringRedisTemplate stringRedisTemplate, String resourceName) {
        super(stringRedisTemplate, resourceName);
    }

    @Override
    public void lock() {
        while (true) {
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
}
