package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.person.Customer;

import java.util.Date;
import java.util.List;

public interface OrderService {
    Order createOrder(Cart cart);
    void placeOrder(Order order,
                    Customer customer,
                    DeliveryMode deliveryMode,
                    Date deliveryDate,
                    String deliveryAddress,
                    PaymentMethod paymentMethod);
    List<DeliveryMode> getDeliveryModes();
    List<PaymentMethod> getPaymentMethod();
}
