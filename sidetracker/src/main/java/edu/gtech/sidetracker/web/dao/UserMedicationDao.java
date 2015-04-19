package edu.gtech.sidetracker.web.dao;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import edu.gtech.sidetracker.web.guice.RequestState;
import edu.gtech.sidetracker.web.model.AppUser;
import edu.gtech.sidetracker.web.model.UserMedication;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

@Singleton
public class UserMedicationDao implements Dao<UserMedication> {

    private final Provider<RequestState> requestStateProvider;
    private final SessionFactory sessionFactory;


    @Inject
    public UserMedicationDao(final Provider<RequestState> requestStateProvider,
                             final SessionFactory sessionFactory) {
        this.requestStateProvider = requestStateProvider;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<? extends UserMedication> getAll() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public UserMedication getById(final long id) {
        return (UserMedication) sessionFactory.getCurrentSession().get(UserMedication.class, id);
    }

    @Override
    public UserMedication add(final UserMedication userMedication) {
        return null;
    }

    public List<UserMedication> getForUser() {
        final AppUser appUser = requestStateProvider.get().getAppUser();
        final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserMedication.class);
        criteria.createCriteria("appUser").add(Restrictions.eq("id", appUser.getId()));
        return criteria.list();
    }

    public void refresh(final UserMedication userMedication) {
        sessionFactory.getCurrentSession().refresh(userMedication);
    }
}
