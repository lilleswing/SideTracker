package edu.gtech.sidetracker.web.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;

public class DbModule extends AbstractModule {

    public void configure() {

    }

    @Provides @Singleton
    public SessionFactory provideEntityManagerFactory(final HibernateUtil hibernateUtil) {
        return hibernateUtil.getSessionFactory();
    }
}
