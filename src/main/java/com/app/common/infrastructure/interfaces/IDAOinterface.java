package com.app.common.infrastructure.interfaces;

import com.app.common.infrastructure.request.FillterRequest;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author InuHa
 */
public interface IDAOinterface<T, I> {

    int insert(T model) throws SQLException;

    int update(T model) throws SQLException;

    int delete(I id) throws SQLException;

    boolean has(I id) throws SQLException;

    Optional<T> getById(I id) throws SQLException;

    Set<T> selectAll(FillterRequest request) throws SQLException;

    int count(FillterRequest request) throws SQLException;

}
