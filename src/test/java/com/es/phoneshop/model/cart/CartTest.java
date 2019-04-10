package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.cart.exception.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartTest {
    @Mock
    private Product product;
    @Mock
    private BigDecimal price;

    private long productId = 31L;
    private int stock = 10;
    private int intPrice = 2;
    private int quantity = 1;
    private int failQuantity = 20;

    @InjectMocks
    private Cart cart;

    @Before
    public void setup() {
        when(product.getStock()).thenReturn(stock);
        when(product.getPrice()).thenReturn(price);
        when(product.getId()).thenReturn(productId);
        when(price.multiply(BigDecimal.valueOf(quantity))).thenReturn(BigDecimal.valueOf(intPrice * quantity));

        cart = new Cart();
    }

    @Test(expected = OutOfStockException.class)
    public void testAddItemNotEnoughStock() throws OutOfStockException {
        cart.addItem(product, failQuantity);
    }

    @Test
    public void testAddNewItem() throws OutOfStockException {
        cart.addItem(product, quantity);

        BigDecimal expectedTotalPrice = BigDecimal.valueOf(quantity * intPrice);
        BigDecimal actualTotalPrice = cart.getTotalPrice();
        assertEquals(expectedTotalPrice, actualTotalPrice);


        int expectedTotalQuantity = quantity;
        int actualTotalQuantity = cart.getTotalQuantity();
        assertEquals(expectedTotalQuantity, actualTotalQuantity);
    }

    @Test(expected = OutOfStockException.class)
    public void testAddOldItemNotEnoughStock() throws OutOfStockException {
        cart.addItem(product, quantity);
        cart.addItem(product, stock);
    }

    @Test
    public void testAddOldItemSuccessfully() throws OutOfStockException {
        cart.addItem(product, quantity);

        int addQuantity = stock - quantity;
        int newQuantity = quantity + addQuantity;
        when(price.multiply(BigDecimal.valueOf(newQuantity))).thenReturn(BigDecimal.valueOf(intPrice * newQuantity));
        cart.addItem(product, addQuantity);

        BigDecimal expectedTotalPrice = BigDecimal.valueOf(newQuantity * intPrice);
        BigDecimal actualTotalPrice = cart.getTotalPrice();
        assertEquals(expectedTotalPrice, actualTotalPrice);

        int expectedTotalQuantity = newQuantity;
        int actualTotalQuantity = cart.getTotalQuantity();
        assertEquals(expectedTotalQuantity, actualTotalQuantity);
    }

    @Test(expected = OutOfStockException.class)
    public void testUpdateNotEnoughStock() throws OutOfStockException {
        cart.update(product, failQuantity);
    }

    @Test
    public void testUpdateSuccessfully() throws OutOfStockException {
        cart.addItem(product, quantity);

        int newQuantity = stock - quantity;
        when(price.multiply(BigDecimal.valueOf(newQuantity))).thenReturn(BigDecimal.valueOf(intPrice * newQuantity));
        cart.update(product, newQuantity);

        BigDecimal expectedTotalPrice = BigDecimal.valueOf(newQuantity * intPrice);
        BigDecimal actualTotalPrice = cart.getTotalPrice();
        assertEquals(expectedTotalPrice, actualTotalPrice);

        int expectedTotalQuantity = newQuantity;
        int actualTotalQuantity = cart.getTotalQuantity();
        assertEquals(expectedTotalQuantity, actualTotalQuantity);
    }

    @Test
    public void testUpdateDeleteSuccessfully() throws OutOfStockException {
        cart.addItem(product, quantity);
        assertFalse(cart.getCartItems().isEmpty());

        cart.delete(product);
        assertTrue(cart.getCartItems().isEmpty());
    }
}
