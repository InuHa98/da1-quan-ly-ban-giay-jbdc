package com.app.core.inuha.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InuhaAddSanPhamRequest {

    private String ten;
    
    private double gia;
    
    private boolean trangThai;
    
    private String moTa;
    
    private int idDanhMuc;
    
    private int idThuongHieu;
    
    private int idXuatXu;
    
    private int idKieuDang;
    
    private int idChatLieu;
    
    private int idDeGiay;
    
    private String hinhAnh;

}
