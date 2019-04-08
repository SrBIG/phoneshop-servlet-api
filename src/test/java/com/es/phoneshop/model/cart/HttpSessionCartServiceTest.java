package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.cart.exception.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.exception.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.es.phoneshop.model.cart.HttpSessionCartService.SESSION_CART;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private Cart cart;
    @Mock
    private ArrayListProductDao productDao;
    @Mock
    private Product product;

    private long realId = 1L;
    private int realQuantity = 1;
    private int failQuantity = 0;

    @InjectMocks
    private HttpSessionCartService cartService;

    @Before
    public void setup() throws ProductNotFoundException {
        when(request.getSession()).thenReturn(session);
        when(ArrayListProductDao.getInstance().getProduct(realId)).thenReturn(product);
//        when(ArrayListProductDao.getInstance()).thenReturn(productDao);
//        when(productDao.getProduct(realId)).thenReturn(product);
        cartService = (HttpSessionCartService) HttpSessionCartService.getInstance();
    }

    @Test
    public void testGetNullCart() {
        when(session.getAttribute(SESSION_CART)).thenReturn(cart);

        Cart expected = cart;
        Cart actual = cartService.getCart(request);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAlreadyExistCart() {
        when(session.getAttribute(SESSION_CART)).thenReturn(null);

        Cart newCart = cartService.getCart(request);

        Class expected = Cart.class;
        Class actual = newCart.getClass();
        assertEquals(expected, actual);
        verify(session).setAttribute(SESSION_CART, newCart);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullQuantity() throws ProductNotFoundException, OutOfStockException {
        cartService.add(cart, realId, failQuantity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateNullQuantity() throws ProductNotFoundException, OutOfStockException {
        cartService.update(cart, realId, failQuantity);
    }

    @Test
    public void testAddSuccessful() throws ProductNotFoundException, OutOfStockException {
        cartService.add(cart, realId, realQuantity);
        verify(cart).addItem(product, realQuantity);
    }

    @Test
    public void testUpdateSuccessful() throws ProductNotFoundException, OutOfStockException {
        cartService.update(cart, realId, realQuantity);
        verify(cart).update(product, realQuantity);
    }

    @Test
    public void testDeleteSuccessful() throws ProductNotFoundException, OutOfStockException {
        cartService.delete(cart, realId);
        verify(cart).delete(product);
    }
}
