/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.app.core.dattv.service;

import com.app.common.infrastructure.interfaces.IServiceInterface;
import com.app.core.dattv.models.DatHoaDonModel;
import com.app.core.dattv.request.DatHoaDonRequest;

/**
 *
 * @author WIN
 */
public interface IDatHoaDonServiceInterface extends IServiceInterface<DatHoaDonRequest, Integer>{
    String getLastCode();
}
