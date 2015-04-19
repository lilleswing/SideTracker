package edu.gtech.sidetracker.web.server.medication.model;

import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.gtech.sidetracker.web.model.Alarm;
import edu.gtech.sidetracker.web.model.SideEffect;
import edu.gtech.sidetracker.web.model.UserMedication;

@Singleton
public class WsMedicationConverter {

    @Inject
    public WsMedicationConverter() {
    }

    public WsMedication convert(final UserMedication userMedication) {
        final WsMedication wsMedication = new WsMedication();
        wsMedication.setId(userMedication.getId());
        wsMedication.setName(userMedication.getMedication().getName());
        for (final Alarm alarm: userMedication.getAlarmSet()) {
            final WsAlarm wsAlarm = new WsAlarm();
            wsAlarm.setTime(alarm.getTime());
            wsAlarm.setDay(alarm.getDay());
            wsAlarm.setId(alarm.getId());
            wsMedication.getAlarms().add(wsAlarm);
        }
        for (final SideEffect sideEffect: userMedication.getSideEffectSet()) {
            final WsSideEffect wsSideEffect = new WsSideEffect();
            wsSideEffect.setId(sideEffect.getId());
            wsSideEffect.setDescription(sideEffect.getDescription());
            wsMedication.getSideEffects().add(wsSideEffect);
        }
        return wsMedication;
    }
}
