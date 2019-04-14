package com.es.phoneshop.model.order;

import java.math.BigDecimal;

public enum DeliveryMode {
    COURIER("Courier", BigDecimal.ZERO),
    STOREPICKUP("Store Pickup", BigDecimal.valueOf(20));

    private String name;
    private BigDecimal cost;

    public String getName(){
        return this.name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    DeliveryMode(String name, BigDecimal cost) {
        this.name = name;
        this.cost = cost;
    }
}
