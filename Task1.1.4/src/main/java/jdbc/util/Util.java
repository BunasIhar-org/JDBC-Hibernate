package jdbc.util;

import jdbc.model.*;
import org.hibernate.*;
import org.hibernate.boot.registry.*;
import org.hibernate.cfg.*;

public class Util {
    // реализуйте настройку соеденения с БД

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/user";

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.setProperty("hibernate.connection.url", URL);
                configuration.setProperty("hibernate.connection.username", USER);
                configuration.setProperty("hibernate.connection.password", PASSWORD);
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
                sessionFactory = configuration
                        .buildSessionFactory(builder.build());
            } catch (HibernateException e) {
                System.out.println("Ошибка при создании сессии");
            }
        }
        return sessionFactory;
    }
}