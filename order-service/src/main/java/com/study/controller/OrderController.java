package com.study.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.study.domain.Item;
import com.study.domain.Order;
import com.study.domain.ServerConfConfigure;
import com.study.feign.ItemFeignInterface;
import com.study.loadbalance.KeyHolder;
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
import java.util.ArrayList;
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

    @Autowired
    private ItemFeignInterface itemFeignInterface;

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

    @GetMapping("/getServerConfConfigure")
    public ServerConfConfigure getServerConfConfigure() {
        KeyHolder.putIP("127.0.0.1");
        return itemFeignInterface.getServerConfConfigure();
    }

    @GetMapping("/updateServerConf")
    public ServerConfConfigure updateServerConf() {
        KeyHolder.putIP("127.0.0.1");
        ServerConfConfigure serverConfConfigure = new ServerConfConfigure();
        List<String> addList = new ArrayList<>();
        addList.add("add");
        addList.add("add1");
        addList.add("add2");
        serverConfConfigure.setAddList(addList);

        List<String> updateList = new ArrayList<>();
        updateList.add("update");
        updateList.add("update1");
        updateList.add("update2");
        serverConfConfigure.setUpdateList(updateList);

        List<String> removeList = new ArrayList<>();
        removeList.add("remove");
        removeList.add("remove1");
        removeList.add("remove2");
        serverConfConfigure.setRemoveList(removeList);

        return itemFeignInterface.updateServerConf(serverConfConfigure);
    }


}
