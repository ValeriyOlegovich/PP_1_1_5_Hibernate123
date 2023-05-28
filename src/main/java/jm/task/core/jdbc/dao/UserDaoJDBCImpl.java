package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static Connection connection = null;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        String sqlQuery = "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT NOT NULL AUTO_INCREMENT," +
                " name VARCHAR(100) NOT NULL," +
                " lastName VARCHAR(100) NOT NULL," +
                " age TINYINT NOT NULL," +
                "PRIMARY KEY(ID))";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("таблица users создана");
    }

    public void dropUsersTable() {
        String sqlQuery = "DROP TABLE IF EXISTS users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("таблица users удалена");
    }

    public void saveUser(String name, String lastName, byte age) {
        //String sqlQuery = "INSERT INTO users(name, lastName, age)" + "VALUES('"+name+"','"+lastName+"','"+age+"');";
        String sqlQuery = "INSERT INTO users (name, lastName, age) VALUES(?, ?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //System.out.printf("User с именем – %s добавлен в базу данных\n", name);
    }

    public void removeUserById(long id) {
        String sqlQuery = "DELETE FROM users WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //System.out.printf("user с id = %s удален из таблицы users /n", id);
    }

    public List<User> getAllUsers(){
        ArrayList<User> usersList = new ArrayList<>();
        ResultSet rs = null;
        try (Statement statement = connection.createStatement()) {
            rs = statement.executeQuery( "SELECT * FROM users;");
            while (rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("lastName"), rs.getByte("age"));
                user.setId(rs.getLong("id"));
                usersList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //System.out.printf("Все users возвращены ввиде списка:\n%s\n", usersList);
        return usersList;
    }

    public void cleanUsersTable(){
        String sqlQuery = "DELETE FROM users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("таблица users очищена");
    }
}
