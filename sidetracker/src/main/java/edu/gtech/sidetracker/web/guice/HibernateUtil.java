package edu.gtech.sidetracker.web.guice;



import java.util.HashMap;
import java.util.Map;

import com.google.inject.Singleton;
import edu.gtech.sidetracker.web.model.AppUser;
import edu.gtech.sidetracker.web.model.Comment;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

@Singleton
public class HibernateUtil {

    private final SessionFactory sessionFactory;
    public HibernateUtil() {
        sessionFactory= buildSessionFactory();
    }

    private static SessionFactory buildSessionFactory() {
        try {
            final Configuration cfg = new Configuration();
            cfg.addAnnotatedClass(Comment.class);
            cfg.addAnnotatedClass(AppUser.class);
            cfg.configure();
            final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(cfg.getProperties()).build();
            return cfg.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
