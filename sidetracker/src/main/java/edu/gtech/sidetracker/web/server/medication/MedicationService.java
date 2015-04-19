package edu.gtech.sidetracker.web.server.medication;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import edu.gtech.sidetracker.web.dao.MedicationDao;
import edu.gtech.sidetracker.web.guice.RequestState;
import edu.gtech.sidetracker.web.model.UserMedication;
import edu.gtech.sidetracker.web.server.medication.model.WsMedication;
import edu.gtech.sidetracker.web.server.medication.model.WsMedicationConverter;

@Path("/medication")
@Singleton
public class MedicationService {

    private final Provider<RequestState> requestStateProvider;
    private final MedicationDao medicationDao;
    private final WsMedicationConverter wsMedicationConverter;

    @Inject
    public MedicationService(final Provider<RequestState> requestStateProvider,
                             final MedicationDao medicationDao,
                             final WsMedicationConverter wsMedicationConverter) {
        this.requestStateProvider = requestStateProvider;
        this.medicationDao = medicationDao;
        this.wsMedicationConverter = wsMedicationConverter;
    }

    @GET
    @Produces(APPLICATION_JSON)
    public List<WsMedication> getMedication() {
        final List<UserMedication> medicationList = medicationDao.getForUser();
        final List<WsMedication> retval = new ArrayList<>(medicationList.size());
        for (final UserMedication userMedication: medicationList) {
            retval.add(wsMedicationConverter.convert(userMedication));
        }
        return retval;
    }
}
