package com.study.service;

import com.study.loadbalance.KeyHolder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.study.domain.Item;
import com.study.feign.ItemFeignInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ItemFeignInterface feignInterface;

    /**
     * test hystrix
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "getItemByIdServiceHystrix")
    public Item getItemByIdService(int id) {
        String itemServiceId = "item-service";
        Item item = restTemplate.getForObject("http://" + itemServiceId + "/item/getItemByOrderId/" + id, Item.class);
        return item;
    }

    /**
     * test feign
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "getItemByIdServiceHystrix")
    public Item getItemByIdServiceFeign(int id, String ip) {
        System.out.println("OrderService.getItemByIdServiceFeign ip is " + ip);
        KeyHolder.putIP(ip);
        Item item = feignInterface.getItemByOrderId(id);
        return item;
    }

    public Item getItemByIdServiceHystrix(int id, String ip) {
        Item item = new Item();
        item.setId(0);
        item.setName("请求出错-hystrix");
        item.setOrderId(0);
        return item;
    }
}
