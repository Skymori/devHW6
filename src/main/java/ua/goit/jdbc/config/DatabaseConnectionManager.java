package ua.goit.jdbc.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ua.goit.jdbc.service.util.PropertiesLoader;

import java.util.Objects;

public class DatabaseConnectionManager {
    private static HikariDataSource ds;

    public DatabaseConnectionManager(PropertiesLoader propertiesLoader) {
        initDataSource(propertiesLoader);
    }

    public static void init() {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        propertiesLoader.loadPropertiesFile("application.properties");
        initDataSource(propertiesLoader);
    }

    public static synchronized HikariDataSource getDataSource() {
        if (Objects.isNull(ds)) {
            init();
            return ds;
        }
        return ds;
    }

    private static void initDataSource(PropertiesLoader propertiesLoader) {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(String.format("jdbc:postgresql://%s/%s", propertiesLoader.getProperty("host"),
                    propertiesLoader.getProperty("database.name")));
            config.setUsername(propertiesLoader.getProperty("username"));
            config.setPassword(propertiesLoader.getProperty("password"));
            config.setMaximumPoolSize(10);
            config.setIdleTimeout(10_000);
            config.setConnectionTimeout(10_000);
            config.setDriverClassName(propertiesLoader.getProperty("jdbc.driver"));
            ds = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
