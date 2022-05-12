package ua.goit.jdbc.dao;

import com.zaxxer.hikari.HikariDataSource;
import ua.goit.jdbc.dto.*;
import ua.goit.jdbc.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SkillDAO extends AbstractDAO<Skill> {

    public SkillDAO(HikariDataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO skills (skill_id, branch, skill_level) " +
                "VALUES(?, CAST(? AS language), CAST(? AS level));";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE skills SET branch=CAST(? AS language), skill_level=CAST(? AS level) " +
                "WHERE skill_id=?;";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT skill_id, branch, skill_level " +
                "FROM skills WHERE skill_id = ?;";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM skills ORDER BY skill_id;";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM skills WHERE skill_id=?;";
    }

    @Override
    protected String getLastIdQuery() {
        return "SELECT max(skill_id) FROM skills;";
    }

    @Override
    protected void sendEntity(PreparedStatement statement, Skill skill) throws DAOException {
        try {
            if (skill.getId() == 0) {
                //CREATE
                skill.setId(getLastId() + 1);
                statement.setLong(1, skill.getId());
                statement.setString(2, skill.getBranch().getName());
                statement.setString(3, skill.getLevel().getName());
            } else {
                //UPDATE
                statement.setString(1, skill.getBranch().getName());
                statement.setString(2, skill.getLevel().getName());
                statement.setLong(3, skill.getId());
                if (skill.getDevelopers() != null) {
                    sendDevelopers(skill);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    private void sendDevelopers(Skill skill) throws DAOException {
        String query = "INSERT INTO developers_skills (skill_id, developer_id) " +
                "VALUES (?, ?);";
        List<Developer> projectsInDB = receiveDevelopers(skill);
        List<Developer> newDevelopers = skill.getDevelopers();
        if (areNotEquals(projectsInDB, newDevelopers)) {
            try (Connection connection = getConnectionManager().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                for (Developer developer : newDevelopers) {
                    statement.setLong(1, skill.getId());
                    statement.setLong(2, developer.getId());
                    statement.execute();
                }
            } catch (SQLException ex) {
                throw new DAOException(ex.getMessage());
            }
        }
    }

    private List<Developer> receiveDevelopers(Skill skill) throws DAOException {
        String query = String.format("SELECT d.developer_id, d.first_name, d.last_name, d.sex, d.salary " +
                "FROM developers d INNER JOIN developer_skills ds ON d.developer_id=ds.developer_id " +
                "WHERE ds.skill_id = %s ORDER by d.developer_id;", skill.getId());
        return new DeveloperDAO(getConnectionManager()).getListByQuery(query, false);
    }

    @Override
    protected Skill getEntity(ResultSet resultSet, boolean getRelatedEntity) throws DAOException {
        Skill skill = new Skill();
        try {
            skill.setId(resultSet.getInt("skill_id"));
            skill.setBranch(Branch.findByName(resultSet.getString("branch")));
            skill.setLevel(SkillLevel.findByName(resultSet.getString("skill_level")));
            if (getRelatedEntity) {
                skill.setDevelopers(receiveDevelopers(skill));
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
        return skill;
    }

}
