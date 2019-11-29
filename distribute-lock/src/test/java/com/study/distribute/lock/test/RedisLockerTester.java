package com.study.distribute.lock.test;

import com.study.distribute.lock.redis.lock.RedisLock;
import com.study.distribute.lock.redis.lock.SimpleRedisLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisLockerTester {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test() {
        String resourceName = "com.study.distribute.lock.test.RedisLockerTester.test";
        RedisLock redisLock = new SimpleRedisLock(stringRedisTemplate, resourceName);

        redisLock.lock();
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        redisLock.unlock();

    }

}
