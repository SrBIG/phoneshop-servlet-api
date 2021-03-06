package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.exception.ProductNotFoundException;

import java.util.List;

public interface ProductDao {
    Product getProduct(long id) throws ProductNotFoundException, IllegalArgumentException;

    List<Product> findProducts(String query, String sortBy, String order);

    void save(Product product) throws IllegalArgumentException;

    void delete(long id) throws IllegalArgumentException, ProductNotFoundException;
}
