package edu.gtech.sidetracker.web.guice;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import edu.gtech.sidetracker.web.model.AppUser;

@RequestScoped
public class RequestState {

    private AppUser appUser;

    @Inject
    public RequestState() {
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(final AppUser appUser) {
        this.appUser = appUser;
    }
}
