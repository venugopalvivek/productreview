package com.intuit.vivek.services;

import com.intuit.vivek.exceptions.ProductReviewException;
import com.intuit.vivek.persistence.dao.ProductDao;
import com.intuit.vivek.persistence.dao.ReviewDao;
import com.intuit.vivek.persistence.model.ProductEntity;
import com.intuit.vivek.persistence.model.ReviewEntity;
import com.intuit.vivek.rest.dto.PostReviewDto;
import com.intuit.vivek.rest.dto.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vvenugopal on 10/21/17.
 */
@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ReviewDao reviewDao;

    public List<ProductEntity> getProducts() {
        return productDao.findAll();
    }

    @Transactional(readOnly = true)
    public List<ProductEntity> getProducts(int score) throws ProductReviewException {
        if (score < 0 || score > 5) {
            throw new ProductReviewException(400, "Invalid score");
        }
        double bottomScore = score * 1.0;
        double topScore = (score + 1) * 1.0;
        topScore = topScore - 0.1;
        return productDao.findAllByScore(bottomScore, topScore);
    }

    @Transactional(readOnly = true)
    public ProductEntity getProduct(int id) throws ProductReviewException {
        ProductEntity product = productDao.findOne(id);
        if (product != null) {
            return product;
        }
        throw new ProductReviewException(404, "Product not found");
    }

    @Transactional(readOnly = true)
    public List<ReviewDto> getProductReviews(int id) throws ProductReviewException {
        List<ReviewDto> reviews = new LinkedList<>();
        if (getProduct(id) != null) {
            List<ReviewEntity> reviewEntities = reviewDao.findAllByProduct(id);
            for (ReviewEntity entity : reviewEntities) {
                reviews.add(entity.fromEntity());
            }
        }
        return reviews;
    }

    @Transactional(readOnly = true)
    public List<ReviewDto> getProductReviews(int id, int score) throws ProductReviewException {
        if (score < 0 || score > 5) {
            throw new ProductReviewException(400, "Invalid score");
        }
        List<ReviewDto> reviews = new LinkedList<>();
        if (getProduct(id) != null) {
            List<ReviewEntity> reviewEntities = reviewDao.findAllByProductAndScore(id, score);
            for (ReviewEntity entity : reviewEntities) {
                reviews.add(entity.fromEntity());
            }
        }
        return reviews;
    }

    @Transactional
    public ReviewEntity createProductReview(int id, PostReviewDto postReviewDto) throws ProductReviewException {
        if (getProduct(id) != null) {
            ReviewEntity existingReview = reviewDao.findByProductIdAndEmail(id, postReviewDto.getReviewerEmail());
            if (existingReview != null) {
                throw new ProductReviewException(409, "A review from same reviewer already exists");
            }
            ReviewEntity entity = ReviewEntity.toEntity(id, postReviewDto);
            ReviewEntity savedEntity = reviewDao.save(entity);
            return savedEntity;
        }
        return null;
    }

}
