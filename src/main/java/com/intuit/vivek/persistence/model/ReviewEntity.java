package com.intuit.vivek.persistence.model;

import com.intuit.vivek.exceptions.ProductReviewException;
import com.intuit.vivek.persistence.converters.ReviewStateConverter;
import com.intuit.vivek.rest.dto.PostReviewDto;
import com.intuit.vivek.rest.dto.ReviewDto;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by vvenugopal on 10/21/17.
 */
@Entity
@Table(name = "reviews")
public class ReviewEntity {

    static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

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

    public static ReviewEntity toEntity(int productId, PostReviewDto dto) throws ProductReviewException {
        ReviewEntity entity = new ReviewEntity();

        entity.setProductId(productId);

        if (dto.getScore() < 1 || dto.getScore() > 5) {
            throw new ProductReviewException(400, "Invalid score");
        }
        entity.setScore(dto.getScore());

        if (dto.getComments() != null && dto.getComments().length() > 512) {
            throw new ProductReviewException(400, "Comments size exceeds maximum length");
        }
        entity.setComments(dto.getComments());

        if (StringUtils.isBlank(dto.getReviewerName())) {
            throw new ProductReviewException(400, "Reviewer name cannot be empty");
        }
        if (dto.getReviewerName().length() > 128) {
            throw new ProductReviewException(400, "Reviewer name size exceeds maximum length");
        }
        entity.setReviewerName(dto.getReviewerName());

        if (StringUtils.isBlank(dto.getReviewerEmail())) {
            throw new ProductReviewException(400, "Reviewer email cannot be empty");
        }
        if (dto.getReviewerEmail().length() > 128) {
            throw new ProductReviewException(400, "Reviewer email size exceeds maximum length");
        }
        if (!VALID_EMAIL_ADDRESS_REGEX.matcher(dto.getReviewerEmail()).find()) {
            throw new ProductReviewException(400, "Reviewer email is not valid");
        }
        entity.setReviewerEmail(dto.getReviewerEmail());

        entity.setState(ReviewState.PENDING);

        Date now = new Date();
        entity.setCreatedAt(now);
        entity.setModifiedAt(now);

        return entity;
    }

    public ReviewDto fromEntity() {
        ReviewDto dto = new ReviewDto();
        dto.setScore(this.getScore());
        dto.setComments(this.getComments());
        dto.setReviewerEmail(this.getReviewerEmail());
        dto.setReviewerName(this.getReviewerName());
        dto.setCreatedAt(this.getCreatedAt());
        return dto;
    }

}
