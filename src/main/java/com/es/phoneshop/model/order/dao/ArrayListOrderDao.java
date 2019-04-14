package com.es.phoneshop.model.order.dao;

import com.es.phoneshop.model.order.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ArrayListOrderDao implements OrderDao {
    private static OrderDao instance;

    private List<Order> orders;
    private AtomicLong orderId;

    public static OrderDao getInstance() {
        if (instance == null) {
            synchronized (ArrayListOrderDao.class) {
                if (instance == null) {
                    instance = new ArrayListOrderDao();
                }
            }
        }
        return instance;
    }

    private ArrayListOrderDao(){
        orders = new ArrayList<>();
        orderId = new AtomicLong();
    }

    @Override
    public Order getOrder(long id) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Order getBySecureId(String secureId) {
        return orders.stream()
                .filter(order -> secureId.equals(order.getSecureId()))
                .findAny()
                .orElse(null);
    }

    @Override
    public void save(Order order) throws IllegalArgumentException {
        order.setId(orderId.incrementAndGet());
        orders.add(order);
        order.setSecureId(UUID.randomUUID().toString());
    }

    @Override
    public void delete(long id) throws IllegalArgumentException {

    }
}
