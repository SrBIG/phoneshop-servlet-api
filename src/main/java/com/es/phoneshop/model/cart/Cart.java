package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.cart.exception.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.exception.ProductNotFoundException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart implements Serializable {
    private static final long serialVersionUID = 7265526492135721028L;
    private List<CartItem> cartItems;
    private BigDecimal totalPrice;
    private int totalQuantity;
    private ProductDao productDao;

    public Cart() {
        cartItems = new ArrayList<>();
        totalPrice = new BigDecimal("0");
        productDao = ArrayListProductDao.getInstance();
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

    public boolean isActual() {
        for (CartItem cartItem : cartItems) {
            try {
                checkStock(cartItem.getProduct(), cartItem.getQuantity());
            } catch (OutOfStockException e) {
                return false;
            }
        }
        return true;
    }

    private void checkStock(Product product, int quantity) throws OutOfStockException {
        try {
            long productId = product.getId().longValue();
            product = productDao.getProduct(productId);
        } catch (ProductNotFoundException e) {
            throw new OutOfStockException("Not enough stock!");
        }
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

