package com.es.phoneshop.model.order.dao;

import com.es.phoneshop.model.order.Order;

public class ArrayListOrderDao implements OrderDao {
    private static OrderDao instance;

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

    @Override
    public Order getOrder(long id) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void save(Order Order) throws IllegalArgumentException {

    }

    @Override
    public void delete(long id) throws IllegalArgumentException {

    }
}
