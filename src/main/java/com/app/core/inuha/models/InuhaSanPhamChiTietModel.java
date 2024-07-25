package com.app.core.inuha.models;

import com.app.core.inuha.models.sanpham.*;
import com.app.utils.CurrencyUtils;
import com.app.utils.ProductUtils;
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
public class InuhaSanPhamChiTietModel {
    
    private int stt;
        
    private int id;
    
    private int soLuong;
    
    private boolean trangThai;
    
    private String ngayTao;
    
    private String ngayCapNhat;
    
    private boolean trangThaiXoa;
    
    private InuhaSanPhamModel sanPham;
    
    private InuhaKichCoModel kichCo;
    
    private InuhaMauSacModel mauSac;
    
    public Object[] toDataRowSanPhamChiTiet() { 
        return new Object[] { 
            false,
            stt,
            sanPham.getTen(),
            kichCo.getTen(),
            mauSac.getTen(),
            CurrencyUtils.parseString(soLuong),
            ProductUtils.getTrangThai(trangThai)
	};
    }
}
