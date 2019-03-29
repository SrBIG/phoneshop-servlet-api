package com.es.phoneshop.web.page;

import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.cart.exception.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.exception.ProductNotFoundException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    private final String PRODUCT = "product";
    private final String ID = "id";

    protected ProductDao productDao;
    private CartService cartService;

    @Override
    public void init(ServletConfig config) {
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long id = getProductId(request);
            request.setAttribute(PRODUCT, productDao.getProduct(id));
            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
        } catch (ProductNotFoundException | NumberFormatException exception) {
            response.sendError(404, "Product not found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id;
        Integer quantity;

        try {
            id = getProductId(request);
            quantity = Integer.parseInt(request.getParameter("quantity"));
        } catch (NumberFormatException exception) {
            request.setAttribute("error", "Not a number");
            doGet(request, response);
            return;
        }
        try {
            cartService.add(id, quantity);
        } catch (ProductNotFoundException e) {
            response.sendError(404, "product not found");
            doGet(request, response);
            return;
        } catch (OutOfStockException e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
            return;
        }
        response.sendRedirect(request.getRequestURI() + "?message=Added successfully&quantity=" + quantity);
    }

    private long getProductId(HttpServletRequest request) throws NumberFormatException {
        String id = request.getPathInfo().substring(1);
        return Long.parseLong(id);
    }
}
