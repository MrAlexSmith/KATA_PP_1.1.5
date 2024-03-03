package jm.task.core.jdbc.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceProvider;

import org.hibernate.HibernateException;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public final class Util {
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static final String DRIVER_P = "jakarta.persistence.jdbc.driver";
    private static final String URL_P = "jakarta.persistence.jdbc.url";
    private static final String USER_P = "jakarta.persistence.jdbc.user";
    private static final String PASSWORD_P = "jakarta.persistence.jdbc.password";
    private static final String SHOW_SQL = "show_sql";
    private static final String FORMAT_SQL = "format_sql";

    private Util() {
    }

    public static Connection getConnection()
            throws SQLException, RuntimeException {

        return DriverManager.getConnection(
                Property.get(URL_KEY),
                Property.get(USERNAME_KEY),
                Property.get(PASSWORD_KEY));
    }

    public static EntityManagerFactory getEntityManagerFactory()
            throws HibernateException {

        Map<String, String> properties = new HashMap<>();

        properties.put(DRIVER_P, Property.get(DRIVER_P));
        properties.put(URL_P, Property.get(URL_P));
        properties.put(USER_P, Property.get(USER_P));
        properties.put(PASSWORD_P, Property.get(PASSWORD_P));
        properties.put(SHOW_SQL, Property.get(SHOW_SQL));
        properties.put(FORMAT_SQL, Property.get(FORMAT_SQL));

        PersistenceProvider provider = new HibernatePersistenceProvider();
        return provider
                .createContainerEntityManagerFactory(
                        PersistenceUnitInfoImpl.getInstance(),
                        properties);
    }
}
