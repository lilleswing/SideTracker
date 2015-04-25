package edu.gtech.sidetracker.web.server.login;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import edu.gtech.sidetracker.web.fhir.PatientUpdater;
import edu.gtech.sidetracker.web.guice.RequestState;
import edu.gtech.sidetracker.web.guice.aop.auth.Authorize;

@Path("/login")
@Singleton
public class LoginService {

    private final Provider<RequestState> requestStateProvider;
    private final PatientUpdater patientUpdater;

    @Inject
    public LoginService(final Provider<RequestState> requestStateProvider,
                        final PatientUpdater patientUpdater) {
        this.requestStateProvider = requestStateProvider;
        this.patientUpdater = patientUpdater;
    }

    @GET
    @Authorize
    public String login() {
        patientUpdater.update(requestStateProvider.get().getAppUser());
        return "Logged In";
    }
}
