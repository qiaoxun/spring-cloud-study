package com.study.distribute.lock.mysql.lock;

import com.study.distribute.lock.mysql.domain.LockerInfo;
import com.study.distribute.lock.mysql.mapper.LockerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.locks.LockSupport;

/**
 * https://juejin.im/post/5bbb0d8df265da0abd3533a5
 */
@Component
public class MySQLLocker {

    @Autowired
    private LockerMapper lockerMapper;

    public void lock(String resourceName, String nodeInfo, long timeout) {
        long begin = System.currentTimeMillis();
        while (true) {
            if (lockInside(resourceName, nodeInfo)) {
                return;
            }
            // 3 ms 后重试
            LockSupport.parkNanos(1000 * 1000 * 3);
            long end = System.currentTimeMillis();
            long duration = end - begin;
            if (timeout > 0 && duration > timeout) {
                throw new RuntimeException("Didn't get lock due to timeout");
            }
        }
    }

    public void release(String resourceName, String nodeInfo) {
        if (releaseInside(resourceName, nodeInfo)) {
            return;
        }
        throw new RuntimeException("Release lock on resource " + resourceName + " node " + nodeInfo + " failed!");
    }

    public boolean tryLock(String resourceName, String nodeInfo) {
        return lockInside(resourceName, nodeInfo);
    }

    public boolean tryRelease(String resourceName, String nodeInfo) {
        return releaseInside(resourceName, nodeInfo);
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
