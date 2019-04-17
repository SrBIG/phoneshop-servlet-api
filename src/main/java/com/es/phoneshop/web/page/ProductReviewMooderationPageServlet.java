package com.es.phoneshop.web.page;

import com.es.phoneshop.model.productReview.ArrayListProductReviewDao;
import com.es.phoneshop.model.productReview.ProductReview;
import com.es.phoneshop.model.productReview.ProductReviewDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProductReviewMooderationPageServlet extends HttpServlet {
    private ProductReviewDao productReviewDao;

    @Override
    public void init() {
        productReviewDao = ArrayListProductReviewDao.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ProductReview> productReviews = productReviewDao.getAllProductsReviews();
        request.setAttribute("productReviews", productReviews);
        request.getRequestDispatcher("/WEB-INF/pages/productsReviewsModeration.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        boolean hasError = false;
    }

}
