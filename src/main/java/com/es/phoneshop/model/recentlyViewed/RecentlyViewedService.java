package com.es.phoneshop.model.recentlyViewed;

import com.es.phoneshop.model.product.Product;

import java.util.List;

public interface RecentlyViewedService {
    List<Product> add(List<Product> recentlyViewed, Product product) throws IllegalArgumentException;
}
