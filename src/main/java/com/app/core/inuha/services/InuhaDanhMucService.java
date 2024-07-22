package com.app.core.inuha.services;

import com.app.common.infrastructure.constants.ErrorConstant;
import com.app.common.infrastructure.exceptions.ServiceResponseException;
import com.app.common.infrastructure.request.FillterRequest;
import com.app.core.inuha.models.InuhaDanhMucModel;
import com.app.core.inuha.repositories.InuhaDanhMucRepository;
import com.app.core.inuha.services.impl.IInuhaDanhMucServiceInterface;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author InuHa
 */
public class InuhaDanhMucService implements IInuhaDanhMucServiceInterface {

    private final InuhaDanhMucRepository danhMucRepository = new InuhaDanhMucRepository();
    
    @Override
    public InuhaDanhMucModel getById(Integer id) {
        return null;
    }

    @Override
    public Integer insert(InuhaDanhMucModel model) {
        try {
            if (danhMucRepository.has(model.getTen())) { 
                throw new ServiceResponseException("Tên danh mục đã tồn tại trên hệ thống");
            }
            int rows = danhMucRepository.insert(model);
            if (rows < 1) { 
                throw new ServiceResponseException("Không thể thêm danh mục");
            }
            return rows;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ServiceResponseException(ErrorConstant.DEFAULT_ERROR);
        }
    }

    @Override
    public boolean has(Integer id) {
        return false;
    }

    @Override
    public void update(InuhaDanhMucModel e) {
    }

    @Override
    public void delete(Integer id) {
    }

    @Override
    public void deleteAll(List<Integer> ids) {
    }

    @Override
    public Set<InuhaDanhMucModel> getAll() {
        try {
            return danhMucRepository.selectAll();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ServiceResponseException(ErrorConstant.DEFAULT_ERROR);
        }
    }

    @Override
    public Set<InuhaDanhMucModel> getPage(FillterRequest request) {
        try {
            return danhMucRepository.selectPage(request);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new LinkedHashSet<>();
    }

    @Override
    public Integer getTotalPage(FillterRequest request) {
        try {
            return danhMucRepository.count(request);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    
}
