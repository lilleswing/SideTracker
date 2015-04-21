package edu.gtech.sidetracker.web.guice;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    public void seed() {
        try {
            final Session session = sessionFactory.openSession();
            final Transaction transaction = session.beginTransaction();
            session.doWork(new Work() {
                @Override
                public void execute(final Connection connection) throws SQLException {
                    final PreparedStatement preparedStatement = connection.prepareStatement(
                            "CREATE SEQUENCE hibernate_sequence START 1;\n" +
                                    "CREATE TABLE app_user (\n" +
                                    "  id SERIAL PRIMARY KEY,\n" +
                                    "  username TEXT,\n" +
                                    "  password TEXT,\n" +
                                    "  fhir_id TEXT\n" +
                                    ");\n" +
                                    "\n" +
                                    "CREATE TABLE comment (\n" +
                                    "  id SERIAL PRIMARY KEY ,\n" +
                                    "  comment TEXT,\n" +
                                    "  app_user_id BIGINT\n" +
                                    ");\n" +
                                    "\n" +
                                    "ALTER TABLE comment ADD CONSTRAINT comment_app_user_fk FOREIGN KEY (app_user_id) REFERENCES app_user(id);\n" +
                                    "\n" +
                                    "CREATE TABLE medication (\n" +
                                    "  id SERIAL PRIMARY KEY,\n" +
                                    "  name TEXT\n" +
                                    ");\n" +
                                    "\n" +
                                    "CREATE TABLE user_medication (\n" +
                                    "  id SERIAL PRIMARY KEY,\n" +
                                    "  app_user_id BIGINT,\n" +
                                    "  medication_id BIGINT\n" +
                                    ");\n" +
                                    "\n" +
                                    "ALTER TABLE user_medication ADD CONSTRAINT user_medication_user_fk FOREIGN KEY (app_user_id) REFERENCES app_user(id);\n" +
                                    "ALTER TABLE user_medication ADD CONSTRAINT user_medication_medication_fk\n" +
                                    "FOREIGN KEY (app_user_id) REFERENCES medication(id);\n" +
                                    "\n" +
                                    "CREATE TABLE alarm (\n" +
                                    "  id SERIAL PRIMARY KEY,\n" +
                                    "  user_medication_id BIGINT,\n" +
                                    "  time TEXT,\n" +
                                    "  day_of_week TEXT\n" +
                                    ");\n" +
                                    "\n" +
                                    "ALTER TABLE alarm ADD CONSTRAINT  alarm_user_medication_fk\n" +
                                    "FOREIGN KEY (user_medication_id) REFERENCES user_medication (id);\n" +
                                    "\n" +
                                    "CREATE TABLE side_effect (\n" +
                                    "  id SERIAL PRIMARY KEY,\n" +
                                    "  user_medication_id BIGINT,\n" +
                                    "  description TEXT\n" +
                                    ");\n" +
                                    "\n" +
                                    "ALTER TABLE side_effect ADD CONSTRAINT  side_effect_user_medication_fk\n" +
                                    "FOREIGN KEY (user_medication_id) REFERENCES user_medication (id);\n" +
                                    "\n" +
                                    "INSERT INTO app_user (username, password) VALUES ('martha', 'password');\n" +
                                    "INSERT INTO medication (name) VALUES ('viagra');\n" +
                                    "INSERT INTO medication (name) VALUES ('prozac');\n" +
                                    "\n" +
                                    "INSERT INTO user_medication (app_user_id, medication_id) (\n" +
                                    "    SELECT app_user.id, medication.id FROM app_user, medication\n" +
                                    ");"
                    );
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
}
