package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.order.DeliveryMode;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderService;
import com.es.phoneshop.model.order.PaymentMethod;
import com.es.phoneshop.web.page.CheckoutPageServlet;
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

import java.io.IOException;
import java.util.Date;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutPageServletTest {
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
    @Mock
    private OrderService orderService;
    @Mock
    private Order order;

    private String name = "name";
    private String surname = "surname";
    private String phone = "phone";
    private String deliveryMode = "COURIER";
    private String deliveryDate = "2020-03-25";
    private String deliveryAddress = "address";
    private String paymentMethod = "CREDITCART";

    private String nullParameter = null;

    @InjectMocks
    private CheckoutPageServlet servlet;

    @Before
    public void setup() {
        when(request.getParameter("name")).thenReturn(name);
        when(request.getParameter("surname")).thenReturn(surname);
        when(request.getParameter("phoneNumber")).thenReturn(phone);
        when(request.getParameter("deliveryMode")).thenReturn(deliveryMode);
        when(request.getParameter("deliveryDate")).thenReturn(deliveryDate);
        when(request.getParameter("deliveryAddress")).thenReturn(deliveryAddress);
        when(request.getParameter("paymentMethod")).thenReturn(paymentMethod);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        when(cartService.getCart(request)).thenReturn(cart);
        when(orderService.createOrder(cart)).thenReturn(order);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).setAttribute("order", order);
        verify(orderService).getDeliveryModes();
        verify(orderService).getPaymentMethod();
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostWithErrors() throws IOException, ServletException {
        when(request.getParameter("name")).thenReturn(nullParameter);
        when(request.getParameter("surname")).thenReturn(nullParameter);
        when(request.getParameter("phoneNumber")).thenReturn(nullParameter);
        when(request.getParameter("deliveryMode")).thenReturn(nullParameter);
        when(request.getParameter("deliveryDate")).thenReturn(nullParameter);
        when(request.getParameter("deliveryAddress")).thenReturn(nullParameter);
        when(request.getParameter("paymentMethod")).thenReturn(nullParameter);

        servlet.doPost(request, response);

        verify(request, times(7)).setAttribute(anyString(), anyString());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostSuccessfully() throws IOException, ServletException {
        servlet.doPost(request, response);

        verify(order).setDeliveryMode(any(DeliveryMode.class));
        verify(order).setDeliveryDate(any(Date.class));
        verify(order).setDeliveryAddress(deliveryAddress);
        verify(order).setPaymentMethod(any(PaymentMethod.class));
        verify(order).setDeliveryMode(any(DeliveryMode.class));
        verify(orderService).placeOrder(order);
        verify(cartService).clearCart(request);
        verify(response).sendRedirect(anyString());
    }
}
