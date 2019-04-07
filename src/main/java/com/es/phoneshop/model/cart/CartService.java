package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.cart.exception.OutOfStockException;
import com.es.phoneshop.model.product.exception.ProductNotFoundException;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    void add(Cart cart, long productId, int quantity) throws ProductNotFoundException, OutOfStockException, NumberFormatException;
    Cart getCart(HttpServletRequest request);
    void update(Cart cart, long productId, int quantity) throws ProductNotFoundException, OutOfStockException, IllegalArgumentException;
    void delete(Cart cart, long productId) throws ProductNotFoundException;
}
