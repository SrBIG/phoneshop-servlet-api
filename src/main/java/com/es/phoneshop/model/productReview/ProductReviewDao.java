package com.es.phoneshop.model.productReview;

import java.util.List;

public interface ProductReviewDao {
    void addProductReview(ProductReview productReview);

    List<ProductReview> getProductReviews(long productId);
}
