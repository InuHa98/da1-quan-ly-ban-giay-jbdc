package com.app.core.inuha.request;

import com.app.common.infrastructure.request.FillterRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InuhaFilterSanPhamChiTietRequest extends FillterRequest {
    
    private String keyword;
    
    private int idSanPham;
    
    private int idDanhMuc;
    
    private int idThuongHieu;
    
    private int idXuatXu;
    
    private int idKieuDang;
    
    private int idChatLieu;
    
    private int idDeGiay;
    
    private int idKichCo;
    
    private int idMauSac;
    
    private int trangThai;

}
