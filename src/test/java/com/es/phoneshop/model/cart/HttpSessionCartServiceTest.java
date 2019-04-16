package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.cart.exception.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.exception.ProductNotFoundException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

import static com.es.phoneshop.model.cart.HttpSessionCartService.SESSION_CART;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private Cart cart;
    @Mock
    private static ArrayListProductDao productDao;

    private static Product product;

    private static BigDecimal price = new BigDecimal("1");
    private static long realId = 41L;
    private static int realQuantity = 1;
    private int failQuantity = 0;

    @InjectMocks
    private HttpSessionCartService cartService;

    @BeforeClass
    public static void setupClass() {
        productDao = ArrayListProductDao.getInstance();
        product = mock(Product.class);
        when(product.getId()).thenReturn(realId);
        when(product.getStock()).thenReturn(realQuantity);
        when(product.getPrice()).thenReturn(price);
        productDao.save(product);
    }

    @AfterClass
    public static void destroyClass() throws ProductNotFoundException {
        productDao = ArrayListProductDao.getInstance();
        productDao.delete(product.getId());
        productDao.findProducts(null, null, null);
    }

    @Before
    public void setup() {
        when(request.getSession()).thenReturn(session);
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
