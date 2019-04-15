package com.es.phoneshop.model.order.dao.exception;

public class OrderNotFoundException extends Exception  {
    public OrderNotFoundException() {
        super();
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
