package com.es.phoneshop.model.productReview;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ArrayListProductReviewDao implements ProductReviewDao {
    private static ProductReviewDao instance;
    private List<ProductReview> productReviews;
    private AtomicLong reviewsId;

    public static ProductReviewDao getInstance() {
        if (instance == null) {
            synchronized (ArrayListProductReviewDao.class) {
                if (instance == null) {
                    instance = new ArrayListProductReviewDao();
                }
            }
        }
        return instance;
    }

    private ArrayListProductReviewDao(){
        productReviews = new ArrayList<>();
        reviewsId = new AtomicLong();
    }

    @Override
    public void addProductReview(ProductReview productReview) throws IllegalArgumentException{
        if(Objects.isNull(productReview)){
            throw new IllegalArgumentException();
        }
        productReview.setId(reviewsId.incrementAndGet());
        productReviews.add(productReview);
    }

    @Override
    public List<ProductReview> getApproveProductReviews(long productId) {
        return productReviews.stream()
                .filter(productReview -> productReview.getProductId() == productId)
                .filter(productReview -> productReview.getStatus().equals(ProductReview.APPROVE))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductReview> getAllProductsReviews() {
        return productReviews.stream()
                .filter(productReview -> productReview.getStatus().equals(ProductReview.MODERATION))
                .collect(Collectors.toList());
    }

    @Override
    public void approve(long id) {
        productReviews.stream()
                .filter(productReview -> productReview.getId() == id)
                .forEach(productReview -> productReview.setStatus(ProductReview.APPROVE));
    }
}
