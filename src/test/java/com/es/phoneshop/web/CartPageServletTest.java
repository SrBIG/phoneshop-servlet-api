package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.exception.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.exception.ProductNotFoundException;
import com.es.phoneshop.web.page.CartPageServlet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private CartService cartService;
    @Mock
    private Cart cart;

    private int numProducts = 10;
    private String[] productIds = new String[numProducts];
    private String[] quantities = new String[numProducts];

    @InjectMocks
    private static CartPageServlet servlet;

    @Before
    public void setup() {
        initParameters();
        when(request.getParameterValues("productId")).thenReturn(productIds);
        when(request.getParameterValues("quantity")).thenReturn(quantities);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(cartService.getCart(request)).thenReturn(cart);
    }

    private void initParameters() {
        for (int index = 0; index < numProducts; index++) {
            String value = String.valueOf(index);
            productIds[index] = value;
            quantities[index] = value;
        }
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).setAttribute("cart", cart);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException, ProductNotFoundException, OutOfStockException {
        servlet.doPost(request, response);
        verify(cartService, times(numProducts)).update(eq(cart), anyLong(), anyInt());
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostHasError() throws ServletException, IOException {
        quantities[0] = "bad quantity";
        String[] errors = new String[quantities.length];
        errors[0] = "Not a number";
        servlet.doPost(request, response);
        verify(request).setAttribute("errors", errors);
    }
}
