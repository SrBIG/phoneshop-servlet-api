package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.person.Customer;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {
    private static OrderService instance;

    public static OrderService getInstance() {
        if (instance == null) {
            synchronized (OrderServiceImpl.class) {
                if (instance == null) {
                    instance = new OrderServiceImpl();
                }
            }
        }
        return instance;
    }

    private OrderServiceImpl() {
    }

    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();
        List<CartItem> cartItems = cart.getCartItems().stream()
                .map(CartItem::new)
                .collect(Collectors.toList());
        BigDecimal cartTotalPrice = cart.getTotalPrice();
        order.setOrderItems(cartItems);
        order.setTotalPrice(cartTotalPrice);
        return order;
    }

    @Override
    public void placeOrder(Order order,
                           Customer customer,
                           DeliveryMode deliveryMode,
                           Date deliveryDate,
                           String deliveryAddress,
                           PaymentMethod paymentMethod) {

    }

    @Override
    public List<DeliveryMode> getDeliveryModes() {
        return Arrays.asList(DeliveryMode.values());
    }

    @Override
    public List<PaymentMethod> getPaymentMethod() {
        return Arrays.asList(PaymentMethod.values());
    }
}