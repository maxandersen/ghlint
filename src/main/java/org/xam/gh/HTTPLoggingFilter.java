package org.xam.gh;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

public class HTTPLoggingFilter implements ClientRequestFilter, ClientResponseFilter {

    private static final Logger logger = Logger.getLogger(EntityLoggingFilter.class.getName());

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        logger.info(requestContext.getUri().toString());
        logger.info(requestContext.getHeaders().toString());

    }

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        //logger.info(responseContext.getUri().toString());
        logger.info(responseContext.getHeaders().toString());
    }
}