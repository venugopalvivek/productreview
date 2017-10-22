package com.intuit.vivek.services;

import com.intuit.vivek.config.TestDbConfig;
import com.intuit.vivek.persistence.model.ProductEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by vvenugopal on 10/21/17.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {TestDbConfig.class}, loader = AnnotationConfigContextLoader.class)
//@PropertySource("classpath:application-test.properties")
@RunWith(MockitoJUnitRunner.class)
public class ReviewUpdateTest {

    @Inject
    private ProductService service;

    @Test
    public void testProducts() {
        List<ProductEntity> products = service.getProducts();
        Assert.assertNotNull(products);
        Assert.assertTrue(products.size() == 2);
    }

}
