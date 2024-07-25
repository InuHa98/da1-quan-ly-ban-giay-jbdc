package com.app.core.inuha.services;

import com.app.common.infrastructure.constants.ErrorConstant;
import com.app.common.infrastructure.constants.TrangThaiXoaConstant;
import com.app.common.infrastructure.exceptions.ServiceResponseException;
import com.app.common.infrastructure.request.FillterRequest;
import com.app.core.inuha.models.sanpham.InuhaThuongHieuModel;
import com.app.core.inuha.repositories.sanpham.InuhaThuongHieuRepository;
import com.app.core.inuha.services.impl.IInuhaThuongHieuServiceInterface;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author InuHa
 */
public class InuhaThuongHieuService implements IInuhaThuongHieuServiceInterface {

    private final InuhaThuongHieuRepository repository = new InuhaThuongHieuRepository();
    
    @Override
    public InuhaThuongHieuModel getById(Integer id) {
        return null;
    }

    @Override
    public Integer insert(InuhaThuongHieuModel model) {
        try {
            if (repository.has(model.getTen())) { 
                throw new ServiceResponseException("Tên thương hiệu đã tồn tại trên hệ thống");
            }
            int rows = repository.insert(model);
            if (rows < 1) { 
                throw new ServiceResponseException("Không thể thêm thương hiệu");
            }
            return rows;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ServiceResponseException(ErrorConstant.DEFAULT_ERROR);
        }
    }

    @Override
    public boolean has(Integer id) {
        try {
            return repository.has(id);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ServiceResponseException("Không thể tìm kiếm thương hiệu");
        }
    }

    @Override
    public void update(InuhaThuongHieuModel model) {
        try {
            Optional<InuhaThuongHieuModel> find = repository.getById(model.getId());
            if (find.isEmpty()) { 
                throw new ServiceResponseException("Không tìm thấy thương hiệu");
            }
            
            if (repository.has(model)) { 
                throw new ServiceResponseException("Tên thương hiệu đã tồn tại trên hệ thống");
            }
            repository.update(model);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ServiceResponseException("Không thể xoá thương hiệu này");
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            Optional<InuhaThuongHieuModel> find = repository.getById(id);
            if (find.isEmpty()) { 
                throw new ServiceResponseException("Không tìm thấy thương hiệu");
            }
            
            if (repository.hasUse(id)) { 
                InuhaThuongHieuModel item = find.get();
                item.setTrangThaiXoa(TrangThaiXoaConstant.DA_XOA);
                repository.update(item);
            } else { 
                repository.delete(id);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ServiceResponseException("Không thể xoá thương hiệu này");
        }
    }

    @Override
    public void deleteAll(List<Integer> ids) {
    }

    @Override
    public List<InuhaThuongHieuModel> getAll() {
        try {
            return repository.selectAll();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ServiceResponseException("Không thể lấy danh sách thương hiệu");
        }
    }

    @Override
    public List<InuhaThuongHieuModel> getPage(FillterRequest request) {
        try {
            return repository.selectPage(request);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public Integer getTotalPage(FillterRequest request) {
        try {
            return repository.count(request);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    
}
