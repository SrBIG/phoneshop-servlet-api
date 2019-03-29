package com.es.phoneshop.model.product.cart;

import com.es.phoneshop.model.product.exception.ProductNotFoundException;

public interface CartService {
    void add(long productId, int quantity) throws ProductNotFoundException;
}
