package ua.goit.jdbc.dao;

import com.zaxxer.hikari.HikariDataSource;
import ua.goit.jdbc.dto.*;
import ua.goit.jdbc.exceptions.DAOException;

import java.sql.*;
import java.util.List;

public class DeveloperDAO extends AbstractDAO<Developer> {

    public DeveloperDAO(HikariDataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO developers (developer_id, first_name, last_name, sex, salary) " +
                "VALUES (?, ?, ?, CAST(? AS sex), ?);";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE developers SET first_name=?, last_name=?, sex=CAST(? AS sex), salary=? " +
                "WHERE developer_id=?;";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT developer_id, first_name, last_name, sex, salary " +
                "FROM developers WHERE developer_id = ?;";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM developers ORDER BY developer_id";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM developers WHERE developer_id=?;";
    }

    @Override
    protected String getLastIdQuery() {
        return "SELECT max(developer_id) FROM developers;";
    }

    @Override
    protected void sendEntity(PreparedStatement statement, Developer developer) throws DAOException {
        try {
            if (developer.getId() == 0) {
                //CREATE
                developer.setId(getLastId() + 1);
                statement.setLong(1, developer.getId());
                statement.setString(2, developer.getFirstName());
                statement.setString(3, developer.getLastName());
                statement.setString(4, developer.getSex().getName());
                statement.setDouble(5, developer.getSalary());
            } else {
                //UPDATE
                statement.setString(1, developer.getFirstName());
                statement.setString(2, developer.getLastName());
                statement.setString(3, developer.getSex().getName());
                statement.setDouble(4, developer.getSalary());
                statement.setLong(5, developer.getId());
                if (developer.getProjects() != null) {
                    sendProjects(developer);
                }
                if (developer.getSkills() != null) {
                    sendSkills(developer);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    private void sendProjects(Developer developer) throws DAOException {
        String query = "INSERT INTO project_developers(developer_id, project_id) " +
                "VALUES (?, ?);";
        List<Project> projectsInDB = receiveProjects(developer);
        List<Project> newProjects = developer.getProjects();
        if (areNotEquals(projectsInDB, newProjects)) {
            try (Connection connection = getConnectionManager().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                for (Project project : newProjects) {
                    statement.setLong(1, developer.getId());
                    statement.setLong(2, project.getId());
                    statement.execute();
                }
            } catch (SQLException ex) {
                throw new DAOException(ex.getMessage());
            }
        }
    }

    private List<Project> receiveProjects(Developer developer) throws DAOException {
        String query = String.format("SELECT p.project_id, p.project_name, p.project_description, p.cost, p.create_date " +
                "FROM projects p INNER JOIN project_developers pd ON p.project_id = pd.project_id " +
                "WHERE pd.developer_id = %s ORDER by p.project_id;", developer.getId());
        return new ProjectDAO(getConnectionManager()).getListByQuery(query, false);
    }

    private void sendSkills(Developer developer) throws DAOException {
        String query = "INSERT INTO developer_skills (developer_id, skill_id ) " +
                "VALUES (?, ?);";
        List<Skill> skillsInDB = receiveSkills(developer);
        List<Skill> newSkills = developer.getSkills();
        if (areNotEquals(skillsInDB, newSkills)) {
            try (Connection connection = getConnectionManager().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                for (Skill skill : newSkills) {
                    statement.setLong(1, developer.getId());
                    statement.setLong(2, skill.getId());
                    statement.execute();
                }
            } catch (SQLException ex) {
                throw new DAOException(ex.getMessage());
            }
        }
    }

    private List<Skill> receiveSkills(Developer developer) throws DAOException {
        String query = String.format("SELECT s.skill_id, s.branch, s.skill_level " +
                "FROM skills s INNER JOIN developer_skills ds ON s.skill_id = ds.skill_id " +
                "WHERE ds.developer_id = %s ORDER by s.skill_id;", developer.getId());
        return new SkillDAO(getConnectionManager()).getListByQuery(query, false);
    }

    @Override
    protected Developer getEntity(ResultSet resultSet, boolean getRelatedEntity) throws DAOException {
        Developer developer = new Developer();
        try {
            developer.setId(resultSet.getLong("developer_id"));
            developer.setFirstName(resultSet.getString("first_name"));
            developer.setLastName(resultSet.getString("last_name"));
            developer.setSex(Sex.findByName(resultSet.getString("sex")));
            developer.setSalary(resultSet.getDouble("salary"));
            if (getRelatedEntity) {
                developer.setProjects(receiveProjects(developer));
                developer.setSkills(receiveSkills(developer));
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
        return developer;
    }

    public List<Developer> getByBranch(Branch branch) {
        String query = String.format("SELECT d.developer_id, d.first_name, d.last_name, d.sex, d.salary " +
                "FROM developers d INNER JOIN developer_skills ds ON d.developer_id = ds.developer_id " +
                "INNER JOIN skills s ON ds.skill_id = s.skill_id " +
                "WHERE s.branch = '%s' ORDER BY d.developer_id;", branch.getName());
        List<Developer> listByQuery = null;
        try {
            listByQuery = getListByQuery(query, false);
        } catch (DAOException ex) {
            ex.printStackTrace();
        }
        return listByQuery;
    }

    public List<Developer> getBySkillLevel(SkillLevel level) {
        String query = String.format("SELECT d.developer_id, d.first_name, d.last_name, d.sex, d.salary " +
                "FROM developers d INNER JOIN developer_skills ds ON d.developer_id = ds.developer_id " +
                "INNER JOIN skills s ON ds.skill_id = s.skill_id " +
                "WHERE s.skill_level = '%s' ORDER BY d.developer_id;", level.getName());
        List<Developer> listByQuery = null;
        try {
            listByQuery = getListByQuery(query, false);
        } catch (DAOException ex) {
            ex.printStackTrace();
        }
        return listByQuery;
    }

}
