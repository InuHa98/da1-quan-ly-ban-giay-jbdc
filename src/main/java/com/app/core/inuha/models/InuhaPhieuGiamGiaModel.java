package com.app.core.inuha.models;

import com.app.utils.BillUtils;
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
public class InuhaPhieuGiamGiaModel {
    
    private int stt;
        
    private int id;
    
    private String ma;
    
    private String ten;
    
    private int soLuong;
    
    private String ngayBatDau;
    
    private String ngayKetThuc;
	
    private boolean giamTheoPhanTram;
    
    private double giaTriGiam;
    
    private double giamToiDa;
    
    private double donToiThieu;
    
    private String ngayTao;
    
    private String ngayCapNhat;
    
    private boolean trangThai;
    
    private boolean trangThaiXoa;
    
    public Object[] toDataRowHoaDon() { 
	return new Object[] { 
	};
    }
}
