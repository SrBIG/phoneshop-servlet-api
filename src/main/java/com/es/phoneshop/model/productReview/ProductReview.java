package com.es.phoneshop.model.productReview;

import java.util.concurrent.atomic.AtomicInteger;

public class ProductReview {
    public static final Integer MODERATION = 0;
    public static final Integer APPROVE = 1;

    private long productId;
    private String userName;
    private int rating;
    private String comment;
    private Integer status;
    private long id;

    public ProductReview(String userName, int rating, String comment, long productId) {
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
        this.productId = productId;
        this.status = MODERATION;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
