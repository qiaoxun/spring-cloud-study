package com.study.feign;

import com.study.domain.Item;
import com.study.domain.ServerConfConfigure;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("item-service")
public interface ItemFeignInterface {

    @GetMapping("/item/getItemByOrderId/{orderId}")
    Item getItemByOrderId(@PathVariable("orderId") int orderId);

    @PostMapping("/item/getServerConfConfigure")
    ServerConfConfigure getServerConfConfigure();

    @PostMapping("/item/updateServerConf")
    ServerConfConfigure updateServerConf(@RequestBody ServerConfConfigure serverConfConfigure);

}
