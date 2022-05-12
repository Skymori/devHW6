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

public class CustomerDAO extends AbstractDAO<Customer> {

    public CustomerDAO(HikariDataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO customers (customer_id, customer_name, industry) " +
                "VALUES(?, ?, ?);";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE customers SET customer_name=?, industry=? " +
                "WHERE customer_id=?;";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT customer_id, customer_name, industry " +
                "FROM customers WHERE customer_id = ?;";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM customers ORDER BY customer_id;";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM customers WHERE customer_id=?;";
    }

    @Override
    protected String getLastIdQuery() {
        return "SELECT max(customer_id) FROM customers;";
    }

    @Override
    protected void sendEntity(PreparedStatement statement, Customer customer) throws DAOException {
        try {
            if (customer.getId() == 0) {
                //CREATE
                customer.setId(getLastId() + 1);
                statement.setLong(1, customer.getId());
                statement.setString(2, customer.getName());
                statement.setString(3, customer.getIndustry());
            } else {
                //UPDATE
                statement.setString(1, customer.getName());
                statement.setString(2, customer.getIndustry());
                statement.setLong(3, customer.getId());
                if (customer.getProjects() != null && customer.getCompanies() != null) {
                    sendCompaniesAndProjects(customer);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    private void sendCompaniesAndProjects(Customer customer) throws DAOException {
        String query = "INSERT INTO customers_companies (customer_id, project_id, company_id) " +
                "VALUES (?, ?, ?);";
        List<Company> companiesInDB = receiveCompanies(customer);
        List<Company> newCompanies = customer.getCompanies();
        List<Project> projectsInDB = receiveProjects(customer);
        List<Project> newProjects = customer.getProjects();
        if (areNotEquals(companiesInDB, newCompanies) && areNotEquals(projectsInDB,newProjects)) {
            try (Connection connection = getConnectionManager().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                for (Company company : newCompanies) {
                    for (Project project : newProjects) {
                        statement.setLong(1, customer.getId());
                        statement.setLong(2, project.getId());
                        statement.setLong(3, company.getId());
                        statement.execute();
                    }
                }
            } catch (SQLException ex) {
                throw new DAOException(ex.getMessage());
            }
        }
    }

    private List<Project> receiveProjects(Customer customer) throws DAOException {
        String query = String.format("SELECT p.project_id, p.project_name, p.project_description, p.cost, p.create_date " +
                "FROM projects p INNER JOIN customers_companies cc ON cc.project_id = p.project_id " +
                "WHERE cc.customer_id = %s ORDER by p.project_id;", customer.getId());
        return new ProjectDAO(getConnectionManager()).getListByQuery(query,false);

    }

    private List<Company> receiveCompanies(Customer customer) throws DAOException {
        String query = String.format("SELECT c.company_id, c.company_name, c.headquarters " +
                "FROM companies c INNER JOIN customers_companies cc ON cc.company_id = c.company_id " +
                "WHERE cc.customer_id = %s ORDER by c.company_id;", customer.getId());
        return new CompanyDAO(getConnectionManager()).getListByQuery(query, false);
    }

    @Override
    protected Customer getEntity(ResultSet resultSet, boolean getRelatedEntity) throws DAOException {
        Customer customer = new Customer();
        try {
            customer.setId(resultSet.getInt("customer_id"));
            customer.setName(resultSet.getString("customer_name"));
            customer.setIndustry(resultSet.getString("industry"));
            if (getRelatedEntity) {
                customer.setCompanies(receiveCompanies(customer));
                customer.setProjects(receiveProjects(customer));
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
        return customer;
    }

}
