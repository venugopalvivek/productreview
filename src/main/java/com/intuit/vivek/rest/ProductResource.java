package com.intuit.vivek.rest;

import com.intuit.vivek.exceptions.ProductReviewException;
import com.intuit.vivek.persistence.model.ProductEntity;
import com.intuit.vivek.persistence.model.ReviewEntity;
import com.intuit.vivek.rest.dto.PostReviewDto;
import com.intuit.vivek.rest.dto.ReviewDto;
import com.intuit.vivek.services.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "/products", description = "Products api")
public class ProductResource {

    private ProductService service;

    @Autowired
    public void setService(ProductService service) {
        this.service = service;
    }

    @GET
    @ApiOperation(value = "Get all products",
                    response = ProductEntity.class,
                    responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Invalid score")})
    public Response getAllProducts(@QueryParam("score") Integer totalScore) throws ProductReviewException {
        List<ProductEntity> products = null;
        if (totalScore != null) {
            products = service.getProducts(totalScore);
        } else {
           products = service.getProducts();
        }
        return Response.status(Response.Status.OK).entity(products).build();
    }

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find product by id",
                    response = ProductEntity.class)
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Product not found")})
    public Response getProduct(@PathParam("id") int id) throws ProductReviewException {
        ProductEntity product = service.getProduct(id);
        return Response.status(Response.Status.OK).entity(product).build();
    }

    @GET
    @Path("/{id}/reviews")
    @ApiOperation(value = "Find reviews of given product id",
            response = ReviewDto.class,
            responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Product not found"),
                        @ApiResponse(code = 400, message = "Invalid score")})
    public Response getProductReviews(@PathParam("id") int id,
                                      @QueryParam("score") Integer score)
            throws ProductReviewException {
        List<ReviewDto> reviews = null;
        if (score != null) {
            reviews = service.getProductReviews(id, score);
        } else {
            reviews = service.getProductReviews(id);
        }
        return Response.status(Response.Status.OK).entity(reviews).build();
    }

    @POST
    @Path("/{id}/reviews")
    @ApiOperation(value = "Add a new review for given product id",
            response = ReviewDto.class,
            responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Product not found"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 409, message = "Review by reviewer already exists")})
    public Response postProductReview(@PathParam("id") int id, PostReviewDto review) throws ProductReviewException {
        ReviewEntity reviewEntity = service.createProductReview(id, review);
        if (reviewEntity != null) {
            return Response.status(Response.Status.ACCEPTED).entity(reviewEntity.getId()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(reviewEntity.getId()).build();
    }

}
