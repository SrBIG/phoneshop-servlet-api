package com.es.phoneshop.web.page;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.cart.exception.OutOfStockException;
import com.es.phoneshop.model.product.exception.ProductNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class CartPageServlet extends HttpServlet {
    private CartService cartService = HttpSessionCartService.getInstance();


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request));
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");

        Cart cart = cartService.getCart(request);
        String[] errors = new String[productIds.length];

        for (int index = 0; index < productIds.length; index++) {
            long productId = Long.parseLong(productIds[index]);
            Integer quantity = parseQuantity(quantities[index], index, errors);
            if (quantity != null) {
                try {
                    cartService.update(cart, productId, quantity);
                } catch (ProductNotFoundException e) {
                    e.printStackTrace();
                } catch (OutOfStockException | IllegalArgumentException e) {
                    errors[index] = e.getMessage();
                }
            }
        }
        boolean hasError = Arrays.stream(errors).anyMatch(Objects::nonNull);
        if (hasError) {
            request.setAttribute("errors", errors);
            doGet(request, response);
        } else {
            response.sendRedirect(request.getRequestURI() + "?message=Updated successfully");
        }
    }

    private Integer parseQuantity(String quantityStr, int index, String[] errors) {
        try {
            return Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            errors[index] = "Not a number";
        }
        return null;
    }
}
