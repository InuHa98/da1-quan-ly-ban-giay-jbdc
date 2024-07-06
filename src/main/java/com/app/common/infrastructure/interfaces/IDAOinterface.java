package com.app.common.infrastructure.interfaces;

import java.sql.SQLException;
import java.util.Set;

/**
 *
 * @author InuHa
 */
public interface IDAOinterface<T, I> {

    public T getById(I id) throws SQLException;

    public Set<T> selectAll() throws SQLException;

    public Long insert(T model) throws SQLException;

    public int update(T model) throws SQLException;

    public int delete(I id) throws SQLException;

    public boolean has(I id) throws SQLException;

    public int count() throws SQLException;

}
