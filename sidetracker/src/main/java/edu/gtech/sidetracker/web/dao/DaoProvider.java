package edu.gtech.sidetracker.web.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DaoProvider {

    private final AlarmDao alarmDao;
    private final AppUserDao appUserDao;
    private final CommentDao commentDao;
    private final MedicationDao medicationDao;
    private final UserMedicationDao userMedicationDao;
    private final SideEffectDao sideEffectDao;

    @Inject
    public DaoProvider(final AlarmDao alarmDao,
                       final AppUserDao appUserDao,
                       final CommentDao commentDao,
                       final MedicationDao medicationDao,
                       final SideEffectDao sideEffectDao,
                       final UserMedicationDao userMedicationDao) {
        this.alarmDao = alarmDao;
        this.appUserDao = appUserDao;
        this.commentDao = commentDao;
        this.medicationDao = medicationDao;
        this.sideEffectDao = sideEffectDao;
        this.userMedicationDao = userMedicationDao;
    }

    public AlarmDao getAlarmDao() {
        return alarmDao;
    }

    public AppUserDao getAppUserDao() {
        return appUserDao;
    }

    public CommentDao getCommentDao() {
        return commentDao;
    }

    public MedicationDao getMedicationDao() {
        return medicationDao;
    }

    public UserMedicationDao getUserMedicationDao() {
        return userMedicationDao;
    }

    public SideEffectDao getSideEffectDao() {
        return sideEffectDao;
    }
}
