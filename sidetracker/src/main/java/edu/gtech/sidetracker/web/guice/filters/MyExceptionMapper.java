package edu.gtech.sidetracker.web.guice.filters;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.google.inject.Singleton;
import edu.gtech.sidetracker.web.guice.aop.auth.AuthorizationException;


@Provider
@Singleton
public class MyExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(final Exception e) {
        if (e instanceof AuthorizationException) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
