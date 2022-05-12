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

@WebServlet("/skills/*")
public class SkillServlet extends AbstractServlet<Skill> {
    private final Service<Developer> developerService = new Service<>(new DeveloperDAO(DatabaseConnectionManager.getDataSource()));

    @Override
    protected Service<Skill> initService() {
        return new Service<>(new SkillDAO(DatabaseConnectionManager.getDataSource()));
    }

    @Override
    protected String getServletPath() {
        return "/skills";
    }

    @Override
    protected String getEntitiesPage() {
        return "/view/skills.jsp";
    }

    @Override
    protected String getEntityPage() {
        return "/view/skill.jsp";
    }

    @Override
    protected String getFormPage() {
        return "/view/skillForm.jsp";
    }

    @Override
    protected Skill readJSPForm(HttpServletRequest req, Skill skill) {

        if (Objects.isNull(skill)) {
            skill = new Skill();
        }

        skill.setBranch(Branch.valueOf(req.getParameter("branch")));
        skill.setLevel(SkillLevel.valueOf(req.getParameter("level")));

        if (skill.getId() != 0) {
            String[] listOfDeveloperId = req.getParameterValues("developers");
            List<Developer> developers = new ArrayList<>();
            if (listOfDeveloperId != null && listOfDeveloperId.length > 0) {
                developers = Arrays.stream(listOfDeveloperId)
                        .mapToInt(Integer::parseInt)
                        .mapToObj(proj -> findById(proj, developerService))
                        .collect(Collectors.toList());
            }
            skill.setDevelopers(developers);

        }
        return skill;

    }

    @Override
    protected void setAdditionalAttributesInForm(HttpServletRequest req) {
        List<Developer> developerList = developerService.readAll();
        req.setAttribute("developerList", developerList);
    }
}

