package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static SessionFactory sessionFactory = Util.getConnection();
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {

        try (Session session = sessionFactory.openSession()) {
            String sqlQuery = "CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT," +
                    " name VARCHAR(100) NOT NULL," +
                    " lastName VARCHAR(100) NOT NULL," +
                    " age TINYINT NOT NULL," +
                    "PRIMARY KEY(ID))";

            session.getTransaction().begin();

            session.createNativeQuery(sqlQuery).executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {

        try (Session session = sessionFactory.openSession()) {
            String sqlQuery = "DROP TABLE IF EXISTS users";

            session.getTransaction().begin();

            session.createNativeQuery(sqlQuery).executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = sessionFactory.openSession()) {
            User user = new User(name, lastName, age);

            session.getTransaction().begin();

            session.persist(user);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            String sqlQuery = String.format("delete from users where id = %s", id);
            session.getTransaction().begin();

            session.createNativeQuery(sqlQuery).executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from users", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {

        try (Session session = sessionFactory.openSession()) {
            String sqlQuery = "DELETE FROM users";
            session.getTransaction().begin();

            session.createNativeQuery(sqlQuery).executeUpdate();

            session.getTransaction().commit();
        }

    }
}
