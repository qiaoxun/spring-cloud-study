package com.study.distribute.lock.test;

import com.study.distribute.lock.domain.LockerInfo;
import com.study.distribute.lock.lock.MySQLLocker;
import com.study.distribute.lock.mapper.LockerMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class Tester {

    @Autowired
    private LockerMapper lockerMapper;

    @Autowired
    private MySQLLocker locker;

    @Test
    public void test() {
        LockerInfo lockerInfo = new LockerInfo();
        lockerInfo.setCount(1);
        lockerInfo.setNodeInfo("node 1");
        lockerInfo.setResourceName("com.study.distribute.lock.test.Tester.test4");
        lockerInfo.setDescription("test");
        lockerInfo.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        lockerInfo.setUpdateTime(lockerInfo.getCreateTime());
        System.err.println(lockerMapper.insertData(lockerInfo) + " =================");
        System.out.println(lockerInfo.getId());
    }

    @Test
    public void testQueryAll() {
        List<LockerInfo> lockerInfos = lockerMapper.listAllLockerInfo();
        lockerInfos.forEach(System.out::println);
    }

    @Test
    public void getLockerInfoByResourceName() {
        LockerInfo lockerInfo = lockerMapper.getLockerInfoByResourceName("com.study.distribute.lock.test.Tester.test4");
        System.out.println(lockerInfo);
    }

    @Test
    public void testLocker() {
        String resourceName = "com.study.distribute.lock.test.Tester.testLocker";

        Thread thread = new Thread(new TestHelper(resourceName, "thread", 0, locker));
        Thread thread1 = new Thread(new TestHelper(resourceName, "thread1", 0, locker));
        thread.start();
        thread1.start();

        try {
            thread.join();
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

class TestHelper implements Runnable {
    private String resourceName;
    private String nodeInfo;
    private long timeout;
    private MySQLLocker locker;

    public TestHelper(String resourceName, String nodeInfo, long timeout, MySQLLocker locker) {
        this.resourceName = resourceName;
        this.nodeInfo = nodeInfo;
        this.timeout = timeout;
        this.locker = locker;
    }

    @Override
    public void run() {
        System.out.println("nodeInfo " + nodeInfo + " prepare to get lock...");
        locker.lock(resourceName, nodeInfo, timeout);
        System.out.println("nodeInfo " + nodeInfo + " I'm inside locker");
        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        locker.release(resourceName, nodeInfo);
        System.out.println("nodeInfo " + nodeInfo + " lock released");
    }
}
