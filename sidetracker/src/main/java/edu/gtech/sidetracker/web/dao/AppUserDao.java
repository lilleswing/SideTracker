package edu.gtech.sidetracker.web.dao;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.gtech.sidetracker.web.model.AppUser;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

@Singleton
public class AppUserDao implements Dao<AppUser> {

    private final SessionFactory sessionFactory;

    @Inject
    public AppUserDao(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<? extends AppUser> getAll() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public AppUser getById(final long id) {
        return (AppUser) sessionFactory.getCurrentSession().get(AppUser.class, id);
    }

    @Override
    public AppUser add(final AppUser appUser) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public AppUser getByNamePassword(final String username, final String password) {
        final Session session = sessionFactory.getCurrentSession();
        final Criteria criteria = session.createCriteria(AppUser.class);
        criteria.add(Restrictions.eq("username", username));
        criteria.add(Restrictions.eq("password", password));
        return (AppUser) criteria.uniqueResult();
    }

    public void update(final AppUser appUser) {
        final Session session = sessionFactory.getCurrentSession();
        session.update(appUser);
    }
}
