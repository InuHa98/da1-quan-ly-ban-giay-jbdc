package com.app.core.inuha.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author InuHa
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InuhaHoaDonChiTietModel {
    
    private int stt;
        
    private int id;
    
    private double giaNhap;
    
    private double giaBan;
    
    private double giaGiam;
	
    private int soLuong;
    
    private InuhaSanPhamChiTietModel sanPhamChiTiet;
    
    public Object[] toDataRow() { 
	return null;
    }
}
