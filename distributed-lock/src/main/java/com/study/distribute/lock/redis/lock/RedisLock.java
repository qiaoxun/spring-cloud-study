package com.study.distribute.lock.redis.lock;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public abstract class RedisLock implements Lock {

    public StringRedisTemplate stringRedisTemplate;
    public String resourceName;

    public RedisLock(StringRedisTemplate stringRedisTemplate, String resourceName) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.resourceName = resourceName;
    }

    @Override
    public void lock() {

    }

    @Override
    public void unlock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
