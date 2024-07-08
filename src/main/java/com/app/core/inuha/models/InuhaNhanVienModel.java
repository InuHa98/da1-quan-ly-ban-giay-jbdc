/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.app.core.inuha.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author inuHa
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InuhaNhanVienModel {

    private int id;

    private String username;
    
    private String password;

    private String email;

    private String hoTen;

    private String sdt;

    private boolean gioiTinh;

    private String diaChi;

    private String avatar;

    private String otp;

    private boolean trangThai;

    private boolean isAdmin;

    private String ngayTao;

}
