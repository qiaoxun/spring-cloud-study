package com.study.distribute.lock.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class ZookeeperLock implements InitializingBean {

    private static final String LOCK_ROOT = "zk_lock  ";
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @Autowired
    private CuratorFramework curatorFramework;

    public void acquireLock(String path) {
        String keyPath = "/" + LOCK_ROOT + "/" + path;
        while (true) {
            try {
                curatorFramework.create()
                        .creatingParentContainersIfNeeded()
                        .withMode(CreateMode.EPHEMERAL)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath(keyPath);
                System.out.println("Succeed to acquire lock for " + keyPath);
                break;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Failed to acquire lock for " + keyPath);
                System.out.println("Try again ......");

                if (countDownLatch.getCount() <= 0) {
                    countDownLatch = new CountDownLatch(1);
                }
                try {
                    countDownLatch.await();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public boolean releaseLock(String path) {
        String keyPath = "/" + LOCK_ROOT + "/" + path;
        try {
            if (curatorFramework.checkExists().forPath(keyPath) != null) {
                curatorFramework.delete().forPath(keyPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void addWatcher(String path) throws Exception {
        String keyPath;
        if (LOCK_ROOT.equals(path)) {
            keyPath = "/" + path;
        } else {
            keyPath = "/" + LOCK_ROOT + "/" + path;
        }

        final PathChildrenCache childrenCache = new PathChildrenCache(curatorFramework, keyPath, false);
        childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);

        childrenCache.getListenable().addListener((client, event) -> {
            if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
                String oldPath = event.getData().getPath();
                System.out.println("Last child been removed: " + oldPath);
                if (oldPath.contains(path)) {
                    countDownLatch.countDown();
                }
            }
        });

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
