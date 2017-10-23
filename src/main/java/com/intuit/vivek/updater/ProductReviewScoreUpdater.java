package com.intuit.vivek.updater;

import com.intuit.vivek.persistence.dao.ProductDao;
import com.intuit.vivek.persistence.dao.ReviewDao;
import com.intuit.vivek.persistence.model.ProductEntity;
import com.intuit.vivek.persistence.model.ReviewEntity;
import com.intuit.vivek.persistence.model.ReviewState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by vvenugopal on 10/21/17.
 */
@Service
public class ProductReviewScoreUpdater {

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private ProductDao productDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateProductReviewScore() {
        List<ReviewEntity> reviews = reviewDao.findByStateTopN(ReviewState.PENDING, new PageRequest(0, 1));
        if (reviews != null && reviews.size() > 0) {
            ReviewEntity review = reviews.get(0);
            ProductEntity product = productDao.findOneForUpdate(review.getProductId());

            double newScore = (review.getScore() * 1.0) + (product.getTotalScore() * product.getReviewsCount());
            newScore = newScore / ((1 + product.getReviewsCount()) * 1.0);
            product.setTotalScore(newScore);
            product.setReviewsCount(product.getReviewsCount()+1);
            productDao.save(product);

            review.setState(ReviewState.APPROVED);
            review.setModifiedAt(new Date());
            reviewDao.save(review);
        }
    }

}
