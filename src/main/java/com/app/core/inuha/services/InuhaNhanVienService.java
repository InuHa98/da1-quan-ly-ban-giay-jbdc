/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.app.core.inuha.services;

import com.app.common.infrastructure.constants.ErrorConstant;
import com.app.common.infrastructure.exceptions.ServiceResponseException;
import com.app.common.infrastructure.session.SessionLogin;
import com.app.core.inuha.models.InuhaNhanVienModel;
import com.app.core.inuha.repositories.InuhaNhanVienRepository;
import com.app.core.inuha.services.impl.IInuhaNhanVienServiceInterface;
import com.app.utils.SessionUtils;
import com.app.utils.ValidateUtils;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author inuHa
 */

public class InuhaNhanVienService implements IInuhaNhanVienServiceInterface {

    private final InuhaNhanVienRepository nhanVienRepository = new InuhaNhanVienRepository();

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
	    return null;
    }

    @Override
    public InuhaNhanVienModel insert(InuhaNhanVienModel e) {
	return null;
    }

    @Override
    public boolean has(Integer id) {
        return false;
    }

    @Override
    public void update(InuhaNhanVienModel e) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void deleteAll(List<Integer> ids) {

    }

    public InuhaNhanVienModel login(String username, String password) {
        try {
            Optional<InuhaNhanVienModel> find = ValidateUtils.isEmail(username) ? nhanVienRepository.findNhanVienByEmail(username) : nhanVienRepository.findNhanVienByUsername(username);
            if (find.isEmpty() || !find.get().getPassword().equals(password)) {
                throw new ServiceResponseException("Tài khoản hoặc mật khẩu không chính xác!");
            }
            InuhaNhanVienModel nhanVien = find.get();
            if (!nhanVien.isTrangThai()) {
                throw new ServiceResponseException("Tài khoản của bạn đã bị khoá!");
            }

            return nhanVien;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServiceResponseException(ErrorConstant.DEFAULT_ERROR);
        }
    }

    public void requestForgotPassword(String email) {

        if (!ValidateUtils.isEmail(email)) {
            throw new ServiceResponseException(ErrorConstant.EMAIL_FORMAT);
        }

        try {
            Optional<InuhaNhanVienModel> nhanVien = nhanVienRepository.findNhanVienByEmail(email);
            if (nhanVien.isEmpty()) {
                throw new ServiceResponseException("Email vừa nhập không tồn tại trên hệ thống.");
            }

            String codeOTP = SessionUtils.generateCode(100000, 999999);

            if (nhanVienRepository.updateOTPById(nhanVien.get().getId(), codeOTP) < 1) {
                throw new RuntimeException("Không thể update OTP");
            }

            if (!SessionUtils.sendOtp(codeOTP, email)) {
                throw new ServiceResponseException("Không thể gửi mã. Vui lòng thử lại sau ít phút.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServiceResponseException(ErrorConstant.DEFAULT_ERROR);
        }
    }

    public void validOtp(String email, String otp) {
        try {
            if (!ValidateUtils.isEmail(email)) {
                throw new ServiceResponseException(ErrorConstant.EMAIL_FORMAT);
            }

            if (!nhanVienRepository.checkOtp(email, otp)) {
                throw new ServiceResponseException("Mã OTP không chính xác.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServiceResponseException(ErrorConstant.DEFAULT_ERROR);
        }
    }


    public void changePassword(String email, String otp, String password, String confirmPassword) {

        try {
            if (!ValidateUtils.isPassword(password)) {
                throw new ServiceResponseException("Mật khẩu không hợp lệ");
            }

            if (!password.equals(confirmPassword)) {
                throw new ServiceResponseException("Mật khẩu nhập lại không chính xác");
            }

            if (nhanVienRepository.updateForgotPassword(password, email, otp) < 1) {
                throw new ServiceResponseException("Không thể cập nhật mật khẩu. Vui lòng thử lại");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServiceResponseException(ErrorConstant.DEFAULT_ERROR);
        }

    }

    public void changeAvatar(String avatar) {
        try {
            if (!SessionLogin.getInstance().isLogin()) {
                throw new ServiceResponseException("Vui lòng đăng nhập để sử dụng chức năng này");
            }

            int id = SessionLogin.getInstance().getData().getId();

            if (nhanVienRepository.updateAvatar(id, avatar) < 1) {
                throw new ServiceResponseException("Không thể cập nhật ảnh đại diện. Vui lòng thử lại");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServiceResponseException(ErrorConstant.DEFAULT_ERROR);
        }
    }

    public void changePassword(String oldPassword, String newPassword, String confirmPassword) {

        try {
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
            InuhaNhanVienModel data = login(username, newPassword);
            SessionLogin.getInstance().create(username, newPassword, data);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServiceResponseException(ErrorConstant.DEFAULT_ERROR);
        }

    }
    
}