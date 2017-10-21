package com.intuit.vivek.persistence.dao;

import com.intuit.vivek.persistence.model.ProductEntity;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;

/**
 * Created by vvenugopal on 10/21/17.
 */
public interface ProductDao extends CrudRepository<ProductEntity, Integer> {

    @Query("select p from ProductEntity p where p.totalScore = ?1")
    List<ProductEntity> findAllByScore(double score);

    @Query("select p from ProductEntity p where p.totalScore >= ?1 and p.totalScore <= ?2")
    List<ProductEntity> findAllByScore(double bottomScore, double topScore);

    @Query("select p from ProductEntity p")
    List<ProductEntity> findAll();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from ProductEntity p where p.id = :id")
    ProductEntity findOneForUpdate(@Param("id") int id);

}
