package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;

    public UserDaoJDBCImpl() {
        try {
            this.connection = Util.getConnection();
        } catch (SQLException | RuntimeException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void createUsersTable() {
        String query = """
                CREATE TABLE IF NOT EXISTS users (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(45) NOT NULL,
                    lastName VARCHAR(45) NOT NULL,
                    age TINYINT NOT NULL
                )
                """;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String query = """
                DROP TABLE IF EXISTS users
                """;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String query = """
                INSERT INTO users (
                    name, lastName, age
                ) VALUES (?, ?, ?)
                """;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        String query = """
                DELETE FROM users WHERE id = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String query = """
                SELECT * FROM users
                """;
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                userList.add(user);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        String query = """
                TRUNCATE TABLE users
                """;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
