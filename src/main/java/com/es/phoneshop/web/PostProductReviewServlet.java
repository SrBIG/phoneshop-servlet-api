package com.es.phoneshop.web;

import com.es.phoneshop.model.productReview.ArrayListProductReviewDao;
import com.es.phoneshop.model.productReview.ProductReview;
import com.es.phoneshop.model.productReview.ProductReviewDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class PostProductReviewServlet extends HttpServlet {
    private ProductReviewDao productReviewDao;

    @Override
    public void init() {
        productReviewDao = ArrayListProductReviewDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String productId = request.getPathInfo().substring(1);
        String name = request.getParameter("name");
        String ratingStr = request.getParameter("rating");
        String comment = request.getParameter("comment");

        boolean hasError = false;

        long id;
        try {
            id = Long.parseLong(productId);
        } catch (NumberFormatException e) {
            response.sendError(404, "Product with id " + productId + " not found");
            return;
        }

        int rating = 0;
        try {
            rating = Integer.parseInt(ratingStr);
        } catch (NumberFormatException e) {
            request.setAttribute("ratingError", "Please check rating!");
            hasError = true;
        }
        if (Objects.isNull(name) || name.isEmpty()) {
            request.setAttribute("nameError", "Please input your name!");
            hasError = true;
        }
        if (Objects.isNull(comment) || comment.isEmpty()) {
            request.setAttribute("commentError", "Please input your comment!");
            hasError = true;
        }

        if (hasError){
            response.sendRedirect(request.getContextPath() + "/products/" + id);
            return;
        }

        ProductReview productReview = new ProductReview(name, rating, comment, id);

        productReviewDao.addProductReview(productReview);

        response.sendRedirect(request.getContextPath() + "/products/" + id);
    }
}
