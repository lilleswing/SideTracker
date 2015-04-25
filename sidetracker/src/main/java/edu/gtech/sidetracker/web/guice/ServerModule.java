package edu.gtech.sidetracker.web.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;
import edu.gtech.sidetracker.web.guice.aop.auth.AuthorizationInterceptor;
import edu.gtech.sidetracker.web.guice.aop.auth.Authorize;
import org.aopalliance.intercept.MethodInterceptor;
import org.hibernate.SessionFactory;

public class ServerModule extends AbstractModule {

    public void configure() {
        final MethodInterceptor authInterceptor = new AuthorizationInterceptor(getProvider(RequestState.class));
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(Authorize.class), authInterceptor);
    }

    @Provides @Singleton
    public SessionFactory provideEntityManagerFactory(final HibernateUtil hibernateUtil) {
        return hibernateUtil.getSessionFactory();
    }
}
