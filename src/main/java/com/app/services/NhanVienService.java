/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.app.services;

import com.app.models.NhanVienModel;

/**
 *
 * @author inuHa
 */
public interface NhanVienService {

    NhanVienModel login(String username, String password);

    void requestForgotPassword(String email);

    void validOtp(String email, String otp);

    void changePassword(String email, String otp, String password, String confirmPassword);
    
    void changeAvatar(String avatar);

}