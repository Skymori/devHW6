package ua.goit.jdbc.command;

import com.zaxxer.hikari.HikariDataSource;
import ua.goit.jdbc.dto.*;
import ua.goit.jdbc.exceptions.DAOException;
import ua.goit.jdbc.view.View;


public class Update extends AbstractCommand implements Command {
    private final View view;

    public Update(View view, HikariDataSource dataSource) {
        super(view, dataSource);
        this.view = view;
    }

    @Override
    public String commandName() {
        return "update";
    }

    @Override
    public void process() {
        boolean running = true;
        while (running) {
            view.write(MenuOutput.UPDATE_SECTION_MENU.getText());
            String section = view.read();
            switch (section) {
                case "1" -> updateCustomer();
                case "2" -> updateCompany();
                case "3" -> updateProject();
                case "4" -> updateDeveloper();
                case "5" -> updateSkill();
                case "return" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
    }

    private void updateCustomer() {
        Customer toUpdate = null;
        view.write("Which customer you would like to update?");
        while (toUpdate == null) {
            toUpdate = getByID(getCustomerService(), "customer");
        }
        boolean running = true;
        while (running) {
            view.write(MenuOutput.UPDATE_CUSTOMER_SECTION_MENU.getText());
            String field = view.read();
            switch (field) {
                case "1" -> {
                    view.write("Write customer new name");
                    toUpdate.setName(view.read());
                }
                case "2" -> {
                    view.write("Write customer new industry");
                    toUpdate.setIndustry(view.read());
                }
                case "3" -> {
                    toUpdate.setCompanies(getCompaniesFromConsole());
                    toUpdate.setProjects(getProjectsFromConsole());
                }
                case "ok" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
        try {
            Customer updated = getCustomerService().update(toUpdate);
            view.write("Updated customer\n" + updated + "\n");
        } catch (DAOException ex) {
            view.write(ex.getMessage());
        }
    }

    private void updateCompany() {
        Company toUpdate = null;
        view.write("Which company you would like to update?");
        while (toUpdate == null) {
            toUpdate = getByID(getCompanyService(), "company");
        }
        boolean running = true;
        while (running) {
            view.write(MenuOutput.UPDATE_COMPANY_SECTION_MENU.getText());
            String field = view.read();
            switch (field) {
                case "1" -> {
                    view.write("Write company new name");
                    toUpdate.setName(view.read());
                }
                case "2" -> {
                    view.write("Write company new headquarters");
                    toUpdate.setHeadquarters(view.read());
                }
                case "3" -> {
                    toUpdate.setCustomers(getCustomersFromConsole());
                    toUpdate.setProjects(getProjectsFromConsole());
                }
                case "ok" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
        try {
            Company updated = getCompanyService().update(toUpdate);
            view.write("Updated company\n" + updated + "\n");
        } catch (DAOException ex) {
            view.write(ex.getMessage());
        }
    }

    private void updateProject() {
        Project toUpdate = null;
        view.write("Which project you would like to update?");
        while (toUpdate == null) {
            toUpdate = getByID(getProjectService(), "project");
        }
        boolean running = true;
        while (running) {
            view.write(MenuOutput.UPDATE_PROJECT_SECTION_MENU.getText());
            String field = view.read();
            switch (field) {
                case "1" -> {
                    view.write("Write project new name");
                    toUpdate.setName(view.read());
                }
                case "2" -> {
                    view.write("Write project new description");
                    toUpdate.setDescription(view.read());
                }
                case "3" -> toUpdate.setCost(getDoubleFromConsole("Write project new cost"));
                case "4" -> {
                    toUpdate.setCustomers(getCustomersFromConsole());
                    toUpdate.setCompanies(getCompaniesFromConsole());
                }
                case "5" -> toUpdate.setDevelopers(getDevelopersFromConsole());
                case "ok" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
        try {
            Project project = getProjectService().update(toUpdate);
            view.write("Updated project\n" + project + "\n");
        } catch (DAOException ex) {
            view.write(ex.getMessage());
        }
    }

    private void updateDeveloper() {
        Developer toUpdate = null;
        view.write("Which developer you would like to update?");
        while (toUpdate == null) {
            toUpdate = getByID(getDeveloperService(), "developer");
        }
        boolean running = true;
        while (running) {
            view.write(MenuOutput.UPDATE_DEVELOPER_SECTION_MENU.getText());
            String field = view.read();
            switch (field) {
                case "1" -> {
                    view.write("Write developer new first name");
                    toUpdate.setFirstName(view.read());
                }
                case "2" -> {
                    view.write("Write developer new last name");
                    toUpdate.setLastName(view.read());
                }
                case "3" -> toUpdate.setSex(getGenderFromConsole());
                case "4" -> toUpdate.setSalary(getDoubleFromConsole("Write developer new salary"));
                case "5" -> toUpdate.setProjects(getProjectsFromConsole());
                case "6" -> toUpdate.setSkills(getSkillsFromConsole());
                case "ok" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
        try {
            Developer developer = getDeveloperService().update(toUpdate);
            view.write("Updated developer\n" + developer + "\n");
        } catch (DAOException ex) {
            view.write(ex.getMessage());
        }
    }

    private void updateSkill() {
        Skill toUpdate = null;
        view.write("Which skill you would like to update?");
        while (toUpdate == null) {
            toUpdate = getByID(getSkillService(), "skill");
        }
        boolean running = true;
        while (running) {
            view.write(MenuOutput.UPDATE_SKILL_SECTION_MENU.getText());
            String field = view.read();
            switch (field) {
                case "1" -> toUpdate.setBranch(getBranchFromConsole());
                case "2" -> toUpdate.setLevel(getLevelFromConsole());
                case "3" -> toUpdate.setDevelopers(getDevelopersFromConsole());
                case "ok" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
        try {
            Skill skill = getSkillService().update(toUpdate);
            view.write("Updated skil;\n" + skill + "\n");
        } catch (DAOException ex) {
            view.write(ex.getMessage());
        }
    }

}
