package com.es.phoneshop.model.cart.exception;

public class OutOfStockException extends Exception {
    public OutOfStockException() {
        super();
    }

    public OutOfStockException(String message) {
        super(message);
    }
}
