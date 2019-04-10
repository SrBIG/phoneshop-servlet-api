package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.cart.exception.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart {
    private List<CartItem> cartItems;
    private BigDecimal totalPrice;
    private int totalQuantity;

    public Cart() {
        cartItems = new ArrayList<>();
        totalPrice = new BigDecimal("0");
    }

    public void addItem(Product product, int quantity) throws OutOfStockException {
        checkStock(product, quantity);

        Optional<CartItem> cartItemOptional = findCartItemByProduct(product);
        CartItem cartItem;
        if (cartItemOptional.isPresent()) {
            cartItem = cartItemOptional.get();
            if (cartItem.getQuantity() + quantity > product.getStock()) {
                throw new OutOfStockException("Not enough stock. Product stock is " + product.getStock());
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
            }
        } else {
            cartItem = new CartItem(product, quantity);
            cartItems.add(cartItem);
        }
        recalculateTotal();
    }

    public void update(Product product, int quantity) throws OutOfStockException {
        checkStock(product, quantity);

        Optional<CartItem> cartItemOptional = findCartItemByProduct(product);
        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(quantity);
            recalculateTotal();
        }
    }

    public void delete(Product product) {
        cartItems.removeIf(cartItem -> cartItem.getProduct().getId().equals(product.getId()));
        recalculateTotal();
    }

    private void checkStock(Product product, int quantity) throws OutOfStockException {
        if (quantity > product.getStock()) {
            throw new OutOfStockException("Not enough stock. Product stock is " + product.getStock());
        }
    }

    private Optional<CartItem> findCartItemByProduct(Product product) {
        return cartItems.stream()
                .filter(item -> product.getId().equals(item.getProduct().getId()))
                .findAny();
    }

    private void recalculateTotal() {
        totalQuantity = 0;
        totalPrice = new BigDecimal("0");
        cartItems.forEach(cartItem -> {
                    BigDecimal cartItemProductPrice = cartItem.getProduct().getPrice();
                    int cartItemQuantity = cartItem.getQuantity();
                    totalQuantity += cartItemQuantity;
                    BigDecimal cartItemPrice = cartItemProductPrice.multiply(BigDecimal.valueOf(cartItemQuantity));
                    totalPrice = totalPrice.add(cartItemPrice);
                }
        );
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    @Override
    public String toString() {
        return totalQuantity == 0 ? "empty" : totalQuantity + " item(s)";
    }
}

