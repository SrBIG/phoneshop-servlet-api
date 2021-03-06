package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.cart.exception.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.exception.ProductNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpSessionCartService implements CartService {
    public static final String SESSION_CART = "cart";
    private static CartService instance;
    private ProductDao productDao;

    private HttpSessionCartService() {
        productDao = ArrayListProductDao.getInstance();
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
        checkQuantity(quantity);
        Product product = productDao.getProduct(productId);
        cart.addItem(product, quantity);
    }

    @Override
    public void update(Cart cart, long productId, int quantity) throws ProductNotFoundException, OutOfStockException, IllegalArgumentException {
        checkQuantity(quantity);
        Product product = productDao.getProduct(productId);
        cart.update(product, quantity);
    }

    @Override
    public void delete(Cart cart, long productId) throws ProductNotFoundException {
        Product product = productDao.getProduct(productId);
        cart.delete(product);
    }

    @Override
    public void clearCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = new Cart();
        session.setAttribute(SESSION_CART, cart);
    }

    @Override
    public boolean isCartActual(Cart cart) {
        return cart.isActual();
    }

    private void checkQuantity(int quantity) throws IllegalArgumentException {
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be more 0");
        }
    }
}
