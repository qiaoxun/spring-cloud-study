package com.study.distribute.lock.zookeeper.config;

import com.study.distribute.lock.zookeeper.utils.ZkProperties;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZKConfiguration {

    @Autowired
    private ZkProperties zkProperties;

    @Bean(initMethod = "start")
    public CuratorFramework curatorFramework() {
        return CuratorFrameworkFactory.newClient(
                zkProperties.getConnectString(),
                zkProperties.getSessionTimeoutMs(),
                zkProperties.getConnectionTimeoutMs(),
                new RetryNTimes(zkProperties.getRetryCount(), zkProperties.getElapsedTimeMs()));
    }

}
