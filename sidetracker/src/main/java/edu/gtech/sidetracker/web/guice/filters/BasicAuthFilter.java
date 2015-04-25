package edu.gtech.sidetracker.web.guice.filters;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import edu.gtech.sidetracker.web.dao.AppUserDao;
import edu.gtech.sidetracker.web.guice.RequestState;
import edu.gtech.sidetracker.web.model.AppUser;

@Singleton
public class BasicAuthFilter implements Filter {
    private static final String AUTH_TYPE_BASIC = "Basic";
    private static final String COLON = ":";

    private final Provider<RequestState> requestStateProvider;
    private final AppUserDao appUserDao;

    @Inject
    public BasicAuthFilter(final Provider<RequestState> requestStateProvider,
                           final AppUserDao appUserDao) {
        this.requestStateProvider = requestStateProvider;
        this.appUserDao = appUserDao;
    }


    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest servletRequest,
                         final ServletResponse servletResponse,
                         final FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequestWrapper) {
            getUserNameAndPassword((HttpServletRequestWrapper) servletRequest);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }

    private void getUserNameAndPassword(final HttpServletRequestWrapper servletRequest) {
        final String auth = servletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (auth == null) {
            return;
        }
        try {
            final String encodeUserNameAndPassWord = auth.replaceFirst(AUTH_TYPE_BASIC + " ", "");
            byte[] decodedBytes = DatatypeConverter.parseBase64Binary(encodeUserNameAndPassWord);
            final String decoded = new String(decodedBytes);
            final String[] credentialArray = decoded.split(COLON);
            final AppUser appUser = appUserDao.getByNamePassword(credentialArray[0], credentialArray[1]);
            requestStateProvider.get().setAppUser(appUser);
        } catch (Exception ignored) {
            int i = 1;
        }
    }
}
