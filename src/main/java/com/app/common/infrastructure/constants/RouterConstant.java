package com.app.common.infrastructure.constants;

import com.app.core.all.views.BanHangView;
import com.app.core.all.views.ThongKeView;
import com.app.core.dattv.views.DatHoaDonView;
import com.app.core.dung.views.DungNhanVienView;
import com.app.core.inuha.views.quanly.InuhaSanPhamView;
import com.app.core.khoi.views.KhoiPhieuGiamGiaView;
import com.app.core.kienhacker.views.KienDotGiamGiaView;
import com.app.core.lam.views.KhachHangView;
import com.app.utils.RouterUtils;

/**
 *
 * @author inuHa
 */
public class RouterConstant {

    public final static String THONG_KE = "com.app.core.all.views.ThongKeView";
    
    public final static String BAN_HANG = "com.app.core.all.views.BanHangView";
	
    public final static String SAN_PHAM = RouterUtils.getPackageName(InuhaSanPhamView.class);
    
    public final static String PHIEU_GIAM_GIA = "com.app.core.khoi.views.KhoiPhieuGiamGiaView";
    
    public final static String DOT_GIAM_GIA = "com.app.core.kienhacker.views.KienDotGiamGiaView";
    
    public final static String HOA_DON = "com.app.core.dattv.views.DatHoaDonView";
    
    public final static String KHACH_HANG = "com.app.core.lam.views.KhachHangView";
    
    public final static String NHAN_VIEN = "com.app.core.dung.views.DungNhanVienView";
    
}
