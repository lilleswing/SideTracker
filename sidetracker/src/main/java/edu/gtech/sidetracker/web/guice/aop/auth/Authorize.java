package edu.gtech.sidetracker.web.guice.aop.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {
    // NOTE(LESWING) can do seomthing like ...
    // Role requiredRole() default Role.USER
}
