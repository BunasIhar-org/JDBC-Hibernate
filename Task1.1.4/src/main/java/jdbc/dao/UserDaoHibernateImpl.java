package jdbc.dao;

import jdbc.model.*;
import jdbc.util.*;
import org.hibernate.*;
import java.util.*;

public class UserDaoHibernateImpl implements UserDao {

    private final static SessionFactory SESSION_FACTORY = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, name varchar(255), lastName varchar(255), age SMALLINT)";
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        long id;
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            id = (long) session.save(new User(name, lastName, age));
            transaction.commit();
        }
        System.out.println("User с именем – " + name + " добавлен в базу данных c ID: " + id);
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = SESSION_FACTORY.openSession()) {
            return session.createQuery("FROM User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE users";
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        }
    }
}