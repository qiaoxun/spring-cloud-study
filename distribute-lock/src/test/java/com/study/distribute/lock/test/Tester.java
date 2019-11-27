package com.study.distribute.lock.test;

import com.study.distribute.lock.mapper.LockerMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class Tester {

    @Autowired
    private LockerMapper lockerMapper;

    @Test
    public void test() {
        System.err.println(lockerMapper.getInt() + " =================");
    }

}
