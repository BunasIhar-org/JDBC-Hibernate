package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.*;
import jm.task.core.jdbc.util.*;
import java.sql.*;
import java.util.*;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, name varchar(255), lastName varchar(255), age SMALLINT)";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы");
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении таблицы.");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            long id = 0;
            while (rs.next()) {
                id = rs.getLong("id");
            }

            System.out.println("User с именем – " + name + " добавлен в базу данных с ID: " + id);

        } catch (SQLException e) {
            System.out.println("Ошибка при сохранении пользователя в таблицу.");
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Ошибка при удалении пользователя из таблицы.");
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        String sql = "Select * from users";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");

                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setLastName(lastName);
                user.setAge(age);
                result.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении всех пользователей из таблицы.");
        }
        return result;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE users";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении всех записей таблицы.");
        }
    }
}
