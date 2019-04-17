package com.es.phoneshop.web;

import com.es.phoneshop.model.productReview.ArrayListProductReviewDao;
import com.es.phoneshop.model.productReview.ProductReviewDao;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductReviewApproveServlet extends HttpServlet {
    private ProductReviewDao productReviewDao;

    @Override
    public void init() {
        productReviewDao = ArrayListProductReviewDao.getInstance();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getPathInfo().substring(1);
        try {
            long reviewId = Long.parseLong(id);
            productReviewDao.approve(reviewId);
            response.sendRedirect(request.getContextPath() + "/productsReviewsModeration" + "?message=Review deleted successfully");
        } catch (NumberFormatException  e) {
            response.sendError(404, "Review not found");
        }
    }
}
