package edu.gtech.sidetracker.web.server.medication;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import edu.gtech.sidetracker.web.dao.AlarmDao;
import edu.gtech.sidetracker.web.dao.DaoProvider;
import edu.gtech.sidetracker.web.dao.SideEffectDao;
import edu.gtech.sidetracker.web.dao.UserMedicationDao;
import edu.gtech.sidetracker.web.guice.RequestState;
import edu.gtech.sidetracker.web.model.Alarm;
import edu.gtech.sidetracker.web.model.SideEffect;
import edu.gtech.sidetracker.web.model.UserMedication;
import edu.gtech.sidetracker.web.server.medication.model.WsAlarm;
import edu.gtech.sidetracker.web.server.medication.model.WsMedication;
import edu.gtech.sidetracker.web.server.medication.model.WsMedicationConverter;
import edu.gtech.sidetracker.web.server.medication.model.WsSideEffect;

@Path("/medication")
@Singleton
public class MedicationService {

    private final Provider<RequestState> requestStateProvider;
    private final UserMedicationDao userMedicationDao;
    private final AlarmDao alarmDao;
    private final SideEffectDao sideEffectDao;
    private final WsMedicationConverter wsMedicationConverter;

    @Inject
    public MedicationService(final Provider<RequestState> requestStateProvider,
                             final DaoProvider daoProvider,
                             final WsMedicationConverter wsMedicationConverter) {
        this.requestStateProvider = requestStateProvider;
        this.userMedicationDao = daoProvider.getUserMedicationDao();
        this.alarmDao = daoProvider.getAlarmDao();
        this.sideEffectDao = daoProvider.getSideEffectDao();
        this.wsMedicationConverter = wsMedicationConverter;
    }

    @GET
    @Produces(APPLICATION_JSON)
    public List<WsMedication> getMedication() {
        final List<UserMedication> medicationList = userMedicationDao.getForUser();
        final List<WsMedication> retval = new ArrayList<>(medicationList.size());
        for (final UserMedication userMedication: medicationList) {
            retval.add(wsMedicationConverter.convert(userMedication));
        }
        return retval;
    }

    @PUT
    @Produces(APPLICATION_JSON)
    public List<WsMedication> updateMedications(final List<WsMedication> wsMedications) {
        final List<WsMedication> retval = new ArrayList<>(wsMedications.size());
        for (final WsMedication wsMedication: wsMedications) {
            final UserMedication userMedication = userMedicationDao.getById(wsMedication.getId());

            removeDeletedAlarms(userMedication, wsMedication.getAlarms());
            final List<Alarm> alarms = new ArrayList<>();
            for (final WsAlarm wsAlarm: wsMedication.getAlarms()) {
                final Alarm alarm = alarmDao.updateOrCreate(wsAlarm, userMedication);
                alarms.add(alarm);
            }

            removeDeletedSideEffects(userMedication, wsMedication.getSideEffects());
            final List<SideEffect> sideEffects = new ArrayList<>();
            for (final WsSideEffect wsSideEffect: wsMedication.getSideEffects()) {
                final SideEffect sideEffect = sideEffectDao.updateOrCreate(wsSideEffect, userMedication);
                sideEffects.add(sideEffect);
            }

            userMedicationDao.refresh(userMedication);
            retval.add(wsMedicationConverter.convert(userMedication));
        }
        return retval;
    }

    private void removeDeletedSideEffects(final UserMedication userMedication,
                                          final List<WsSideEffect> sideEffects) {
        final Set<Long> toKeep = new HashSet<>();
        for(final WsSideEffect wsSideEffect: sideEffects) {
            toKeep.add(wsSideEffect.getId());
        }
        for(final SideEffect sideEffect: userMedication.getSideEffectSet()) {
            if(!toKeep.contains(sideEffect.getId())) {
                sideEffectDao.delete(sideEffect);
            }
        }
    }

    private void removeDeletedAlarms(final UserMedication userMedication,
                                     final List<WsAlarm> alarms) {
        final Set<Long> toKeep = new HashSet<>();
        for(final WsAlarm wsAlarm: alarms) {
            toKeep.add(wsAlarm.getId());
        }
        for(final Alarm alarm: userMedication.getAlarmSet()) {
            if(!toKeep.contains(alarm.getId())) {
                alarmDao.delete(alarm);
            }
        }
    }
}
