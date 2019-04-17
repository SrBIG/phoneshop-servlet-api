package com.es.phoneshop.model.productReview;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ArrayListProductReviewDao implements ProductReviewDao {
    private static ProductReviewDao instance;
    private List<ProductReview> productReviews;

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
    }

    @Override
    public void addProductReview(ProductReview productReview) throws IllegalArgumentException{
        if(Objects.isNull(productReview)){
            throw new IllegalArgumentException();
        }
        productReviews.add(productReview);
    }

    @Override
    public List<ProductReview> getProductReviews(long productId) {
        return productReviews.stream()
                .filter(productReview -> productReview.getProductId() == productId)
                .collect(Collectors.toList());
    }
}
