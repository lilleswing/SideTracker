package edu.gtech.sidetracker.web.server.comment;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.inject.Provider;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.rest.client.IGenericClient;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.gtech.sidetracker.web.guice.RequestState;

@Path("/test")
@Singleton
public class TestService {
    private final Provider<RequestState> requestStateProvider;

    @Inject
    public TestService(final Provider<RequestState> requestStateProvider) {
        this.requestStateProvider = requestStateProvider;
    }

    @GET
    @Produces(APPLICATION_JSON)
    public String get() {
        final String url = "http://fhirtest.uhn.ca/baseDstu1";
        FhirContext ctx = new FhirContext();
        IGenericClient client = ctx.newRestfulGenericClient(url);
        IdDt myID = new IdDt("Patient", "3.568001602-01");
        //Bundle bundle = client.search().forResource(MedicationPrescription.class)
        //        .where(MedicationPrescription.PATIENT.hasId(myID)).execute();
        return "foo";
    }
}
