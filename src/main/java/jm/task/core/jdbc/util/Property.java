package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public final class Property {
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private Property() {
    }

    public static String get(String stringKey) {
        return PROPERTIES.getProperty(stringKey);
    }

    private static void loadProperties() {
        try (InputStream inputStream = Files.newInputStream(
                Paths.get("application.properties"))) {
            PROPERTIES.load(inputStream);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
