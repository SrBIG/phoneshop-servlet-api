package com.es.phoneshop.model.recentlyViewed;

import com.es.phoneshop.model.product.Product;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class RecentlyViewedServiceImpl implements RecentlyViewedService {
    private static RecentlyViewedService instance;
    private static final Integer BEGIN = 0;
    private static final Integer MAX_SIZE = 3;

    public static RecentlyViewedService getInstance() {
        if (instance == null) {
            synchronized (RecentlyViewedServiceImpl.class) {
                if (instance == null) {
                    instance = new RecentlyViewedServiceImpl();
                }
            }
        }
        return instance;
    }

    private RecentlyViewedServiceImpl() {
    }

    @Override
    public List<Product> add(List<Product> recentlyViewed, Product product) throws IllegalArgumentException {
        if (Objects.isNull(product)) {
            throw new IllegalArgumentException();
        }
        if (Objects.isNull(recentlyViewed)) {
            recentlyViewed = new LinkedList<>();
        }
        recentlyViewed.remove(product);
        ((LinkedList<Product>) recentlyViewed).addFirst(product);
        while (recentlyViewed.size() > MAX_SIZE) {
            ((LinkedList<Product>) recentlyViewed).removeLast();
        }
        return recentlyViewed;
    }
}
