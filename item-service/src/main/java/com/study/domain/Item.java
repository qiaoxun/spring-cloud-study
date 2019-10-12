package com.study.domain;

public class Item {
    private int orderId;
    private int id;
    private String name;

    public Item() {}

    public Item(int orderId, int id, String name) {
        this.orderId = orderId;
        this.id = id;
        this.name = name;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
