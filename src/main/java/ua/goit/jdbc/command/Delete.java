package ua.goit.jdbc.command;

import com.zaxxer.hikari.HikariDataSource;
import ua.goit.jdbc.exceptions.DAOException;
import ua.goit.jdbc.service.Service;
import ua.goit.jdbc.view.View;

public class Delete extends AbstractCommand implements Command {
    private final View view;

    public Delete(View view, HikariDataSource dataSource) {
        super(view, dataSource);
        this.view = view;
    }

    @Override
    public String commandName() {
        return "delete";
    }

    @Override
    public void process() {
        boolean running = true;
        while (running) {
            view.write(MenuOutput.DELETE_MENU.getText());
            String section = view.read();
            switch (section) {
                case "1" -> delete(getCustomerService());
                case "2" -> delete(getCompanyService());
                case "3" -> delete(getProjectService());
                case "4" -> delete(getDeveloperService());
                case "5" -> delete(getSkillService());
                case "return" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
    }

    private <T> void delete(Service<T> service) {
        long id = getLongFromConsole("Enter id");
        try {
            service.delete(id);
            view.write("Deleted successfully\n");
        } catch (DAOException ex) {
            view.write(ex.getMessage());
        }
    }
}
