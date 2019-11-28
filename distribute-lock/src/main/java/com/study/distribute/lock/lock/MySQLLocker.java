package com.study.distribute.lock.lock;

import com.study.distribute.lock.domain.LockerInfo;
import com.study.distribute.lock.mapper.LockerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.locks.LockSupport;

@Component
public class MySQLLocker {

    @Autowired
    private LockerMapper lockerMapper;

    public void lock(String resourceName, String nodeInfo) {
        while (true) {
            if (lockInside(resourceName, nodeInfo)) {
                return;
            }
            // 3 ms 后重试
            LockSupport.parkNanos(1000 * 1000 * 3);
        }
    }

    public void release(String resourceName, String nodeInfo) {
        if (releaseInside(resourceName, nodeInfo)) {
            return;
        }
        throw new RuntimeException("Release lock on resource " + resourceName + " node " + nodeInfo + " failed!");
    }


    public boolean tryLock() {

        return false;
    }

    public boolean tryRelease() {

        return false;
    }

    @Transactional
    private boolean lockInside(String resourceName, String nodeInfo) {
        LockerInfo lockerInfo = lockerMapper.getLockerInfoByResourceName(resourceName);
        if (null != lockerInfo && lockerInfo.getId() != 0) {
            if (nodeInfo.equals(lockerInfo.getNodeInfo())) {
                // 重入
                lockerMapper.increaseCountByOne(resourceName);
                return true;
            }
            return false;
        } else {
            // 如果两个线程同时查到数据库没有数据，都像数据库插入数据，肯定会有一个因为主键不能重复而报错
            // 此时，应当返回 false 让线程等待重试
            try {
                LockerInfo lockerInfoNew = new LockerInfo();
                lockerInfoNew.setCount(1);
                lockerInfoNew.setNodeInfo(nodeInfo);
                lockerInfoNew.setResourceName(resourceName);
                lockerInfoNew.setDescription("test");
                lockerInfoNew.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
                lockerInfoNew.setUpdateTime(lockerInfoNew.getCreateTime());
                lockerMapper.insertData(lockerInfoNew);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
    }

    @Transactional
    private boolean releaseInside(String resourceName, String nodeInfo) {
        LockerInfo lockerInfo = lockerMapper.getLockerInfoByResourceName(resourceName);
        if (null != lockerInfo && lockerInfo.getId() != 0) {
            if (nodeInfo.equals(lockerInfo.getNodeInfo())) {
                // 重入情况下，只需要将 count - 1
                if (lockerInfo.getCount() > 1) {
                    lockerMapper.decreaseCountByOne(resourceName);
                    return true;
                } else {
                    lockerMapper.deleteLockerInfoByResourceName(resourceName);
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

}
