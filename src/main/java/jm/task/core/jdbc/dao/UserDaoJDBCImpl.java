package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.exception.DaoException;
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
    private static final UserDaoJDBCImpl INSTANCE = new UserDaoJDBCImpl();
    private static final String CREATE_USERS_TABLE_SQL;
    private static final String DROP_USERS_TABLE_SQL;
    private static final String SAVE_USER_SQL;
    private static final String REMOVE_USER_BY_ID_SQL;
    private static final String GET_ALL_USERS_SQL;
    private static final String CLEAN_USERS_TABLE_SQL;

    static {
        CREATE_USERS_TABLE_SQL = """
                CREATE TABLE IF NOT EXISTS users (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(45) NOT NULL,
                    lastName VARCHAR(45) NOT NULL,
                    age TINYINT NOT NULL
                )
                """;
        DROP_USERS_TABLE_SQL = """
                DROP TABLE IF EXISTS users
                """;
        SAVE_USER_SQL = """
                INSERT INTO users (
                    name, lastName, age
                ) VALUES (?,?,?)
                """;
        REMOVE_USER_BY_ID_SQL = """
                DELETE FROM users
                WHERE id =?
                """;
        GET_ALL_USERS_SQL = """
                SELECT
                    id,
                    name,
                    lastName,
                    age
                FROM users
                """;
        CLEAN_USERS_TABLE_SQL = """
                TRUNCATE TABLE users
                """;
    }

    private UserDaoJDBCImpl() {}

    public static UserDaoJDBCImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_USERS_TABLE_SQL);
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_USERS_TABLE_SQL);
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     SAVE_USER_SQL)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();

        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     REMOVE_USER_BY_ID_SQL)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     GET_ALL_USERS_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                userList.add(user);
            }
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(CLEAN_USERS_TABLE_SQL);
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }
}
