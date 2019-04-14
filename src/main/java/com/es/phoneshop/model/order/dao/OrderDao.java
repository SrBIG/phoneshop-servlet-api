package com.es.phoneshop.model.order.dao;

import com.es.phoneshop.model.order.Order;

public interface OrderDao {
    Order getOrder(long id) throws IllegalArgumentException;

    void save(Order Order) throws IllegalArgumentException;

    void delete(long id) throws IllegalArgumentException;
}
