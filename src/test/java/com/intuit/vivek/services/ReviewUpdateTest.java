package com.intuit.vivek.services;

import com.intuit.vivek.persistence.dao.ProductDao;
import com.intuit.vivek.persistence.dao.ReviewDao;
import com.intuit.vivek.persistence.model.ProductEntity;
import com.intuit.vivek.persistence.model.ReviewEntity;
import com.intuit.vivek.persistence.model.ReviewState;
import com.intuit.vivek.updater.ProductReviewScoreUpdater;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vvenugopal on 10/21/17.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {TestDbConfig.class}, loader = AnnotationConfigContextLoader.class)
//@PropertySource("classpath:application-test.properties")
@RunWith(MockitoJUnitRunner.class)
public class ReviewUpdateTest {

    @Autowired
    @InjectMocks
    private ProductService service;

    @Autowired
    @InjectMocks
    private ProductReviewScoreUpdater updater;

    @Mock
    private ReviewDao reviewDao;

    @Mock
    private ProductDao productDao;

    private List<ProductEntity> getProducts() {
        List<ProductEntity> products = new ArrayList<>(4);

        ProductEntity product1 = new ProductEntity();
        product1.setId(1);
        product1.setName("Pen");
        product1.setReviewsCount(4);
        product1.setTotalScore(3.0);
        products.add(product1);

        return products;
    }

    private List<ReviewEntity> getPendingReviews() {
        ReviewEntity entity = new ReviewEntity();
        entity.setScore(1);
        entity.setProductId(1);
        entity.setState(ReviewState.PENDING);
        return Collections.singletonList(entity);
    }

    @Test
    public void testReviewAggregation() throws Exception {
        Mockito.when(productDao.findOne(Matchers.anyInt())).thenReturn(getProducts().get(0));
        ProductEntity product = service.getProduct(1);
        Assert.assertNotNull(product);
        Assert.assertTrue(product.getName().equals("Pen"));

        Mockito.when(reviewDao.findByStateTopN(ReviewState.PENDING, new PageRequest(0, 1))).thenReturn(getPendingReviews());
        Mockito.when(productDao.findOneForUpdate(Matchers.anyInt())).thenReturn(getProducts().get(0));

        updater.updateProductReviewScore();

        ArgumentCaptor<ProductEntity> prodArg = ArgumentCaptor.forClass(ProductEntity.class);
        Mockito.verify(productDao).save(prodArg.capture());
        Assert.assertEquals(5, prodArg.getValue().getReviewsCount());
        Assert.assertEquals(2.6, prodArg.getValue().getTotalScore(), 0.00001);

        ArgumentCaptor<ReviewEntity> revArg = ArgumentCaptor.forClass(ReviewEntity.class);
        Mockito.verify(reviewDao).save(revArg.capture());
        Assert.assertEquals(ReviewState.APPROVED, revArg.getValue().getState());
    }

}
