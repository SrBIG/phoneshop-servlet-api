package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.exception.ProductNotFoundException;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id) throws ProductNotFoundException, IllegalArgumentException;

    List<Product> findProducts(String query);

    void save(Product product) throws IllegalArgumentException;

    void delete(Long id) throws IllegalArgumentException, ProductNotFoundException;
}
