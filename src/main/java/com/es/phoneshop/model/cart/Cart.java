package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.cart.exception.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart {
    private List<CartItem> cartItems;

    public Cart() {
        cartItems = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) throws OutOfStockException {
        if (quantity > product.getStock()) {
            throw new OutOfStockException("Not enough stock. Product stock is " + product.getStock());
        }
        Optional<CartItem> cartItemOptional = cartItems.stream()
                .filter(item -> product.getId().equals(item.getProduct().getId()))
                .findAny();
        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            if (cartItem.getQuantity() + quantity > product.getStock()) {
                throw new OutOfStockException("Not enough stock. Product stock is " + product.getStock());
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
            }
        } else {
            CartItem cartItem = new CartItem(product, quantity);
            cartItems.add(cartItem);
        }
    }
}

