package ua.goit.jdbc.controller;

import com.zaxxer.hikari.HikariDataSource;
import ua.goit.jdbc.command.*;
import ua.goit.jdbc.view.View;

import java.util.*;

public class MainController {
    private final View view;
    private final List<Command> commands;

    public MainController(View view, HikariDataSource dataSource) {
        this.view = view;
        this.commands = new ArrayList<>(Arrays.asList(new Help(view),
                new Create(view, dataSource),
                new Read(view, dataSource),
                new Update(view, dataSource),
                new Delete(view, dataSource)));
    }

    public void run() {
        view.write("Welcome to the Project management system!");
        doCommand();
    }

    private void doCommand() {
        boolean running = true;
        while (running) {
            view.write("Please enter a command or 'help' to retrieve command list\nEnter 'exit' to leave");
            String inputCommand = view.read();
            for (Command command : commands) {
                if (command.canProcess(inputCommand)) {
                    command.process();
                    break;
                } else if (inputCommand.equalsIgnoreCase("exit")) {
                    view.write("Good Bye!");
                    running = false;
                    break;
                }
            }
        }
    }
}
