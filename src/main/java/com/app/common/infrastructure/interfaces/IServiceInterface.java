/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.app.common.infrastructure.interfaces;

import java.util.List;

/**
 *
 * @author inuHa
 */
public interface IServiceInterface<T, K> {
    
    List<T> getAll();
    
    List<T> getAllByIds(List<K> ids);
    
    T getById(K id);
    
    T insert(T e);

    boolean has(K id);

    void update(T e);
    
    void delete(K id);
    
    void deleteAll(List<K> ids);
    
}
