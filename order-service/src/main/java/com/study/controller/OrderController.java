package com.study.controller;

import com.study.domain.Item;
import com.study.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.Max;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/getOrderById/{id}")
    public Order getOrderById(@PathVariable int id) {
        Item item = restTemplate.getForObject("http://localhost:8889/item/getItemByOrderId/" + id, Item.class);
        Order order = new Order("name " + id, id);
        order.setItem(item);
        return order;
    }

    @GetMapping("/getOrderByIdEureka/{id}")
    public Order getOrderByIdEureka(@PathVariable int id) {
        String itemServiceId = "item-service";
        List<ServiceInstance> instances = discoveryClient.getInstances(itemServiceId);
        ServiceInstance instance = instances.get(0);

        String url = instance.getHost() + ":" + instance.getPort();

        Item item = restTemplate.getForObject("http://" + url + "/item/getItemByOrderId/" + id, Item.class);
        System.out.println("url is " + url);
        Order order = new Order("name " + id, id);
        order.setItem(item);
        return order;
    }
}
