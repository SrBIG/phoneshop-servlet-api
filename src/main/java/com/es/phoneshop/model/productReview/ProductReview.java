package com.es.phoneshop.model.productReview;

public class ProductReview {
    private long productId;
    private String userName;
    private int rating;
    private String comment;

    public ProductReview(String userName, int rating, String comment, long productId) {
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
        this.productId = productId;
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
}
