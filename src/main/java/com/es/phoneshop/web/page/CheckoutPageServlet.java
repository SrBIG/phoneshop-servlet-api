package com.es.phoneshop.web.page;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.order.*;
import com.es.phoneshop.model.order.dao.ArrayListOrderDao;
import com.es.phoneshop.model.order.dao.OrderDao;
import com.es.phoneshop.model.person.Customer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class CheckoutPageServlet extends HttpServlet {
    private CartService cartService;
    private OrderService orderService;
    private OrderDao orderDao;

    @Override
    public void init() {
        cartService = HttpSessionCartService.getInstance();
        orderService = OrderServiceImpl.getInstance();
        orderDao = ArrayListOrderDao.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        renderCheckoutPage(request, response, cart);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        boolean hasError = false;

        String name = request.getParameter("name");
        if (isInvalidStringParameter(name)) {
            hasError = true;
            request.setAttribute("nameError", "Name is required");
        }

        String surname = request.getParameter("surname");
        if (isInvalidStringParameter(surname)) {
            hasError = true;
            request.setAttribute("surnameError", "Surname is required");
        }

        String phoneNumber = request.getParameter("phoneNumber");
        if (isInvalidStringParameter(phoneNumber)) {
            hasError = true;
            request.setAttribute("phoneNumberError", "Phone number is required");
        }

        DeliveryMode deliveryMode = null;
        try {
            deliveryMode = DeliveryMode.valueOf(request.getParameter("deliveryMode"));
        } catch (IllegalArgumentException | NullPointerException e) {
            hasError = true;
            request.setAttribute("deliveryModeError", "Please check delivery mode");
        }

        Date deliveryDate = null;
        try {
            String date = request.getParameter("deliveryDate");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            deliveryDate = sdf.parse(date);
        } catch (ParseException | NullPointerException e) {
            hasError = true;
            request.setAttribute("deliveryDateError", "Please check delivery date");
        }

        String deliveryAddress = request.getParameter("deliveryAddress");
        if (isInvalidStringParameter(deliveryAddress)) {
            hasError = true;
            request.setAttribute("deliveryAddressError", "Delivery address is required");
        }

        PaymentMethod paymentMethod = null;
        try {
            paymentMethod = PaymentMethod.valueOf(request.getParameter("paymentMethod"));
        } catch (IllegalArgumentException | NullPointerException e) {
            hasError = true;
            request.setAttribute("paymentMethodError", "Please check payment method");
        }

        Cart cart = cartService.getCart(request);
        if (hasError) {
            request.setAttribute("cart", cart);
            renderCheckoutPage(request, response, cart);
            return;
        }

        Order order = orderService.createOrder(cart);
        Customer customer = new Customer(name, surname, phoneNumber);
        order.setCustomer(customer);
        order.setDeliveryMode(deliveryMode);
        order.setDeliveryDate(deliveryDate);
        order.setDeliveryAddress(deliveryAddress);
        order.setPaymentMethod(paymentMethod);
        orderService.placeOrder(order);
        cartService.clearCart(request);

        request.setAttribute("order", order);
        response.sendRedirect(request.getContextPath() + "/orderOverview/" + order.getSecureId());
    }

    private boolean isInvalidStringParameter(String parameter) {
        return Objects.isNull(parameter) || parameter.isEmpty();
    }

    private void renderCheckoutPage(HttpServletRequest request, HttpServletResponse response, Cart cart)
            throws ServletException, IOException {
        request.setAttribute("cart", cart);
        request.setAttribute("deliveryModes", orderService.getDeliveryModes());
        request.setAttribute("paymentMethods", orderService.getPaymentMethod());
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }
}