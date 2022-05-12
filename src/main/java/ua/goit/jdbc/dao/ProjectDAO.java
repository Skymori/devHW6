package ua.goit.jdbc.dao;

import com.zaxxer.hikari.HikariDataSource;
import ua.goit.jdbc.dto.Company;
import ua.goit.jdbc.dto.Customer;
import ua.goit.jdbc.dto.Developer;
import ua.goit.jdbc.dto.Project;
import ua.goit.jdbc.exceptions.DAOException;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class ProjectDAO extends AbstractDAO<Project> {

    public ProjectDAO(HikariDataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO projects (project_id, project_name, project_description, cost, create_date) " +
                "VALUES(?, ?, ?, ?, ?);";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE projects SET project_name=?, project_description=? , cost=?, create_date=? " +
                "WHERE project_id=?;";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT project_id, project_name, project_description, cost, create_date " +
                "FROM projects WHERE project_id = ?;";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM projects ORDER BY project_id;";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM projects WHERE project_id=?;";
    }

    @Override
    protected String getLastIdQuery() {
        return "SELECT max(project_id) FROM projects;";
    }

    @Override
    protected void sendEntity(PreparedStatement statement, Project project) throws DAOException {
        try {
            if ( project.getId()== 0) {
                //CREATE
                project.setId(getLastId() + 1);
                statement.setLong(1, project.getId());
                statement.setString(2, project.getName());
                statement.setString(3, project.getDescription());
                statement.setDouble(4, project.getCost());
                statement.setDate(5, Date.valueOf(project.getDate()));
            } else {
                //UPDATE
                statement.setString(1, project.getName());
                statement.setString(2, project.getDescription());
                statement.setDouble(3, project.getCost());
                statement.setDate(4, Date.valueOf(project.getDate()));
                statement.setLong(5, project.getId());
                if (project.getDevelopers() != null) {
                    sendDevelopers(project);
                }
                if (project.getCompanies() != null && project.getCustomers() != null) {
                    sendCompaniesAndCustomers(project);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    private void sendDevelopers(Project project) throws DAOException {
        String query = "INSERT INTO project_developers (project_id, developer_id) " +
                "VALUES (?, ?);";
        List<Developer> developersInDB = receiveDevelopers(project);
        List<Developer> newDevelopers = project.getDevelopers();
        if (areNotEquals(developersInDB, newDevelopers)) {
            try (Connection connection = getConnectionManager().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                for (Developer dev : newDevelopers) {
                    statement.setLong(1, project.getId());
                    statement.setLong(2, dev.getId());
                    statement.execute();
                }
            } catch (SQLException ex) {
                throw new DAOException(ex.getMessage());
            }
        }
    }

    private List<Developer> receiveDevelopers(Project project) throws DAOException {
        String query = String.format("SELECT d.developer_id, d.first_name, d.last_name, d.sex, d.salary " +
                "FROM developers d INNER JOIN project_developers pd ON pd.developer_id = d.developer_id " +
                "WHERE pd.project_id = %s ORDER by d.developer_id;", project.getId());
        return new DeveloperDAO(getConnectionManager()).getListByQuery(query, false);
    }


    private void sendCompaniesAndCustomers(Project project) throws DAOException {
        String query = "INSERT INTO customers_companies (project_id, company_id, customer_id) " +
                "VALUES (?, ?, ?);";
        List<Company> companiesInDB = receiveCompanies(project);
        List<Company> newCompanies = project.getCompanies();
        List<Customer> customersInDB = receiveCustomers(project);
        List<Customer> newCustomers = project.getCustomers();
        if (areNotEquals(companiesInDB, newCompanies) && areNotEquals(customersInDB,newCustomers)) {
            try (Connection connection = getConnectionManager().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                for (Company company : newCompanies) {
                    for (Customer customer : newCustomers) {
                        statement.setLong(1, project.getId());
                        statement.setLong(2, company.getId());
                        statement.setLong(3, customer.getId());
                        statement.execute();
                    }
                }
            } catch (SQLException ex) {
                throw new DAOException(ex.getMessage());
            }
        }
    }

    private List<Company> receiveCompanies(Project project) throws DAOException {
        String query = String.format("SELECT c.company_id, c.company_name, c.headquarters " +
                "FROM companies c INNER JOIN customers_companies cc ON cc.company_id = c.company_id " +
                "WHERE cc.project_id = %s ORDER by c.company_id;", project.getId());
        return new CompanyDAO(getConnectionManager()).getListByQuery(query, false);
    }

    private List<Customer> receiveCustomers(Project project) throws DAOException {
        String query = String.format("SELECT c.customer_id, c.customer_name, c.industry " +
                "FROM customers c INNER JOIN customers_companies cc ON cc.customer_id = c.customer_id " +
                "WHERE cc.project_id = %s ORDER by c.customer_id;", project.getId());
        return new CustomerDAO(getConnectionManager()).getListByQuery(query, false);
    }

    @Override
    protected Project getEntity(ResultSet resultSet, boolean getRelatedEntity) throws DAOException {
        Project project = new Project();
        try {
            project.setId(resultSet.getInt("project_id"));
            project.setName(resultSet.getString("project_name"));
            project.setDescription(resultSet.getString("project_description"));
            project.setCost(resultSet.getDouble("cost"));
            project.setDate(resultSet.getObject("create_date", LocalDate.class));
            if (getRelatedEntity) {
                project.setDevelopers(receiveDevelopers(project));
                project.setCompanies(receiveCompanies(project));
                project.setCustomers(receiveCustomers(project));
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
        return project;
    }

}
