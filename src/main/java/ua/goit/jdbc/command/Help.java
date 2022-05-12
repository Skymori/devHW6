package ua.goit.jdbc.command;

import ua.goit.jdbc.view.View;

public class Help implements Command {
    private final View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public String commandName() {
        return "help";
    }

    @Override
    public void process() {
        view.write(MenuOutput.HELP_MENU.getText());
    }

}
