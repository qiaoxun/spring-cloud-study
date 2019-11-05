package com.study.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.study.domain.Item;
import com.study.domain.Order;
import com.study.feign.ItemFeignInterface;
import com.study.service.OrderService;
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

    @Autowired
    private OrderService orderService;



    @GetMapping("/getOrderById/{id}")
    public Order getOrderById(@PathVariable int id) {
        Item item = restTemplate.getForObject("http://localhost:8889/item/getItemByOrderId/" + id, Item.class);
        Order order = new Order("name " + id, id);
        order.setItem(item);
        return order;
    }

    /**
     * need comment @LoadBalance annotation line at RestTemplateConfig
     * @param id
     * @return
     */
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

    /**
     * RestTemplate need @LoadBalance annotation
     * @param id
     * @return
     */
    @GetMapping("/getOrderByIdEurekaLoadBalance/{id}")
    public Order getOrderByIdEurekaLoadBalance(@PathVariable int id) {
        String itemServiceId = "item-service";
        Item item = restTemplate.getForObject("http://" + itemServiceId + "/item/getItemByOrderId/" + id, Item.class);
        Order order = new Order("name " + id, id);
        order.setItem(item);
        return order;
    }


    /**
     * RestTemplate need @LoadBalance annotation
     * @param id
     * @return
     */
    @GetMapping("/getOrderByIdEurekaHystrix/{id}")
    public Order getOrderByIdEurekaHystrix(@PathVariable int id) {
        Item item = orderService.getItemByIdService(id);
        Order order = new Order("name " + id, id);
        order.setItem(item);
        return order;
    }

    /**
     * RestTemplate need @LoadBalance annotation
     * @param id
     * @return
     */
    @GetMapping("/getOrderByIdEurekaFeign/{id}/{ip}")
    public Order getOrderByIdEurekaFeign(@PathVariable int id, @PathVariable String ip) {
        System.out.println("Controller.getItemByIdServiceFeign ip is " + ip);
        Item item = orderService.getItemByIdServiceFeign(id, ip);
        Order order = new Order("name " + id, id);
        order.setItem(item);
        return order;
    }



}
