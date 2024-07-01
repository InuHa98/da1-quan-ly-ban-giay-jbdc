/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.app.models;

import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author inuHa
 */

public interface INhanVienDTO {

    @Value("#{target.id}")
    int getId();
    
    @Value("#{target.username}")
    String getUsername();
    
    @Value("#{target.password}")
    String getPassword();
    
    @Value("#{target.ho_ten}")
    String getHoTen();
    
    @Value("#{target.email}")
    String getEmail();
    
    @Value("#{target.so_dien_thoai}")
    String getSdt();

    @Value("#{target.avatar}")
    String getAvatar();

    @Value("#{target.chuc_vu}")
    String getChucVu();


}
