package ua.goit.jdbc.dao;

import com.zaxxer.hikari.HikariDataSource;
import ua.goit.jdbc.dto.Company;
import ua.goit.jdbc.dto.Customer;
import ua.goit.jdbc.dto.Project;
import ua.goit.jdbc.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CompanyDAO extends AbstractDAO<Company> {

    public CompanyDAO(HikariDataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO companies (company_id, company_name, headquarters) " +
                "VALUES(?, ?, ?);";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE companies SET company_name=?, headquarters=? " +
                "WHERE company_id=?;";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT company_id, company_name, headquarters " +
                "FROM companies WHERE company_id = ?;";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM companies ORDER BY company_id;";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM companies WHERE company_id=?;";
    }

    public String getLastIdQuery() {
        return "SELECT max(company_id) FROM companies;";
    }

    @Override
    protected void sendEntity(PreparedStatement statement, Company company) throws DAOException {
        try {
            if (company.getId() == 0) {
                //CREATE
                company.setId(getLastId() + 1);
                statement.setLong(1, company.getId());
                statement.setString(2, company.getName());
                statement.setString(3, company.getHeadquarters());
            } else {
                //UPDATE
                statement.setString(1, company.getName());
                statement.setString(2, company.getHeadquarters());
                statement.setLong(3, company.getId());
                if (company.getCustomers() != null && company.getProjects() != null) {
                    sendProjectsAndCustomers(company);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    private void sendProjectsAndCustomers(Company company) throws DAOException {
        String query = "INSERT INTO customers_companies (company_id, project_id, customer_id) " +
                "VALUES (?, ?, ?);";
        List<Project> projectsInDB = receiveProjects(company);
        List<Project> newProjects = company.getProjects();
        List<Customer> customersInDB = receiveCustomers(company);
        List<Customer> newCustomers = company.getCustomers();
        if (areNotEquals(projectsInDB, newProjects) && areNotEquals(customersInDB,newCustomers)) {
            try (Connection connection = getConnectionManager().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                for (Project project : newProjects) {
                    for (Customer customer : newCustomers) {
                        statement.setLong(1, company.getId());
                        statement.setLong(2, project.getId());
                        statement.setLong(3, customer.getId());
                        statement.execute();
                    }
                }
            } catch (SQLException ex) {
                throw new DAOException(ex.getMessage());
            }
        }
    }

    private List<Customer> receiveCustomers(Company company) throws DAOException {
        String query = String.format("SELECT c.customer_id, c.customer_name, c.industry " +
                "FROM customers c INNER JOIN customers_companies cc ON cc.customer_id = c.customer_id " +
                "WHERE cc.company_id = %s ORDER by c.customer_id;", company.getId());
        return new CustomerDAO(getConnectionManager()).getListByQuery(query,false);
    }

    private List<Project> receiveProjects(Company company) throws DAOException {
        String query = String.format("SELECT p.project_id, p.project_name, p.project_description, p.cost, p.create_date " +
                "FROM projects p INNER JOIN customers_companies cc ON cc.project_id = p.project_id " +
                "WHERE cc.company_id = %s ORDER by p.project_id;", company.getId());
        return new ProjectDAO(getConnectionManager()).getListByQuery(query,false);

    }

    @Override
    protected Company getEntity(ResultSet resultSet, boolean getRelatedEntity) throws DAOException {
        Company company = new Company();
        try {
            company.setId(resultSet.getInt("company_id"));
            company.setName(resultSet.getString("company_name"));
            company.setHeadquarters(resultSet.getString("headquarters"));
            if (getRelatedEntity) {
                company.setProjects(receiveProjects(company));
                company.setCustomers(receiveCustomers(company));
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
        return company;
    }
}
