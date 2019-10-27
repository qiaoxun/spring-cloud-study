package com.study.feign;

import com.study.domain.Item;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("item-service")
public interface ItemFeignInterface {

    @GetMapping("/item/getItemByOrderId/{orderId}")
    Item getItemByOrderId(@PathVariable int orderId);
}
