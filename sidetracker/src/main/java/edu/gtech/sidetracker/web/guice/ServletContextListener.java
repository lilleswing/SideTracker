package edu.gtech.sidetracker.web.guice;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import edu.gtech.sidetracker.web.guice.filters.BasicAuthFilter;
import edu.gtech.sidetracker.web.guice.filters.UnitOfWorkFilter;

public class ServletContextListener extends GuiceServletContextListener {
    private static final String SERVICES_PACKAGE = "edu.gtech.sidetracker.web.server";

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new JerseyServletModule() {

            @Override
            protected void configureServlets() {
                bind(GuiceContainer.class);

                final PackagesResourceConfig resourceConfig = new PackagesResourceConfig(SERVICES_PACKAGE);
                for (final Class<?> resource : resourceConfig.getClasses()) {
                    bind(resource);
                }

                bind(MessageBodyReader.class).to(JacksonJsonProvider.class);
                bind(MessageBodyWriter.class).to(JacksonJsonProvider.class);

                filter("/api/*").through(UnitOfWorkFilter.class);
                filter("/api/*").through(BasicAuthFilter.class);
                serve("/api/*").with(GuiceContainer.class);
            }
        }, new DbModule());
    }
}
