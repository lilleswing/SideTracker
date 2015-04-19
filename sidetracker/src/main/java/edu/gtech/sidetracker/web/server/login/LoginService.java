package edu.gtech.sidetracker.web.server.login;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Path("/login")
@Singleton
public class LoginService {

    @Inject
    public LoginService() {
    }

    @GET
    public String login() {
        return "Logged In";
    }
}
