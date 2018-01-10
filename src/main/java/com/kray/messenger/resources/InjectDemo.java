package com.kray.messenger.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/params")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemo {

    @GET
    @Path("/matrix")
    public String matrixParamDemo(@MatrixParam("matrixParam") String matrixParam) {
        return matrixParam;
    }

    @GET
    @Path("/header")
    public String headerParamDemo(@HeaderParam("headerParam") String headerParam) {
        return headerParam;
    }

    @GET
    @Path("/cookie")
    public String cookieParamDemo(@CookieParam("cookieParam") String cookieParam) {
        return cookieParam;
    }

    @GET
    @Path("/context")
    public String getParamsUsingContext(@Context UriInfo uriInfo,
                                        @Context HttpHeaders headers) {
        return "Path: " + uriInfo.getAbsolutePath().toString() + "\n" +
                "Cookies: " + headers.getCookies().toString();
    }

}
