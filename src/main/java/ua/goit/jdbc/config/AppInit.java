package ua.goit.jdbc.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppInit  implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Init database connection manager");
        DatabaseConnectionManager.init();
        System.out.println("Database init finished");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
