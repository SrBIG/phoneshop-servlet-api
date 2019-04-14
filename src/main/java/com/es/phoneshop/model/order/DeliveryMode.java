package com.es.phoneshop.model.order;

import java.math.BigDecimal;

public enum DeliveryMode {
    COURIER("Courier", BigDecimal.valueOf(20)),
    STOREPICKUP("Store Pickup", BigDecimal.ZERO);

    private String name;
    private BigDecimal cost;

    public String getName(){
        return this.name;
    }

    public BigDecimal getCost() {
        return this.cost;
    }

    DeliveryMode(String name, BigDecimal cost) {
        this.name = name;
        this.cost = cost;
    }
}
