package com.study.controller;

import com.study.db.JdbcBean;
import com.study.domain.Item;
import com.study.domain.ServerConfConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private JdbcBean jdbcBean;

    @GetMapping("/getItemByOrderId/{orderId}")
    public Item getItemByOrderId(@PathVariable int orderId) {
        System.out.println("getItemByOrderId - 8091");
        return new Item(orderId, orderId + 1, "item " + orderId);
    }

    @GetMapping("/getJdbcBean")
    public JdbcBean getJdbcBean() {
        return jdbcBean;
    }

    @PostMapping("/getServerConfConfigure")
    public ServerConfConfigure getServerConfConfigure() {
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

        return serverConfConfigure;
    }

    @PostMapping("/updateServerConf")
    public ServerConfConfigure updateServerConf(@RequestBody ServerConfConfigure serverConfConfigure) {
        System.out.println(serverConfConfigure);
        return serverConfConfigure;
    }

}
