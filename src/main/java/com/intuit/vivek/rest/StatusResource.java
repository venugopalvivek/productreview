package com.intuit.vivek.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by vvenugopal on 3/28/17.
 */
@Component
@Path("/status")
@Api(value = "/status", description = "Status api")
public class StatusResource {

    @GET
    @ApiOperation(value = "Status api")
    public Response status() {
        return Response.ok("Status Fine").build();
    }

}
