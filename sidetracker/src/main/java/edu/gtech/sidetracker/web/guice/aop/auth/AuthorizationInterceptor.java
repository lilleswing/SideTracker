package edu.gtech.sidetracker.web.guice.aop.auth;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import edu.gtech.sidetracker.web.guice.RequestState;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Singleton
public class AuthorizationInterceptor implements MethodInterceptor {

    private final Provider<RequestState> requestStateProvider;

    @Inject
    public AuthorizationInterceptor(final Provider<RequestState> requestStateProvider) {
        this.requestStateProvider = requestStateProvider;
    }

    @Override
    public Object invoke(final MethodInvocation methodInvocation) throws Throwable {
        final RequestState requestState = requestStateProvider.get();
        if (requestState.getAppUser() == null) {
            throw new AuthorizationException();
        }
        return methodInvocation.proceed();
    }
}
