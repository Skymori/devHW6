package ua.goit.jdbc;

import ua.goit.jdbc.controller.MainController;
import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.view.Console;
import ua.goit.jdbc.view.View;


public class Main {
    public static void main(String[] args) {
        View view = new Console(System.in, System.out);
        MainController controller = new MainController(view, DatabaseConnectionManager.getDataSource());

        controller.run();

    }
}
