package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.exception.ProductNotFoundException;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteItemServlet extends HttpServlet {
    private CartService cartService;

    @Override
    public void init() {
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getPathInfo().substring(1);
        try {
            long productId = Long.parseLong(id);
            Cart cart = cartService.getCart(request);
            cartService.delete(cart, productId);
            response.sendRedirect(request.getContextPath() + "/cart" + "?message=Cart item deleted successfully");
        } catch (NumberFormatException | ProductNotFoundException e) {
            response.sendError(404, "Product not found");
        }
    }
}
