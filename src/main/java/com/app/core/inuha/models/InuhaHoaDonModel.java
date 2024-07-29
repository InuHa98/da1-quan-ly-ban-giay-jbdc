package com.app.core.inuha.models;

import com.app.common.infrastructure.constants.TrangThaiHoaDonConstant;
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
    
    private InuhaKhachHangModel khachHang;
    
    private InuhaTaiKhoanModel taiKhoan;
        
    public static String getTrangThai(int trangThai) { 
	return switch (trangThai) {
	    case TrangThaiHoaDonConstant.STATUS_DA_THANH_TOAN -> "Đã thanh toán";
	    case TrangThaiHoaDonConstant.STATUS_DA_HUY -> "Đã huỷ";
	    case TrangThaiHoaDonConstant.STATUS_CHUA_THANH_TOAN -> "Chưa thanh toán";
	    default -> "Không xác định";
	};
    }
    
    public Object[] toDataRowHoaDonCho() { 
	return new Object[] { 
	    stt,
	    ma,
	    taiKhoan.getUsername(),
	    ngayTao,
	    getTrangThai(trangThai)
	};
    }
}
