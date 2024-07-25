package com.app.core.inuha.services;

import com.app.common.infrastructure.constants.ErrorConstant;
import com.app.common.infrastructure.constants.TrangThaiXoaConstant;
import com.app.common.infrastructure.exceptions.ServiceResponseException;
import com.app.common.infrastructure.request.FillterRequest;
import com.app.core.inuha.models.sanpham.InuhaDeGiayModel;
import com.app.core.inuha.repositories.sanpham.InuhaDeGiayRepository;
import com.app.core.inuha.services.impl.IInuhaDeGiayServiceInterface;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author InuHa
 */
public class InuhaDeGiayService implements IInuhaDeGiayServiceInterface {

    private final InuhaDeGiayRepository repository = new InuhaDeGiayRepository();
    
    @Override
    public InuhaDeGiayModel getById(Integer id) {
        return null;
    }

    @Override
    public Integer insert(InuhaDeGiayModel model) {
        try {
            if (repository.has(model.getTen())) { 
                throw new ServiceResponseException("Tên đế giày đã tồn tại trên hệ thống");
            }
            int rows = repository.insert(model);
            if (rows < 1) { 
                throw new ServiceResponseException("Không thể thêm đế giày");
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
            throw new ServiceResponseException("Không thể tìm kiếm đế giày");
        }
    }

    @Override
    public void update(InuhaDeGiayModel model) {
        try {
            Optional<InuhaDeGiayModel> find = repository.getById(model.getId());
            if (find.isEmpty()) { 
                throw new ServiceResponseException("Không tìm thấy đế giày");
            }
            
            if (repository.has(model)) { 
                throw new ServiceResponseException("Tên đế giày đã tồn tại trên hệ thống");
            }
            repository.update(model);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ServiceResponseException("Không thể xoá đế giày này");
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            Optional<InuhaDeGiayModel> find = repository.getById(id);
            if (find.isEmpty()) { 
                throw new ServiceResponseException("Không tìm thấy đế giày");
            }
            
            if (repository.hasUse(id)) { 
                InuhaDeGiayModel item = find.get();
                item.setTrangThaiXoa(TrangThaiXoaConstant.DA_XOA);
                repository.update(item);
            } else { 
                repository.delete(id);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ServiceResponseException("Không thể xoá đế giày này");
        }
    }

    @Override
    public void deleteAll(List<Integer> ids) {
    }

    @Override
    public List<InuhaDeGiayModel> getAll() {
        try {
            return repository.selectAll();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ServiceResponseException("Không thể lấy danh sách đế giày");
        }
    }

    @Override
    public List<InuhaDeGiayModel> getPage(FillterRequest request) {
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
