package ua.goit.jdbc.controller;

import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.dao.*;
import ua.goit.jdbc.dto.*;
import ua.goit.jdbc.service.Service;;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@WebServlet("/customers/*")
public class CustomerServlet extends AbstractServlet<Customer> {
    private final Service<Company> companyService = new Service<>(new CompanyDAO(DatabaseConnectionManager.getDataSource()));
    private final Service<Project> projectService = new Service<>(new ProjectDAO(DatabaseConnectionManager.getDataSource()));

    @Override
    protected Service<Customer> initService() {
        return new Service<>(new CustomerDAO(DatabaseConnectionManager.getDataSource()));
    }

    @Override
    protected String getServletPath() {
        return "/customers";
    }

    @Override
    protected String getEntitiesPage() {
        return "/view/customers.jsp";
    }

    @Override
    protected String getEntityPage() {
        return "/view/customer.jsp";
    }

    @Override
    protected String getFormPage() {
        return "/view/customerForm.jsp";
    }

    @Override
    protected Customer readJSPForm(HttpServletRequest req, Customer customer) {

        if (Objects.isNull(customer)) {
            customer = new Customer();
        }

        customer.setName(req.getParameter("name"));
        customer.setIndustry(req.getParameter("industry"));

        if (customer.getId() != 0) {
            String[] listOfCustomerId = req.getParameterValues("companies");
            List<Company> companies = new ArrayList<>();
            if (listOfCustomerId != null && listOfCustomerId.length > 0) {
                companies = Arrays.stream(listOfCustomerId)
                        .mapToInt(Integer::parseInt)
                        .mapToObj(cus -> findById(cus, companyService))
                        .collect(Collectors.toList());
            }
            customer.setCompanies(companies);

            String[] listOfProjectId = req.getParameterValues("projects");
            List<Project> projects = new ArrayList<>();
            if (listOfProjectId != null && listOfProjectId.length > 0) {
                projects = Arrays.stream(listOfProjectId)
                        .mapToInt(Integer::parseInt)
                        .mapToObj(proj -> findById(proj, projectService))
                        .collect(Collectors.toList());
            }
            customer.setProjects(projects);

        }
        return customer;

    }

    @Override
    protected void setAdditionalAttributesInForm(HttpServletRequest req) {
        List<Company> companyList = companyService.readAll();
        List<Project> projectList = projectService.readAll();
        req.setAttribute("companyList", companyList);
        req.setAttribute("projectList", projectList);
    }
}
