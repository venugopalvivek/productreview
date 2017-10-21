package com.intuit.vivek.rest;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by vvenugopal on 3/28/17.
 */
@Component
@Path("/status")
public class StatusResource {

    @GET
    @Path("/")
    public Response status() {
        return Response.ok("Status Fine").build();
    }

}
