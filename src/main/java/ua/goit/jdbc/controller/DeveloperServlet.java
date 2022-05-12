package ua.goit.jdbc.controller;

import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.dao.*;
import ua.goit.jdbc.dto.*;
import ua.goit.jdbc.service.Service;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@WebServlet("/developers/*")
public class DeveloperServlet extends AbstractServlet<Developer> {
    private final Service<Project> projectService = new Service<>(new ProjectDAO(DatabaseConnectionManager.getDataSource()));
    private final Service<Skill> skillService = new Service<>(new SkillDAO(DatabaseConnectionManager.getDataSource()));

    @Override
    protected Service<Developer> initService() {
        return new Service<>(new DeveloperDAO(DatabaseConnectionManager.getDataSource()));
    }

    @Override
    protected String getServletPath() {
        return "/developers";
    }

    @Override
    protected String getEntitiesPage() {
        return "/view/developers.jsp";
    }

    @Override
    protected String getEntityPage() {
        return "/view/developer.jsp";
    }

    @Override
    protected String getFormPage() {
        return "/view/developerForm.jsp";
    }

    @Override
    protected Developer readJSPForm(HttpServletRequest req, Developer developer) {

        if (Objects.isNull(developer)) {
            developer = new Developer();
        }

        developer.setFirstName(req.getParameter("firstName"));
        developer.setLastName(req.getParameter("lastName"));
        developer.setSex(Sex.valueOf(req.getParameter("sex")));
        developer.setSalary(Double.parseDouble(req.getParameter("salary")));

        if (developer.getId() != 0) {
            String[] listOfProjectId = req.getParameterValues("projects");
            List<Project> projects = new ArrayList<>();
            if (listOfProjectId != null && listOfProjectId.length > 0) {
                projects = Arrays.stream(listOfProjectId)
                        .mapToInt(Integer::parseInt)
                        .mapToObj(proj -> findById(proj, projectService))
                        .collect(Collectors.toList());
            }
            developer.setProjects(projects);

            List<Skill> skills = new ArrayList<>();
            String[] listOfSkillId = req.getParameterValues("skills");
            if (listOfSkillId != null && listOfSkillId.length > 0) {
                skills = Arrays.stream(listOfSkillId)
                        .mapToInt(Integer::parseInt)
                        .mapToObj(proj -> findById(proj, skillService))
                        .collect(Collectors.toList());
            }
            developer.setSkills(skills);

        }
        return developer;

    }

    @Override
    protected void setAdditionalAttributesInForm(HttpServletRequest req) {
        List<Project> projectList = projectService.readAll();
        req.setAttribute("projectList", projectList);
        List<Skill> skillList = skillService.readAll();
        req.setAttribute("skillList", skillList);
    }
}
