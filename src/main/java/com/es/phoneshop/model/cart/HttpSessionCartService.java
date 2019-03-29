package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.cart.exception.OutOfStockException;
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
    public void add(long productId, int quantity) throws ProductNotFoundException, OutOfStockException {
        Product product = ArrayListProductDao.getInstance().getProduct(productId);
        if (quantity > product.getStock()) {
            throw new OutOfStockException("Not enough stock. Product stock is " + product.getStock());
        }
        cart.addItem(product, quantity);
    }
}
