package ua.goit.jdbc.command;

public interface Command {

    String commandName();

    void process();

    default boolean canProcess(String command) {
        return commandName().equals(command);
    }
}
