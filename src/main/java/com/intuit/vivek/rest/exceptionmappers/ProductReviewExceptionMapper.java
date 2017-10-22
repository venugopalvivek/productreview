package com.intuit.vivek.rest.exceptionmappers;

import com.intuit.vivek.exceptions.ProductReviewException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by vvenugopal on 10/21/17.
 */
@Provider
public class ProductReviewExceptionMapper implements ExceptionMapper<ProductReviewException> {

    @Override
    public Response toResponse(ProductReviewException exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.code = exception.getCode();
        exceptionResponse.message = exception.getMessage();
        if (exception.getCause() != null)
            exceptionResponse.cause = exception.getCause().getMessage();

        return Response.status(exception.getCode()).entity(exceptionResponse).build();
    }
}
