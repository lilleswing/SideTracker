package edu.gtech.sidetracker.web.guice;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import edu.gtech.sidetracker.web.dao.CommentDao;
import edu.gtech.sidetracker.web.dao.Dao;
import edu.gtech.sidetracker.web.model.Comment;

import java.util.HashMap;
import java.util.Map;

public class SideTrackerServletModule extends JerseyServletModule {
    private final ResourceConfig rc;

    public SideTrackerServletModule(final ResourceConfig rc) {
        this.rc = rc;
    }

    @Override
    protected void configureServlets() {
        bind(new TypeLiteral<Dao<Comment>>() {}).to(CommentDao.class);

        for (Class<?> resource : rc.getClasses()) {
            System.out.println("Binding resource: " + resource.getName());
            bind(resource);
        }

        Map<String, String> initParams = new HashMap<String, String>();
        initParams.put("com.sun.jersey.config.feature.Trace",
                "true");
        initParams.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
        serve("/api/*").with(GuiceContainer.class, initParams);
    }


    @Provides
    @Singleton
    ObjectMapper objectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper;
    }

    @Provides @Singleton
    JacksonJsonProvider jacksonJsonProvider(ObjectMapper mapper) {
        return new JacksonJsonProvider(mapper);
    }
}
