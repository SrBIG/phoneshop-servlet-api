package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart {
    private List<CartItem> cartItems;

    public Cart() {
        cartItems = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) {
        Optional<CartItem> cartItemOptional = cartItems.stream()
                .filter(item -> product.getId().equals(item.getProduct().getId()))
                .findAny();
        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            CartItem cartItem = new CartItem(product, quantity);
            cartItems.add(cartItem);
        }
    }
}

