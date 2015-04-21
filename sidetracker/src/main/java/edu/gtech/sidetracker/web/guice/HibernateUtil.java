package edu.gtech.sidetracker.web.guice;


import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.gtech.sidetracker.web.model.Alarm;
import edu.gtech.sidetracker.web.model.AppUser;
import edu.gtech.sidetracker.web.model.Comment;
import edu.gtech.sidetracker.web.model.Medication;
import edu.gtech.sidetracker.web.model.SideEffect;
import edu.gtech.sidetracker.web.model.UserMedication;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;

@Singleton
public class HibernateUtil {

    private final SessionFactory sessionFactory;

    @Inject
    public HibernateUtil() {
        destroy();
        sessionFactory= buildSessionFactory();
        seed();
    }



    private static SessionFactory buildSessionFactory() {
        try {
            final Configuration cfg = new Configuration();
            cfg.addAnnotatedClass(Alarm.class);
            cfg.addAnnotatedClass(AppUser.class);
            cfg.addAnnotatedClass(Comment.class);
            cfg.addAnnotatedClass(Medication.class);
            cfg.addAnnotatedClass(SideEffect.class);
            cfg.addAnnotatedClass(UserMedication.class);
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

    private void destroy() {
        try {
            Files.deleteIfExists(Paths.get("/tmp/sidetracker.mv.db"));
            Files.delete(Paths.get("/tmp/sidetracker.mv.db"));
        } catch (Exception ignored) {
        }
    }

    public void seed() {
        try {
            final String query =  getQuery();
            final Session session = sessionFactory.openSession();
            final Transaction transaction = session.beginTransaction();
            session.doWork(new Work() {
                @Override
                public void execute(final Connection connection) throws SQLException {
                    final PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.execute();
                    preparedStatement.close();
                }
            });
            transaction.commit();
            session.close();
        } catch (Exception pass) {
            int i = 1;
        }
    }

    public String getQuery() {
        try {
            final ClassLoader classLoader = getClass().getClassLoader();
            final File sqlFile = new File(classLoader.getResource("create_users.sql").getFile());
            return new Scanner(sqlFile).useDelimiter("\\Z").next();
        } catch (FileNotFoundException ignored) {
            return "";
        }
    }
}
