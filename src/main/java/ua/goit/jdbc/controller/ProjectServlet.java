package ua.goit.jdbc.controller;

import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.dao.*;
import ua.goit.jdbc.dto.*;
import ua.goit.jdbc.service.Service;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@WebServlet("/projects/*")
public class ProjectServlet extends AbstractServlet<Project> {
    private final Service<Customer> customerService = new Service<>(new CustomerDAO(DatabaseConnectionManager.getDataSource()));
    private final Service<Company> companyService = new Service<>(new CompanyDAO(DatabaseConnectionManager.getDataSource()));
    private final Service<Developer> developerService = new Service<>(new DeveloperDAO(DatabaseConnectionManager.getDataSource()));

    @Override
    protected Service<Project> initService() {
        return new Service<>(new ProjectDAO(DatabaseConnectionManager.getDataSource()));
    }

    @Override
    protected String getServletPath() {
        return "/projects";
    }

    @Override
    protected String getEntitiesPage() {
        return "/view/projects.jsp";
    }

    @Override
    protected String getEntityPage() {
        return "/view/project.jsp";
    }

    @Override
    protected String getFormPage() {
        return "/view/projectForm.jsp";
    }

    @Override
    protected Project readJSPForm(HttpServletRequest req, Project project) {

        if (Objects.isNull(project)) {
            project = new Project();
        }

        project.setName(req.getParameter("name"));
        project.setDescription(req.getParameter("description"));
        project.setCost(Double.parseDouble(req.getParameter("cost")));
        project.setDate(LocalDate.parse(req.getParameter("date")));

        if (project.getId() != 0) {
            String[] listOfCustomerId = req.getParameterValues("customers");
            List<Customer> customers = new ArrayList<>();
            if (listOfCustomerId != null && listOfCustomerId.length > 0) {
                customers = Arrays.stream(listOfCustomerId)
                        .mapToInt(Integer::parseInt)
                        .mapToObj(cus -> findById(cus, customerService))
                        .collect(Collectors.toList());
            }
            project.setCustomers(customers);

            String[] listOfCompanyId = req.getParameterValues("companies");
            List<Company> companies = new ArrayList<>();
            if (listOfCompanyId != null && listOfCompanyId.length > 0) {
                companies = Arrays.stream(listOfCompanyId)
                        .mapToInt(Integer::parseInt)
                        .mapToObj(proj -> findById(proj, companyService))
                        .collect(Collectors.toList());
            }
            project.setCompanies(companies);

            String[] listOfDeveloperId = req.getParameterValues("developers");
            List<Developer> developers = new ArrayList<>();
            if (listOfDeveloperId != null && listOfDeveloperId.length > 0) {
                developers = Arrays.stream(listOfDeveloperId)
                        .mapToInt(Integer::parseInt)
                        .mapToObj(proj -> findById(proj, developerService))
                        .collect(Collectors.toList());
            }
            project.setDevelopers(developers);

        }
        return project;

    }

    @Override
    protected void setAdditionalAttributesInForm(HttpServletRequest req) {
        List<Customer> customerList = customerService.readAll();
        List<Company> companyList = companyService.readAll();
        List<Developer> developerList = developerService.readAll();
        req.setAttribute("customerList", customerList);
        req.setAttribute("companyList", companyList);
        req.setAttribute("developerList", developerList);
    }
}
