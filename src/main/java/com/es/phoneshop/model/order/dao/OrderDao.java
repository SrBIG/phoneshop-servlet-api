package com.es.phoneshop.model.order.dao;

import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.dao.exception.OrderNotFoundException;

public interface OrderDao {
    Order getOrder(long id) throws IllegalArgumentException, OrderNotFoundException;

    void save(Order Order) throws IllegalArgumentException;

    void delete(long id) throws IllegalArgumentException, OrderNotFoundException;

    Order getBySecureId(String secureId) throws OrderNotFoundException;
}
