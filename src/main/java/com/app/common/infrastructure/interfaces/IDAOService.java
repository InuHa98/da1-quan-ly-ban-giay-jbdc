/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.app.common.infrastructure.interfaces;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Set;

/**
 *
 * @author inuHa
 */
public interface IDAOService<T, K> {
    
    List<T> getAll();
    
    List<T> getAllByIds(List<K> ids);
    
    T getById(K id);
    
    T insert(T e);
    
    void update(T e);
    
    void delete(K id);
    
    void deleteAll(Set<K> ids);
    
}
