package com.app.common.infrastructure.constants;

import com.app.core.inuha.views.all.banhang.InuhaBanHangView;
import com.app.core.inuha.views.quanly.thongke.InuhaThongKeView;
import com.app.core.inuha.views.quanly.InuhaSanPhamView;
import com.app.core.inuha.views.quanly.phieugiamgia.InuhaPhieuGiamGiaView;
import com.app.core.lam.views.KhachHangView;
import com.app.utils.RouterUtils;

/**
 *
 * @author inuHa
 */
public class RouterConstant {

    public final static String THONG_KE = RouterUtils.getPackageName(InuhaThongKeView.class);
    
    public final static String BAN_HANG = RouterUtils.getPackageName(InuhaBanHangView.class);
	
    public final static String SAN_PHAM = RouterUtils.getPackageName(InuhaSanPhamView.class);
    
    public final static String PHIEU_GIAM_GIA = RouterUtils.getPackageName(InuhaPhieuGiamGiaView.class);
    
    public final static String DOT_GIAM_GIA = "com.app.core.kienhacker.views.KienDotGiamGiaView";
    
    public final static String HOA_DON = "com.app.core.dattv.views.DatHoaDonView";
    
    public final static String KHACH_HANG = RouterUtils.getPackageName(KhachHangView.class);
    
    public final static String NHAN_VIEN = "com.app.core.dung.views.DungNhanVienView";
    
}
