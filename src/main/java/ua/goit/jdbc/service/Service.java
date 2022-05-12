package ua.goit.jdbc.service;

import ua.goit.jdbc.dao.GenericDAO;
import ua.goit.jdbc.exceptions.DAOException;

import java.util.List;

public class Service<T> {
    private final GenericDAO<T> repository;

    public Service(GenericDAO<T> repository) {
        this.repository = repository;
    }

    public T create(T company) throws DAOException {
        return repository.create(company);
    }

    public T update(T entity) throws DAOException {
        return repository.update(entity);
    }

    public void delete(long id) throws DAOException {
        repository.read(id);
        repository.delete(id);
    }

    public T findById(long id) throws DAOException {
        return repository.read(id);
    }

    public List<T> readAll() {
        List<T> entities = null;
        try {
            entities = repository.readAll();
        } catch (DAOException ex) {
            ex.printStackTrace();
        }
        return entities;
    }

}
