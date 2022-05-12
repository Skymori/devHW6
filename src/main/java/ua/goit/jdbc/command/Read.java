package ua.goit.jdbc.command;

import com.zaxxer.hikari.HikariDataSource;
import ua.goit.jdbc.dao.*;
import ua.goit.jdbc.dto.*;
import ua.goit.jdbc.exceptions.DAOException;
import ua.goit.jdbc.service.Service;
import ua.goit.jdbc.view.View;

import java.util.List;

public class Read extends AbstractCommand implements Command {
    private final View view;
    private final HikariDataSource dataSource;

    public Read(View view, HikariDataSource dataSource) {
        super(view, dataSource);
        this.view = view;
        this.dataSource = dataSource;
    }

    @Override
    public String commandName() {
        return "read";
    }

    @Override
    public void process() {
        boolean running = true;
        while (running) {
            view.write(MenuOutput.READ_SECTION_MENU.getText());
            String section = view.read();
            switch (section) {
                case "1" -> infoByID();
                case "2" -> infoAll();
                case "3" -> getSumSalaryForProject();
                case "4" -> getDevelopersByProject();
                case "5" -> getDevelopersByBranch();
                case "6" -> getDevelopersByLevel();
                case "7" -> getProjectsInfo();
                case "return" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
    }

    private void infoByID() {
        boolean running = true;
        while (running) {
            view.write(MenuOutput.READ_SUB_SECTION_MENU.getText());
            String section = view.read();
            switch (section) {
                case "1" -> getByID(getCustomerService(), "customer");
                case "2" -> getByID(getCompanyService(), "company");
                case "3" -> getByID(getProjectService(), "project");
                case "4" -> getByID(getDeveloperService(), "developer");
                case "5" -> getByID(getSkillService(), "skill");
                case "return" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
    }

    private void infoAll() {
        boolean running = true;
        while (running) {
            view.write(MenuOutput.READ_SUB_SECTION_MENU.getText());
            String section = view.read();
            switch (section) {
                case "1" -> getAllInfo(getCustomerService());
                case "2" -> getAllInfo(getCompanyService());
                case "3" -> getAllInfo(getProjectService());
                case "4" -> getAllInfo(getDeveloperService());
                case "5" -> getAllInfo(getSkillService());
                case "return" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
    }

    private <T> void getAllInfo(Service<T> service) {
        List<T> all = service.readAll();
        all.forEach((entity) -> view.write(entity.toString()));
    }

    private void getSumSalaryForProject() {
        long id = getLongFromConsole("Enter the project id");
        try {
            double sum = getProjectService().findById(id).getDevelopers().stream()
                    .mapToDouble(Developer::getSalary)
                    .sum();
            view.write("Summary of developers salaries for project with id [" + id + "] is " + sum + "\n");
        } catch (DAOException ex) {
            view.write(ex.getMessage());
        }
    }

    private void getDevelopersByProject() {
        long id = getLongFromConsole("Enter the project id");
        try {
            List<Developer> developers = getProjectService().findById(id).getDevelopers();
            view.write("Developers for project with id [" + id + "]");
            developers.forEach((dev) -> view.write(dev.toString()));
        } catch (DAOException ex) {
            view.write(ex.getMessage());
        }
    }

    private void getDevelopersByBranch() {
        Branch branch = getBranchFromConsole();
        List<Developer> byBranch = new DeveloperDAO(dataSource).getByBranch(branch);
        view.write("Developers with language [" + branch.getName() + "]");
        byBranch.forEach((dev) -> view.write(dev.toString()));
    }

    private void getDevelopersByLevel() {
        SkillLevel level = getLevelFromConsole();
        List<Developer> bySkillLevel = new DeveloperDAO(dataSource).getBySkillLevel(level);
        view.write("Developers with level [" + level.getName() + "]");
        bySkillLevel.forEach(System.out::println);
    }

    private void getProjectsInfo() {
        getProjectService().readAll().forEach((project) -> {
            view.write(project.getDate() + " - " + project.getName() + " - " + project.getDevelopers().size() + ";");
        });
    }
}
