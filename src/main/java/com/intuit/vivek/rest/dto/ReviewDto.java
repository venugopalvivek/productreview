package com.intuit.vivek.rest.dto;

import java.util.Date;

/**
 * Created by vvenugopal on 10/21/17.
 */
public class ReviewDto extends PostReviewDto {
    private Date createdAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
