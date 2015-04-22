package edu.gtech.sidetracker.web.dao;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import edu.gtech.sidetracker.web.guice.RequestState;
import edu.gtech.sidetracker.web.model.AppUser;
import edu.gtech.sidetracker.web.model.Medication;
import edu.gtech.sidetracker.web.model.UserMedication;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

@Singleton
public class MedicationDao implements Dao<Medication> {

    private final Provider<RequestState> requestStateProvider;
    private final SessionFactory sessionFactory;

    @Inject
    public MedicationDao(final Provider<RequestState> requestStateProvider,
                         final SessionFactory sessionFactory) {
        this.requestStateProvider = requestStateProvider;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<? extends Medication> getAll() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public Medication getById(final long id) {
        return (Medication) sessionFactory.getCurrentSession().get(Medication.class, id);
    }

    @Override
    public Medication add(final Medication medication) {
        final Session session = sessionFactory.openSession();
        final Transaction transaction = session.beginTransaction();
        session.save(medication);
        transaction.commit();
        session.close();
        return getById(medication.getId());
    }

    public Medication getByName(final String name) {
        final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Medication.class);
        criteria.add(Restrictions.eq("name", name));
        return (Medication) criteria.uniqueResult();
    }

    public Medication getOrCreate(final String medicationName) {
        final Medication existing = getByName(medicationName);
        if (existing != null) {
            return existing;
        }
        final Medication medication = new Medication();
        medication.setName(medicationName);
        return add(medication);
    }
}
