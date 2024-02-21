package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class Util {
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";

    // The method created for support of legacy JDK (early JDK 8)
    static {
        loadDriver();
    }

    private Util() {

    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    Property.get(URL_KEY),
                    Property.get(USERNAME_KEY),
                    Property.get(PASSWORD_KEY));
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    // The method created for support of legacy JDK (early JDK 8)
    private static void loadDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }
}
