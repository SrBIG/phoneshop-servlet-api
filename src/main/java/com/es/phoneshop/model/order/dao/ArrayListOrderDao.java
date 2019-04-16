package com.es.phoneshop.model.order.dao;

import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.dao.exception.OrderNotFoundException;

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

    private ArrayListOrderDao() {
        orders = new ArrayList<>();
        orderId = new AtomicLong();
    }

    @Override
    public Order getOrder(long id) throws IllegalArgumentException, OrderNotFoundException {
        return orders.stream()
                .filter(order -> order.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));
    }

    @Override
    public Order getBySecureId(String secureId) throws OrderNotFoundException {
        return orders.stream()
                .filter(order -> secureId.equals(order.getSecureId()))
                .findAny()
                .orElseThrow(() -> new OrderNotFoundException("Order with secure id " + secureId + " not found"));
    }

    @Override
    public void save(Order order) throws IllegalArgumentException {
        order.setId(orderId.incrementAndGet());
        orders.add(order);
        order.setSecureId(UUID.randomUUID().toString());
    }

    @Override
    public void delete(long id) throws IllegalArgumentException, OrderNotFoundException {
        if (!orders.removeIf(order -> order.getId().equals(id))) {
            throw new OrderNotFoundException(String.valueOf(id));
        }
    }
}
