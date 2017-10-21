package com.intuit.vivek.rest.dto;

import com.intuit.vivek.exceptions.ProductReviewException;
import com.intuit.vivek.persistence.model.ReviewEntity;

/**
 * Created by vvenugopal on 10/21/17.
 */
public class PostReviewDto {

    private int score;
    private String comments;
    private String reviewerName;
    private String reviewerEmail;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getReviewerEmail() {
        return reviewerEmail;
    }

    public void setReviewerEmail(String reviewerEmail) {
        this.reviewerEmail = reviewerEmail;
    }

}
