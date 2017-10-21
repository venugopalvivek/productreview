package com.intuit.vivek.persistence.model;

import com.intuit.vivek.persistence.converters.ReviewStateConverter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by vvenugopal on 10/21/17.
 */
@Entity
@Table(name = "reviews")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "score")
    private int score;

    @Column(name = "comments", length = 512)
    private String comments;

    @Column(name = "reviewer_name", length = 128)
    private String reviewerName;

    @Column(name = "reviewer_email", length = 256)
    private String reviewerEmail;

    @Column(name = "state")
    @Convert(converter = ReviewStateConverter.class)
    private ReviewState state;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "modified_at")
    private Date modifiedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

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

    public ReviewState getState() {
        return state;
    }

    public void setState(ReviewState state) {
        this.state = state;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
