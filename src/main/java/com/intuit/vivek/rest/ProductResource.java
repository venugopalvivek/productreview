package com.intuit.vivek.rest;

import com.intuit.vivek.exceptions.ProductReviewException;
import com.intuit.vivek.persistence.dao.ProductDao;
import com.intuit.vivek.persistence.model.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by vvenugopal on 10/21/17.
 */
@Component
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Autowired
    private ProductDao productDao;

    @GET
    @Path("/")
    public Response getAllProducts(@QueryParam("score") Integer totalScore) throws ProductReviewException {
        List<ProductEntity> products = null;
        if (totalScore != null) {
            products = productDao.findAll();
        } else {
            if (totalScore < 1 || totalScore > 5) {
                throw new ProductReviewException(400, "Invalid score");
            }
            double bottomScore = totalScore * 1.0;
            double topScore = (totalScore + 1) * 1.0;
            topScore = topScore - 0.1;
            products = productDao.findAllByScore(bottomScore, topScore);
        }
        return Response.status(Response.Status.OK).entity(products).build();
    }

    @GET
    @Path(("/{id}"))
    public Response getProduct(@PathParam("id") int id) throws ProductReviewException {
        ProductEntity product = productDao.findOne(id);
        if (product != null) {
            throw new ProductReviewException(400, "Product Not Found");
        }
        return Response.status(Response.Status.OK).entity(product).build();
    }

}
