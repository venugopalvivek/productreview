package com.intuit.vivek.services;

import com.intuit.vivek.exceptions.ProductReviewException;
import com.intuit.vivek.persistence.dao.ProductDao;
import com.intuit.vivek.persistence.dao.ReviewDao;
import com.intuit.vivek.persistence.model.ProductEntity;
import com.intuit.vivek.persistence.model.ReviewEntity;
import com.intuit.vivek.rest.dto.PostReviewDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vvenugopal on 10/21/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Autowired
    @InjectMocks
    private ProductService service;

    @Mock
    private ReviewDao reviewDao;

    @Mock
    private ProductDao productDao;

    private List<ProductEntity> getProducts() {
        List<ProductEntity> products = new ArrayList<>(4);

        ProductEntity product1 = new ProductEntity();
        product1.setId(1);
        product1.setName("Pen");
        product1.setReviewsCount(0);
        product1.setTotalScore(0.0);
        products.add(product1);

        ProductEntity product2 = new ProductEntity();
        product2.setId(1);
        product2.setName("Pen");
        product2.setReviewsCount(0);
        product2.setTotalScore(0.0);
        products.add(product2);

        return products;
    }

    @Test
    public void testGetProducts() {
        Mockito.when(productDao.findAll()).thenReturn(getProducts());
        List<ProductEntity> products = service.getProducts();
        Assert.assertNotNull(products);
        Assert.assertTrue(products.size() == 2);
    }

    @Test
    public void testGetProduct() throws Exception {
        Mockito.when(productDao.findOne(Matchers.anyInt())).thenReturn(getProducts().get(0));
        ProductEntity product = service.getProduct(1);
        Assert.assertNotNull(product);
        Assert.assertTrue(product.getName().equals("Pen"));
    }

    @Test(expected = ProductReviewException.class)
    public void testNonExistentProduct() throws Exception {
        ProductEntity product = service.getProduct(100);
    }

    @Test
    public void testPostNewReview() throws Exception {
        Mockito.when(productDao.findOne(Matchers.anyInt())).thenReturn(getProducts().get(0));
        Mockito.when(reviewDao.save(Matchers.any(ReviewEntity.class))).then(new Answer<ReviewEntity>() {
            @Override
            public ReviewEntity answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                ReviewEntity entity = (ReviewEntity) arguments[0];
                entity.setId(123);
                return entity;
            }
        });
        PostReviewDto review = new PostReviewDto();
        review.setScore(3);
        review.setComments("Nice pen");
        review.setReviewerName("Vivek");
        review.setReviewerEmail("abc@gmail.com");
        ReviewEntity entity = service.createProductReview(1, review);
        Assert.assertNotNull(entity);
    }

    @Test(expected = ProductReviewException.class)
    public void testPostReviewNonExistingProduct() throws Exception {
        PostReviewDto review = new PostReviewDto();
        review.setScore(3);
        review.setComments("Nice pen");
        review.setReviewerName("Vivek");
        review.setReviewerEmail("abc@gmail.com");
        service.createProductReview(1, review);
    }

    @Test(expected = ProductReviewException.class)
    public void testRepostReview() throws Exception {
        Mockito.when(productDao.findOne(Matchers.anyInt())).thenReturn(getProducts().get(0));
        Mockito.when(reviewDao.findByProductIdAndEmail(Matchers.anyInt(), Matchers.anyString()))
                .then(new Answer<ReviewEntity>() {
                    @Override
                    public ReviewEntity answer(InvocationOnMock invocation) throws Throwable {
                        Object[] arguments = invocation.getArguments();
                        int productId = (int) arguments[0];
                        String reviewerEmail = (String) arguments[1];
                        ReviewEntity entity = new ReviewEntity();
                        entity.setId(123);
                        entity.setProductId(productId);
                        entity.setReviewerEmail(reviewerEmail);
                        entity.setReviewerName("Test");
                        return entity;
                    }
                });
        PostReviewDto review = new PostReviewDto();
        review.setScore(3);
        review.setComments("Nice pen");
        review.setReviewerName("Vivek");
        review.setReviewerEmail("abc@gmail.com");
        service.createProductReview(1, review);
    }

}
