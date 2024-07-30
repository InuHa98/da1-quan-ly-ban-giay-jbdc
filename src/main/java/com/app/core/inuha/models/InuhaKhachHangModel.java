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
public class InuhaKhachHangModel {
    
    private int stt;
        
    private int id;
    
    private String sdt;
    
    private String hoTen;
    
    private boolean gioiTinh;
    
    private String diaChi;
    
    private boolean trangThaiXoa;
        
    public boolean isGioiTinh() { 
	return gioiTinh;
    }
    
    public String getGioiTinh() { 
	return gioiTinh ? "Nam" : "Nữ";
    }
    
    public Object[] toDataRow() { 
	return new Object[] { 
	    stt,
	    null,
	    sdt,
	    hoTen,
	    getGioiTinh(),
	    diaChi
	};
    }
}
