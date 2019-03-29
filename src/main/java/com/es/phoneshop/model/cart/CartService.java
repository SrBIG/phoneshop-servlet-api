package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.cart.exception.OutOfStockException;
import com.es.phoneshop.model.product.exception.ProductNotFoundException;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    void add(Cart cart, long productId, int quantity) throws ProductNotFoundException, OutOfStockException;
    Cart getCart(HttpServletRequest request);
}
