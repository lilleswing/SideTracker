package edu.gtech.sidetracker.web.guice.filters;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;

@Singleton
public class UnitOfWorkFilter implements Filter{
    private final SessionFactory sessionFactory;

    @Inject
    public UnitOfWorkFilter(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(final ServletRequest servletRequest,
                         final ServletResponse servletResponse,
                         final FilterChain filterChain) throws IOException, ServletException {
        this.sessionFactory.getCurrentSession().beginTransaction();
        filterChain.doFilter(servletRequest, servletResponse);
        this.sessionFactory.getCurrentSession().getTransaction().commit();
        this.sessionFactory.getCurrentSession().close();
    }

    @Override
    public void destroy() {

    }
}
