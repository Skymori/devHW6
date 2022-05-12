package ua.goit.jdbc.service.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    private final Properties properties = new Properties();

    public String getProperty(String name) {
        return properties.getProperty(name);
    }

    public void loadPropertiesFile(String fileName) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName)) {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
