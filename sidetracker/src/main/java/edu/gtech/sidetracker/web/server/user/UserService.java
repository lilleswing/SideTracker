package edu.gtech.sidetracker.web.server.user;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import edu.gtech.sidetracker.web.dao.AppUserDao;
import edu.gtech.sidetracker.web.dao.DaoProvider;
import edu.gtech.sidetracker.web.fhir.PatientUpdater;
import edu.gtech.sidetracker.web.guice.RequestState;
import edu.gtech.sidetracker.web.guice.aop.auth.Authorize;
import edu.gtech.sidetracker.web.model.AppUser;
import edu.gtech.sidetracker.web.server.user.model.WsAppUser;

@Path("/user")
@Singleton
public class UserService {

    private final Provider<RequestState> requestStateProvider;
    private final PatientUpdater patientUpdater;
    private final AppUserDao appUserDao;

    @Inject
    public UserService(final Provider<RequestState> requestStateProvider,
                       final PatientUpdater patientUpdater,
                       final DaoProvider daoProvider) {
        this.requestStateProvider = requestStateProvider;
        this.patientUpdater = patientUpdater;
        this.appUserDao = daoProvider.getAppUserDao();
    }

    @GET
    @Authorize
    public WsAppUser getAppUser() {
        final RequestState requestState = requestStateProvider.get();
        final AppUser appUser = requestState.getAppUser();
        final WsAppUser wsAppUser = new WsAppUser();
        wsAppUser.setUserName(appUser.getUsername());
        wsAppUser.setId(appUser.getId());
        wsAppUser.setFhirId(appUser.getFhirId());
        return wsAppUser;
    }

    @PUT
    @Authorize
    public WsAppUser updateAppUser(final WsAppUser wsAppUser) {
        final RequestState requestState = requestStateProvider.get();
        final AppUser appUser = requestState.getAppUser();
        appUser.setFhirId(wsAppUser.getFhirId());
        appUserDao.update(appUser);
        patientUpdater.update(appUser);
        return wsAppUser;
    }

    @POST
    public WsAppUser createAppUser(final WsAppUser wsAppUser) {
        final AppUser appUser = new AppUser();
        appUser.setPassword(wsAppUser.getPassword());
        appUser.setFhirId(wsAppUser.getFhirId());
        appUser.setUsername(wsAppUser.getUserName());
        final AppUser returned = appUserDao.add(appUser);
        final WsAppUser returnWs = new WsAppUser();
        returnWs.setId(returned.getId());
        returnWs.setUserName(returned.getUsername());
        returnWs.setFhirId(returned.getFhirId());
        return returnWs;
    }
}
