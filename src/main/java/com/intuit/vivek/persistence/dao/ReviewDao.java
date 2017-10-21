package com.intuit.vivek.persistence.dao;

import com.intuit.vivek.persistence.model.ReviewEntity;
import com.intuit.vivek.persistence.model.ReviewState;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;

/**
 * Created by vvenugopal on 10/21/17.
 */
public interface ReviewDao extends CrudRepository<ReviewEntity, Integer> {

    @Query("select r from ReviewEntity r where r.productId = ?1 order by r.createdAt desc")
    List<ReviewEntity> findAllByProduct(int productId);

    @Query("select r from ReviewEntity r where r.productId = ?1 and r.score = ?2 order by r.createdAt desc")
    List<ReviewEntity> findAllByProductAndScore(int productId, int score);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from ReviewEntity r where r.state = :state order by r.createdAt")
    List<ReviewEntity> findByStateTopN(@Param("state") ReviewState state, Pageable pageable);

}
