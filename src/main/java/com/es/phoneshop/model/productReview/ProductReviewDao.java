package com.es.phoneshop.model.productReview;

import java.util.List;

public interface ProductReviewDao {
    void addProductReview(ProductReview productReview);

    List<ProductReview> getApproveProductReviews(long productId);

    List<ProductReview> getAllProductsReviews();

    void approve(long id);
}

