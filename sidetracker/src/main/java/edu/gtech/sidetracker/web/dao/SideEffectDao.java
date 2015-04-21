package edu.gtech.sidetracker.web.dao;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import edu.gtech.sidetracker.web.guice.RequestState;
import edu.gtech.sidetracker.web.model.SideEffect;
import edu.gtech.sidetracker.web.model.UserMedication;
import edu.gtech.sidetracker.web.server.medication.model.WsSideEffect;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Singleton
public class SideEffectDao implements Dao<SideEffect>{

    private final Provider<RequestState> requestStateProvider;
    private final SessionFactory sessionFactory;

    @Inject
    public SideEffectDao(final Provider<RequestState> requestStateProvider,
                         final SessionFactory sessionFactory) {
        this.requestStateProvider = requestStateProvider;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<? extends SideEffect> getAll() {
        return null;
    }

    @Override
    public SideEffect getById(final long id) {
        return (SideEffect) sessionFactory.getCurrentSession().get(SideEffect.class, id);
    }

    @Override
    public SideEffect add(final SideEffect sideEffect) {
        final Session session = sessionFactory.openSession();
        final Transaction transaction = session.beginTransaction();
        session.save(sideEffect);
        transaction.commit();
        session.close();
        return getById(sideEffect.getId());
    }

    public SideEffect updateOrCreate(final WsSideEffect wsSideEffect,
                                     final UserMedication userMedication) {
        if (wsSideEffect.getId() == 0) { // create
            final SideEffect sideEffect = new SideEffect();
            sideEffect.setDescription(wsSideEffect.getDescription());
            sideEffect.setUserMedication(userMedication);
            return add(sideEffect);
        }
        final SideEffect existing = getById(wsSideEffect.getId());
        existing.setDescription(wsSideEffect.getDescription());
        sessionFactory.getCurrentSession().update(existing);
        return existing;
    }

    public void delete(final SideEffect sideEffect) {
        final Session session = sessionFactory.openSession();
        final Transaction transaction = session.beginTransaction();
        session.delete(sideEffect);
        transaction.commit();
        session.clear();
    }
}
