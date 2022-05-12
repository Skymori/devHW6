package ua.goit.jdbc.dao;

import com.zaxxer.hikari.HikariDataSource;
import ua.goit.jdbc.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO<T> implements GenericDAO<T> {
    private final HikariDataSource dataSource;

    protected AbstractDAO(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected HikariDataSource getConnectionManager() {
        return dataSource;
    }

    protected abstract String getCreateQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getSelectByIdQuery();

    protected abstract String getDeleteQuery();

    protected abstract String getLastIdQuery();

    protected abstract String getSelectAllQuery();

    protected abstract void sendEntity(PreparedStatement statement, T object) throws DAOException;

    protected abstract T getEntity(ResultSet resultSet, boolean getRelatedEntity) throws DAOException;

    protected long getLastId() throws DAOException {
        String lastIdQuery = getLastIdQuery();
        long result = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(lastIdQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getLong("max");
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage());
        }
        return result;
    }

    @Override
    public T create(T entity) throws DAOException {
        String createQuery = getCreateQuery();
        T createdEntity;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            sendEntity(statement, entity);
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                createdEntity = read(resultSet.getLong(1));
            } else {
                throw new DAOException("Problems with creating the object");
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage());
        }
        return createdEntity;
    }

    @Override
    public T read(long id) throws DAOException {
        T entity;
        String selectByIdQuery = getSelectByIdQuery();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectByIdQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                entity = getEntity(resultSet, true);
            } else {
                throw new DAOException("There is no object with such ID!");
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage());
        }
        return entity;
    }

    @Override
    public T update(T entity) throws DAOException {
        String updateQuery = getUpdateQuery();
        T updated;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateQuery, Statement.RETURN_GENERATED_KEYS)) {
            sendEntity(statement, entity);
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                updated = read(resultSet.getLong(1));
            } else {
                throw new DAOException("There are problems with updating the object");
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage());
        }
        return updated;
    }

    @Override
    public void delete(long id) throws DAOException {
        String deleteQuery = getDeleteQuery();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage());
        }
    }

    @Override
    public List<T> readAll() throws DAOException {
        String selectAllQuery = getSelectAllQuery();
        return getListByQuery(selectAllQuery, true);
    }

    protected List<T> getListByQuery(String query, boolean getRelatedEntity) throws DAOException {
        List<T> entities = new ArrayList<>();
        T entity;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entity = getEntity(resultSet, getRelatedEntity);
                entities.add(entity);
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage());
        }
        return entities;
    }

    protected <V> boolean areNotEquals(List<V> listInDB, List<V> entityList) {
        return !entityList.equals(listInDB) && !listInDB.containsAll(entityList) && !entityList.isEmpty();
    }

    protected <V> boolean validateInfo(List<V> listInDataBase, List<V> newList) {
        boolean areDifferent = areNotEquals(listInDataBase, newList);
        boolean hasDuplicates = newList.removeIf(listInDataBase::contains);

        return true;
    }
}
