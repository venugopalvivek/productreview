package com.intuit.vivek.persistence.dao;

import com.intuit.vivek.persistence.model.ProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by vvenugopal on 10/21/17.
 */
public interface ProductDao extends CrudRepository<ProductEntity, Integer> {

    @Query("select p from ProductEntity p where p.totalScore = ?1")
    List<ProductEntity> findAllByScore(int score);

}
