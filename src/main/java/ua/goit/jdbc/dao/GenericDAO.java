package ua.goit.jdbc.dao;

import ua.goit.jdbc.exceptions.DAOException;

import java.util.List;

public interface GenericDAO<T> {

    T create(T entity) throws DAOException;

    T read(long id) throws DAOException;

    T update(T entity) throws DAOException;

    void delete(long id) throws DAOException;

    List<T> readAll() throws DAOException;

}
