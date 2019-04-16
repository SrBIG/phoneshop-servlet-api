package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.cart.exception.OutOfStockException;
import com.es.phoneshop.model.order.dao.ArrayListOrderDao;
import com.es.phoneshop.model.order.dao.OrderDao;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {
    private static OrderService instance;
    private static OrderDao orderDao;
    private ProductDao productDao;

    public static OrderService getInstance() {
        if (instance == null) {
            synchronized (OrderServiceImpl.class) {
                if (instance == null) {
                    instance = new OrderServiceImpl();
                }
            }
        }
        return instance;
    }

    private OrderServiceImpl() {
        orderDao = ArrayListOrderDao.getInstance();
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();
        List<CartItem> cartItems = cart.getCartItems().stream()
                .map(CartItem::new)
                .collect(Collectors.toList());
        BigDecimal cartTotalPrice = cart.getTotalPrice();
        order.setOrderItems(cartItems);
        order.setTotalPrice(cartTotalPrice);
        return order;
    }

    @Override
    public void placeOrder(Order order) throws OutOfStockException {
        BigDecimal deliveryCost = order.getDeliveryMode().getCost();
        BigDecimal totalPrice = order.getTotalPrice().add(deliveryCost);
        order.setTotalPrice(totalPrice);
        orderDao.save(order);
        stockReduction(order.getOrderItems());
    }

    @Override
    public List<DeliveryMode> getDeliveryModes() {
        return Arrays.asList(DeliveryMode.values());
    }

    @Override
    public List<PaymentMethod> getPaymentMethod() {
        return Arrays.asList(PaymentMethod.values());
    }

    synchronized private void stockReduction(List<CartItem> cartItems) throws OutOfStockException {
        List<Product> productsToSave = new ArrayList<>();
        AtomicBoolean notEnoughStock = new AtomicBoolean(false);
        for (CartItem cartItem : cartItems){
            Product product = cartItem.getProduct();
            Integer quantity = cartItem.getQuantity();
            Integer oldStock = product.getStock();
            int newStock = oldStock - quantity;
            if(newStock < 0){
                throw new OutOfStockException("Not enough Stock!");
            }
            product.setStock(newStock);
            productsToSave.add(product);
        }
        productsToSave.forEach(product -> productDao.save(product));
    }
}