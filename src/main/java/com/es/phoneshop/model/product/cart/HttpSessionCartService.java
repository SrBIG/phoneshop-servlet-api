package com.es.phoneshop.model.product.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.exception.ProductNotFoundException;

public class HttpSessionCartService implements CartService {
    private static CartService instance;
    private Cart cart = new Cart();

    private HttpSessionCartService() {
    }

    public static CartService getInstance() {
        if (instance == null) {
            synchronized (HttpSessionCartService.class) {
                if (instance == null) {
                    instance = new HttpSessionCartService();
                }
            }
        }
        return instance;
    }

    @Override
    public void add(long productId, int quantity) throws ProductNotFoundException {
        Product product = ArrayListProductDao.getInstance().getProduct(productId);
        cart.addItem(product, quantity);
    }
}
