package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.cart.exception.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.exception.ProductNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpSessionCartService implements CartService {
    private static final String SESSION_CART = "sessionCat";
    private static CartService instance;

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
    public Cart getCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(SESSION_CART);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(SESSION_CART, cart);
        }
        return cart;
    }

    @Override
    public void add(Cart cart, long productId, int quantity) throws ProductNotFoundException, OutOfStockException, IllegalArgumentException {
        if (quantity < 1){
            throw new IllegalArgumentException("Quantity must be more 0");
        }
        Product product = ArrayListProductDao.getInstance().getProduct(productId);
        cart.addItem(product, quantity);
    }
}
