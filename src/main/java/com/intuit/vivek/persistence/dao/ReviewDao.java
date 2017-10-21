package com.intuit.vivek.persistence.dao;

import com.intuit.vivek.persistence.model.ReviewEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by vvenugopal on 10/21/17.
 */
public interface ReviewDao extends CrudRepository<ReviewEntity, Integer> {

    @Query("select r from ReviewEntity r where r.productId = ?1 order by createdAt desc")
    List<ReviewEntity> findAllByProduct(int productId);

    @Query("select r from ReviewEntity r where r.productId = ?1 and r.score = ?2 order by createdAt desc")
    List<ReviewEntity> findAllByProductAndScore(int productId, int score);

}
