/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.app.core.inuha.services.impl;

import com.app.common.infrastructure.exceptions.ServiceResponseException;
import com.app.common.infrastructure.session.SessionLogin;
import com.app.core.inuha.models.InuhaNhanVienModel;
import com.app.core.inuha.repositories.InuhaNhanVienRepository;
import com.app.core.inuha.services.InuhaNhanVienService;
import com.app.models.NhanVienModel;
import com.app.services.NhanVienService;
import com.app.utils.ValidateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author inuHa
 */
@Service("inuha_NhanVienService")
@RequiredArgsConstructor
public class InuhaNhanVienServiceImpl implements InuhaNhanVienService {

    private final InuhaNhanVienRepository nhanVienRepository;
    
    private final NhanVienService nhanVienService;
    
    @Override
    public List<InuhaNhanVienModel> getAll() {
	return null;
    }

    @Override
    public List<InuhaNhanVienModel> getAllByIds(List<Integer> ids) {
	return null;
    }

    @Override
    public InuhaNhanVienModel getById(Integer id) {
        try {
            Optional<InuhaNhanVienModel> get = nhanVienRepository.findNhanVienById(id);
            return get.orElse(null);
        }catch (Exception e) {
            e.printStackTrace();
        }

	    return null;
    }

    @Override
    public InuhaNhanVienModel insert(InuhaNhanVienModel e) {
	return null;
    }

    @Override
    public void update(InuhaNhanVienModel e) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void deleteAll(Set<Integer> ids) {

    }


    
    @Transactional
    public void changePassword(String oldPassword, String newPassword, String confirmPassword) {

        if (!oldPassword.equals(SessionLogin.getInstance().getData().getPassword())) {
            throw new ServiceResponseException("Mật khẩu cũ không chính xác");
        }

        if (!ValidateUtils.isPassword(newPassword)) {
            throw new ServiceResponseException("Mật khẩu mới không hợp lệ");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new ServiceResponseException("Mật khẩu nhập lại không chính xác");
        }

        int id = SessionLogin.getInstance().getData().getId();
        if (nhanVienRepository.updatePassword(id, oldPassword, newPassword) < 1) {
            throw new ServiceResponseException("Không thể đổi mật khẩu. Vui lòng thử lại");
        }

        String username = SessionLogin.getInstance().getData().getUsername();
        NhanVienModel data = nhanVienService.login(username, newPassword);
        SessionLogin.getInstance().create(username, newPassword, data);
    }
    
}