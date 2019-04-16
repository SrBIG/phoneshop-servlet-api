package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.exception.OutOfStockException;

import java.util.List;

public interface OrderService {
    Order createOrder(Cart cart);
    void placeOrder(Order order) throws OutOfStockException;
    List<DeliveryMode> getDeliveryModes();
    List<PaymentMethod> getPaymentMethod();
}
