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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartTest {
    @Mock
    private Product product;
    @Mock
    private BigDecimal price;
    @Mock
    private CartItem cartItem;


    private int stock = 10;
    private int intPrice = 2;
    private int quantity = 1;
    private int failQuantity = 20;

    @InjectMocks
    private Cart cart;

    @Before
    public void setup(){
        when(product.getStock()).thenReturn(stock);
        when(product.getPrice()).thenReturn(price);

        when(price.multiply(BigDecimal.valueOf(quantity))).thenReturn(BigDecimal.valueOf(intPrice*quantity));

        cart = new Cart();
    }

    @Test(expected = OutOfStockException.class)
    public void testAddItemNotEnoughStock() throws OutOfStockException {
        cart.addItem(product, failQuantity);
    }

    @Test
    public void testAddNewItem() throws OutOfStockException {
        cart.addItem(product, quantity);

        BigDecimal expectedTotalPrice = BigDecimal.valueOf(quantity*intPrice);
        BigDecimal actualTotalPrice = cart.getTotalPrice();
        assertEquals(expectedTotalPrice, actualTotalPrice);

        int expectedTotalQuantity = quantity;
        int actualTotalQuantity = cart.getTotalQuantity();
        assertEquals(expectedTotalQuantity, actualTotalQuantity);
    }

    @Test(expected = OutOfStockException.class)
    public void testAddOldItemNotEnoughStock() throws OutOfStockException {
        int cartItemFailQuantity = stock + 1;

        when(cartItem.getQuantity()).thenReturn(cartItemFailQuantity); // TODO: findCartItem must return my cartItem

        cart.addItem(product, quantity);
    }

    @Test(expected = OutOfStockException.class)
    public void testUpdateNotEnoughStock() throws OutOfStockException {
        cart.update(product, failQuantity);
    }

    //TODO: need implement tests for delete
}
