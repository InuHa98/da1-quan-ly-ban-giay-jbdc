package com.app.core.inuha.services;

import com.app.common.infrastructure.constants.ErrorConstant;
import com.app.common.infrastructure.constants.TrangThaiXoaConstant;
import com.app.common.infrastructure.exceptions.ServiceResponseException;
import com.app.common.infrastructure.request.FillterRequest;
import com.app.core.inuha.models.sanpham.InuhaXuatXuModel;
import com.app.core.inuha.repositories.sanpham.InuhaXuatXuRepository;
import com.app.core.inuha.services.impl.IInuhaXuatXuServiceInterface;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author InuHa
 */
public class InuhaXuatXuService implements IInuhaXuatXuServiceInterface {

    private final InuhaXuatXuRepository repository = new InuhaXuatXuRepository();
    
    @Override
    public InuhaXuatXuModel getById(Integer id) {
        return null;
    }

    @Override
    public Integer insert(InuhaXuatXuModel model) {
        try {
            if (repository.has(model.getTen())) { 
                throw new ServiceResponseException("Tên xuất xứ đã tồn tại trên hệ thống");
            }
            int rows = repository.insert(model);
            if (rows < 1) { 
                throw new ServiceResponseException("Không thể thêm xuất xứ");
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
            throw new ServiceResponseException("Không thể tìm kiếm xuất xứ");
        }
    }

    @Override
    public void update(InuhaXuatXuModel model) {
        try {
            Optional<InuhaXuatXuModel> find = repository.getById(model.getId());
            if (find.isEmpty()) { 
                throw new ServiceResponseException("Không tìm thấy xuất xứ");
            }
            
            if (repository.has(model)) { 
                throw new ServiceResponseException("Tên xuất xứ đã tồn tại trên hệ thống");
            }
            repository.update(model);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ServiceResponseException("Không thể xoá xuất xứ này");
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            Optional<InuhaXuatXuModel> find = repository.getById(id);
            if (find.isEmpty()) { 
                throw new ServiceResponseException("Không tìm thấy xuất xứ");
            }
            
            if (repository.hasUse(id)) { 
                InuhaXuatXuModel item = find.get();
                item.setTrangThaiXoa(TrangThaiXoaConstant.DA_XOA);
                repository.update(item);
            } else { 
                repository.delete(id);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ServiceResponseException("Không thể xoá xuất xứ này");
        }
    }

    @Override
    public void deleteAll(List<Integer> ids) {
    }

    @Override
    public List<InuhaXuatXuModel> getAll() {
        try {
            return repository.selectAll();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ServiceResponseException("Không thể lấy danh sách xuất xứ");
        }
    }

    @Override
    public List<InuhaXuatXuModel> getPage(FillterRequest request) {
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
