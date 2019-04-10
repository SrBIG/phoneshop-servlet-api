package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteItemServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private CartService cartService;
    @Mock
    private Cart cart;

    private String pathInfo = "/1";
    private String badPathInfo = "bad path info";

    @InjectMocks
    private DeleteItemServlet servlet;

    @Before
    public void setup() {
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(cartService.getCart(request)).thenReturn(cart);
    }

    @Test
    public void testDoPost() throws IOException {
        servlet.doPost(request, response);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostBadId() throws IOException {
        when(request.getPathInfo()).thenReturn(badPathInfo);
        servlet.doPost(request, response);
        verify(response).sendError(eq(404), anyString());
    }
}
