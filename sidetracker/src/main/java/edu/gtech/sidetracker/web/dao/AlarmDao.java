package edu.gtech.sidetracker.web.dao;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import edu.gtech.sidetracker.web.guice.RequestState;
import edu.gtech.sidetracker.web.model.Alarm;
import edu.gtech.sidetracker.web.model.UserMedication;
import edu.gtech.sidetracker.web.server.medication.model.WsAlarm;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Singleton
public class AlarmDao implements Dao<Alarm> {

    private final Provider<RequestState> requestStateProvider;
    private final SessionFactory sessionFactory;

    @Inject
    public AlarmDao(final Provider<RequestState> requestStateProvider,
                    final SessionFactory sessionFactory) {
        this.requestStateProvider = requestStateProvider;
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<? extends Alarm> getAll() {
        return null;
    }

    @Override
    public Alarm getById(final long id) {
        return (Alarm) sessionFactory.getCurrentSession().get(Alarm.class, id);
    }

    @Override
    public Alarm add(final Alarm alarm) {
        final Session session = sessionFactory.openSession();
        final Transaction transaction = session.beginTransaction();
        session.save(alarm);
        transaction.commit();
        session.close();
        return getById(alarm.getId());
    }

    public Alarm updateOrCreate(final WsAlarm wsAlarm,
                                final UserMedication userMedication) {
        if (wsAlarm.getId() == 0) { // create
            final Alarm alarm = new Alarm();
            alarm.setUserMedication(userMedication);
            alarm.setDay(wsAlarm.getDay());
            alarm.setTime(wsAlarm.getTime());
            return add(alarm);
        }
        final Alarm existing = getById(wsAlarm.getId());
        existing.setTime(wsAlarm.getTime());
        existing.setDay(wsAlarm.getDay());
        sessionFactory.getCurrentSession().update(existing);
        return existing;
    }

    public void delete(final Alarm alarm) {
        final Session session = sessionFactory.openSession();
        final Transaction transaction = session.beginTransaction();
        session.delete(alarm);
        transaction.commit();
        session.clear();
    }
}
