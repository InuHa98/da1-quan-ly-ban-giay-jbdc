/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.app.common.infrastructure.interfaces;

import com.app.common.infrastructure.request.FillterRequest;

import java.util.List;

/**
 *
 * @author inuHa
 */
public interface IServiceInterface<T, K> {

    T getById(K id);
    
    T insert(T e);

    boolean has(K id);

    void update(T e);
    
    void delete(K id);
    
    void deleteAll(List<K> ids);

    List<T> getAll(FillterRequest request);

    int getTotalPage(FillterRequest request);

}
