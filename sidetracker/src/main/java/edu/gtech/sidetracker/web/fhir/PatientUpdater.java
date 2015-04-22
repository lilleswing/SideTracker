package edu.gtech.sidetracker.web.fhir;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import edu.gtech.sidetracker.web.dao.DaoProvider;
import edu.gtech.sidetracker.web.dao.MedicationDao;
import edu.gtech.sidetracker.web.dao.UserMedicationDao;
import edu.gtech.sidetracker.web.guice.RequestState;
import edu.gtech.sidetracker.web.model.AppUser;
import edu.gtech.sidetracker.web.model.Medication;
import edu.gtech.sidetracker.web.model.UserMedication;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Singleton
public class PatientUpdater {

    private final FhirClient fhirClient;
    private final MedicationParser medicationParser;
    private final MedicationDao medicationDao;
    private final UserMedicationDao userMedicationDao;

    @Inject
    public PatientUpdater(final FhirClient fhirClient,
                          final MedicationParser medicationParser,
                          final DaoProvider daoProvider) {
        this.fhirClient = fhirClient;
        this.medicationParser = medicationParser;
        this.medicationDao = daoProvider.getMedicationDao();
        this.userMedicationDao = daoProvider.getUserMedicationDao();
    }

    public void update(final AppUser appUser) {
        final String data = fhirClient.getPatient(appUser);
        if (Strings.isNullOrEmpty(data)) {
            return;
        }
        final Set<String> medicationNames = medicationParser.parse(data);
        final Set<Medication> fhirMedications = new HashSet<>();
        for (final String medicationName: medicationNames) {
            final Medication medication = medicationDao.getOrCreate(medicationName);
            fhirMedications.add(medication);
        }

        final List<UserMedication> forUser = userMedicationDao.getForUser();
        final Set<Medication> existing = new HashSet<>();
        for(final UserMedication userMedication: forUser) {
            existing.add(userMedication.getMedication());
        }
        for(final Medication medication: fhirMedications) {
            if (existing.contains(medication)) {
                continue;
            }
            final UserMedication userMedication = new UserMedication();
            userMedication.setAppUser(appUser);
            userMedication.setMedication(medication);
            userMedicationDao.add(userMedication);
        }
    }
}
