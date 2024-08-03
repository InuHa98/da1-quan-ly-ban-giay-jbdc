/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.core.dattv.service;

import com.app.common.infrastructure.constants.ErrorConstant;
import com.app.common.infrastructure.exceptions.ServiceResponseException;
import com.app.common.infrastructure.request.FillterRequest;
import com.app.core.dattv.models.DatHoaDonModel;
import com.app.core.dattv.repositoris.DatHoaDonRepository;
import com.app.core.dattv.request.DatHoaDonRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author WIN
 */
public class DatHoaDonService implements IDatHoaDonServiceInterface{
    
    private final DatHoaDonRepository repository = DatHoaDonRepository.getInstance();
    
    private static DatHoaDonService instance = null;
    
    public static DatHoaDonService getInstance() { 
	if (instance == null) { 
	    instance = new DatHoaDonService();
	}
	return instance;
    }

    @Override
    public String getLastCode() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public DatHoaDonRequest getById(Integer id) {
        try {
            Optional<DatHoaDonRequest> find = repository.getById(id);
            if (find.isEmpty()) { 
                throw new SQLException();
            }
            return find.get();
        } catch (SQLException ex) {
            throw new ServiceResponseException("Không tìm thấy sản phẩm");
        }
    }

    @Override
    public Integer insert(DatHoaDonRequest model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean has(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(DatHoaDonRequest model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAll(List<Integer> ids) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<DatHoaDonRequest> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<DatHoaDonRequest> getPage(FillterRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Integer getTotalPage(FillterRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
