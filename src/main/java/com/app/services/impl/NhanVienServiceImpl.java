/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.app.services.impl;

import com.app.common.infrastructure.constants.ErrorConstant;
import com.app.common.infrastructure.exceptions.ServiceResponseException;
import com.app.common.infrastructure.session.SessionLogin;
import com.app.models.NhanVienModel;
import com.app.repositories.NhanVienRepository;
import com.app.services.NhanVienService;
import com.app.utils.SessionUtils;
import com.app.utils.ValidateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 *
 * @author inuHa
 */
@Service
@RequiredArgsConstructor
public class NhanVienServiceImpl implements NhanVienService {

    private final NhanVienRepository nhanVienRepository;

    public NhanVienModel login(String username, String password) {
        Optional<NhanVienModel> nhanVien = ValidateUtils.isEmail(username) ? nhanVienRepository.findNhanVienByEmail(username) : nhanVienRepository.findNhanVienByUsername(username);
        if (nhanVien.isEmpty() || !nhanVien.get().getPassword().equals(password)) {
            throw new ServiceResponseException("Tài khoản hoặc mật khẩu không chính xác!");
        }
        return nhanVien.get();
    }
    
    @Transactional
    public void requestForgotPassword(String email) { 

        if (!ValidateUtils.isEmail(email)) {
            throw new ServiceResponseException(ErrorConstant.EMAIL_FORMAT);
        }

        Optional<NhanVienModel> nhanVien = nhanVienRepository.findNhanVienByEmail(email);
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
	
    }

    public void validOtp(String email, String otp) {

        if (!ValidateUtils.isEmail(email)) {
            throw new ServiceResponseException(ErrorConstant.EMAIL_FORMAT);
        }

        if (!nhanVienRepository.checkOtp(email, otp)) {
            throw new ServiceResponseException("Mã OTP không chính xác.");
        }

    }

    @Transactional
    public void changePassword(String email, String otp, String password, String confirmPassword) {

        if (!ValidateUtils.isPassword(password)) {
            throw new ServiceResponseException("Mật khẩu không hợp lệ");
        }

        if (!password.equals(confirmPassword)) {
            throw new ServiceResponseException("Mật khẩu nhập lại không chính xác");
        }

        if (nhanVienRepository.updateForgotPassword(password, email, otp) < 1) {
            throw new ServiceResponseException("Không thể cập nhật mật khẩu. Vui lòng thử lại");
        }

    }
    
    @Transactional
    public void changeAvatar(String avatar) {

        if (!SessionLogin.getInstance().isLogin()) {
            throw new ServiceResponseException("Vui lòng đăng nhập để sử dụng chức năng này");
        }

        int id = SessionLogin.getInstance().getData().getId();

        if (nhanVienRepository.updateAvatar(id, avatar) < 1) {
            throw new ServiceResponseException("Không thể cập nhật ảnh đại diện. Vui lòng thử lại");
        }

    }
}