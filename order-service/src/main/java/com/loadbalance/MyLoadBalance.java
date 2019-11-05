package com.loadbalance;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Optional;

public class MyLoadBalance extends AbstractLoadBalancerRule {
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }

    public Server choose(ILoadBalancer lb, Object key) {
        String ip = KeyHolder.getIP();
        System.out.println("MyLoadBalance.choose ip is " + ip);
        KeyHolder.remote();
        if (StringUtils.isNotBlank(ip)) {
            List<Server> serverList = lb.getAllServers();
            Optional<Server> serverOptional = serverList.stream().filter(server -> {
                if (StringUtils.equals(server.getHost(), ip)) {
                    System.out.println("find same ip host : " + server.getHostPort() + ", id= " + server.getId());
                    return true;
                }
                return false;
            }).findAny();
            if (serverOptional.isPresent()) {
                return serverOptional.get();
            }
        }
        return null;
    }
}
