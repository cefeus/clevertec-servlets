package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for reading properties from a YAML file.
 */
public class ReadProperties {

    private static final Properties PROP = new Properties();

    static {
        loadProperties();
    }

    /**
     * Loads properties from the application.yaml file.
     * Handles the IOException if the file loading fails.
     */
    private static void loadProperties() {
        try (InputStream input = ReadProperties.class.
                getClassLoader().
                getResourceAsStream("application.yaml")) {
            PROP.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a property value based on the provided key.
     *
     * @param key The key for which the property value is retrieved.
     * @return The property value associated with the provided key.
     */
    public static String getPropertyByKey(String key) {
        return PROP.getProperty(key);
    }

}
