package com.app.core.inuha.models;

import java.util.List;
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
public class InuhaHoaDonModel {
    
    private int stt;
        
    private int id;
    
    private String ma;
    
    private double tienGiam;
    
    private double tienMat;
    
    private double tienChuyenKhoan;
	
    private int phuongThucThanhToan;
    
    private String ghiChu;
    
    private int trangThai;
    
    private String ngayTao;
    
    private String ngayCapNhat;
    
    private boolean trangThaiXoa;
    
    private List<InuhaHoaDonChiTietModel> hoaDonChiTiets;
    
    public Object[] toDataRowHoaDonCho() { 
	return null;
    }
}
