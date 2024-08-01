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
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author WIN
 */
public class DatHoaDonService implements IDatHoaDonServiceInterface{
    private  final DatHoaDonRepository repository=new DatHoaDonRepository();

    @Override
    public String getLastCode() {
try {
            return repository.getLastCode();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;    }

    @Override
    public DatHoaDonModel getById(Integer id) {
try {
            Optional<DatHoaDonModel> find = repository.getById(id);
            if (find.isEmpty()) { 
                throw new SQLException();
            }
            return find.get();
        } catch (SQLException ex) {
            throw new ServiceResponseException("Không tìm thấy sản phẩm");
        }    }

    @Override
    public Integer insert(DatHoaDonModel model) {
        return null;
    }

    @Override
    public boolean has(Integer id) {
try {
            return repository.has(id);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ServiceResponseException("Không thể tìm kiếm sản phẩm");
        }    }

    @Override
    public void update(DatHoaDonModel model) {
try {
            Optional<DatHoaDonModel> find = repository.getById(model.getId());
            if (find.isEmpty()) { 
                throw new ServiceResponseException("Không tìm thấy sản phẩm");
            }
            
            
            repository.update(model);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ServiceResponseException("Không thể xoá sản phẩm này");
        }    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAll(List<Integer> ids) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<DatHoaDonModel> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<DatHoaDonModel> getPage(FillterRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Integer getTotalPage(FillterRequest request) {
try {
            return repository.count(request);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;    }
    
}
