package com.es.phoneshop.web.page;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.cart.exception.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.exception.ProductNotFoundException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailsPageServlet extends HttpServlet {
    private static final String PRODUCT = "product";
    private static final String ID = "id";
    public static final String ERROR = "error";
    public static final String QUANTITY = "quantity";
    private static final String RECENTLY_VIEWED = "recentlyViewed";
    private static final Integer RECENTLY_VIEWED_SIZE = 3;

    private ProductDao productDao;
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
            Product product = productDao.getProduct(id);
            addToRecentlyViewed(request, product);
            request.setAttribute(PRODUCT, product);
            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
        } catch (ProductNotFoundException | NumberFormatException exception) {
            response.sendError(404, "Product not found");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id;
        Integer quantity;
        try {
            id = getProductId(request);
            quantity = Integer.parseInt(request.getParameter(QUANTITY));
            Cart cart = cartService.getCart(request);
            cartService.add(cart, id, quantity);
        } catch (ProductNotFoundException e) {
            response.sendError(404, "Product not found");
            return;
        } catch (NumberFormatException e) {
            request.setAttribute(ERROR, "Not a number");
            doGet(request, response);
            return;
        } catch (OutOfStockException | IllegalArgumentException e) {
            request.setAttribute(ERROR, e.getMessage());
            doGet(request, response);
            return;
        }
        response.sendRedirect(request.getRequestURI() + "?message=Added successfully&" + QUANTITY + "=" + quantity);
    }

    private long getProductId(HttpServletRequest request) throws NumberFormatException {
        String id = request.getPathInfo().substring(1);
        return Long.parseLong(id);
    }

    private void addToRecentlyViewed(HttpServletRequest request, Product product) {
        HttpSession session = request.getSession();
        List<Product> recentlyViewed = (List<Product>) session.getAttribute(RECENTLY_VIEWED);
        if (recentlyViewed == null) {
            recentlyViewed = new ArrayList<>();
            session.setAttribute(RECENTLY_VIEWED, recentlyViewed);
        }
        if (recentlyViewed.contains(product)) {
            return;
        }
        if (recentlyViewed.size() >= RECENTLY_VIEWED_SIZE) {
            recentlyViewed.remove(0);
        }
        recentlyViewed.add(product);
    }
}
