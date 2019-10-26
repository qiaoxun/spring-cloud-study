package com.study;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for simple OrderServiceApp.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest

public class AppTest {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * Rigorous Test :-)
     */
    @Test
    public void test() {
        String itemServiceId = "item-service";
        for (int i = 0; i < 100; i++) {
            ServiceInstance serviceInstance = this.loadBalancerClient.choose(itemServiceId);
            System.out.println(String.format("第%s次" + serviceInstance.getHost() + ":" + serviceInstance.getPort(), i));
        }

    }
}
