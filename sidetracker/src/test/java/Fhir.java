import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.Bundle;
import ca.uhn.fhir.model.dstu.resource.MedicationPrescription;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.gclient.StringClientParam;
import org.junit.Test;

public class Fhir {

    @Test
    public void test() {
        // Create a client (only needed once)
        FhirContext ctx = new FhirContext();
        IGenericClient client = ctx.newRestfulGenericClient("https://taurus.i3l.gatech.edu:8443/HealthPort/fhir/");

        try {
            Bundle bundle = client.search().forResource(MedicationPrescription.class)
                    .where(new StringClientParam("_id").matches().value("3.568001602-01"))
                    .execute();
        } catch (Exception e) {
            int i = 1;
        }
    }
}
