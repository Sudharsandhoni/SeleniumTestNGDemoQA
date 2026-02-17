package core.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import core.exceptions.CustomException;

public final class PropertiesReader {

    private static final Map<String, Properties> CACHE = new ConcurrentHashMap<>();

    private PropertiesReader() {}

    public static String getPropertyValue(String fileName, String propertyName) {

        Properties props = CACHE.computeIfAbsent(fileName, PropertiesReader::loadPropertiesUnchecked);

        String value = props.getProperty(propertyName);
        if (value == null) {
            throw new RuntimeException(
                String.format("Property '%s' not found in file '%s'", propertyName, fileName)
            );
        }
        return value;
    }

    private static Properties loadPropertiesUnchecked(String fileName) {
        try {
            return loadProperties(fileName);
        } catch (CustomException e) {
            throw new IllegalStateException(
                "Failed to load properties file: " + fileName, e
            );
        }
    }

    private static Properties loadProperties(String fileName) throws CustomException {
        try (InputStream is =
                 PropertiesReader.class.getClassLoader().getResourceAsStream(fileName)) {

            if (is == null) {
                throw new CustomException("Properties file not found: " + fileName);
            }

            Properties props = new Properties();
            props.load(is);
            return props;

        } catch (IOException e) {
            throw new CustomException(
                String.format("Failed to load properties file: %s", fileName, e.getMessage()
            ));
        }
    }
}

