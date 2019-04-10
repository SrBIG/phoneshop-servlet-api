package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.exception.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.exception.ProductNotFoundException;
import com.es.phoneshop.web.page.ProductDetailsPageServlet;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ArrayListProductDao productDao;
    @Mock
    private Product product;
    @Mock
    private HttpSession session;
    @Mock
    private CartService cartService;
    @Mock
    private Cart cart;

    private String quantityStr = "1";
    private int quantity = Integer.valueOf(quantityStr);
    private String pathInfo = "/1";
    private long id = Long.valueOf(pathInfo.substring(1));

    @InjectMocks
    private static ProductDetailsPageServlet servlet;

    @Before
    public void setup() {
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getParameter(ProductDetailsPageServlet.QUANTITY)).thenReturn(quantityStr);
    }

    @Test
    public void testDoGet() throws ServletException, IOException, ProductNotFoundException {
        when(productDao.getProduct(anyLong())).thenReturn(product);

        servlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetBadId() throws IOException, ServletException {
        String badPath = "BAD_PATH";
        when(request.getPathInfo()).thenReturn(badPath);
        servlet.doGet(request, response);
        verify(response).sendError(eq(404), anyString());
    }

    @Test
    public void testDoGetProductNotFound() throws IOException, ProductNotFoundException, ServletException {
        when(productDao.getProduct(anyLong())).thenThrow(new ProductNotFoundException());
        servlet.doGet(request, response);
        verify(response).sendError(eq(404), anyString());
    }

    @Test
    public void testDoPost() throws ServletException, IOException, ProductNotFoundException, OutOfStockException {
        servlet.doPost(request, response);
        verify(cartService).add(cart, id, quantity);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostBadId() throws ServletException, IOException {
        String badPath = "BAD_PATH";
        when(request.getPathInfo()).thenReturn(badPath);
        servlet.doPost(request, response);
        verify(request).setAttribute(eq(ProductDetailsPageServlet.ERROR), anyString());
    }
}
