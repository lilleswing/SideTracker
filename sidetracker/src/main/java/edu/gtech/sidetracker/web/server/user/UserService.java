package edu.gtech.sidetracker.web.server.user;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import edu.gtech.sidetracker.web.dao.AppUserDao;
import edu.gtech.sidetracker.web.dao.DaoProvider;
import edu.gtech.sidetracker.web.guice.RequestState;
import edu.gtech.sidetracker.web.model.AppUser;
import edu.gtech.sidetracker.web.server.user.model.WsAppUser;

@Path("/user")
@Singleton
public class UserService {

    private final Provider<RequestState> requestStateProvider;
    private final AppUserDao appUserDao;

    @Inject
    public UserService(final Provider<RequestState> requestStateProvider,
                       final DaoProvider daoProvider) {
        this.requestStateProvider = requestStateProvider;
        this.appUserDao = daoProvider.getAppUserDao();
    }

    @GET
    public WsAppUser getAppUser() {
        final RequestState requestState = requestStateProvider.get();
        final AppUser appUser = requestState.getAppUser();
        final WsAppUser wsAppUser = new WsAppUser();
        wsAppUser.setFhirId(appUser.getFhirId());
        return wsAppUser;
    }

    @PUT
    public WsAppUser updateAppUser(final WsAppUser wsAppUser) {
        final RequestState requestState = requestStateProvider.get();
        final AppUser appUser = requestState.getAppUser();
        appUser.setFhirId(wsAppUser.getFhirId());
        appUserDao.update(appUser);
        return wsAppUser;
    }
}
