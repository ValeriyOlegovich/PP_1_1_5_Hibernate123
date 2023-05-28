package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        UserDaoHibernateImpl dao = new UserDaoHibernateImpl();
// удаление таблицы
        dao.dropUsersTable();
// создание таблицы
        dao.createUsersTable();
// добавление users
        dao.saveUser("Harry", "Poter", (byte) 21);
        dao.saveUser("Larry", "Loter", (byte) 12);
        dao.saveUser("Jarry", "Soter", (byte) 127);
        dao.saveUser("Barry", "Koter", (byte) 55);
// таблица users в список
        dao.getAllUsers();
        //System.out.println("\nТаблица до очищения users:");
        //System.out.println(dao.getAllUsers());
// удаление user по id
        dao.removeUserById(1L);
        //System.out.println("\nТаблица после удаления первого users:");
        //System.out.println(dao.getAllUsers());
// очищение таблицы
        dao.cleanUsersTable();
        //System.out.println("\nТаблица после очищения users:");
        //System.out.println(dao.getAllUsers());
        Util.getConnection().close();
    }
}
