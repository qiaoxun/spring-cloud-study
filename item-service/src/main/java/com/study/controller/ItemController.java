package com.study.controller;

import com.study.domain.Item;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {

    @GetMapping("/getItemByOrderId/{orderId}")
    public Item getItemByOrderId(@PathVariable int orderId) {
        System.out.println("getItemByOrderId - 8091");
        return new Item(orderId, orderId + 1, "item " + orderId);
    }

}
