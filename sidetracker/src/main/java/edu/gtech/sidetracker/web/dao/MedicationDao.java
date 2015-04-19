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
import org.hibernate.SessionFactory;
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
        return null;
    }

    @Override
    public Medication getById(final long id) {
        return null;
    }

    @Override
    public Medication add(final Medication medication) {
        return null;
    }

}
