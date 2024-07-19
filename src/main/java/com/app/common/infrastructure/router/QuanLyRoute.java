package com.app.common.infrastructure.router;

import com.app.common.infrastructure.constants.RouterConstant;
import com.app.common.infrastructure.session.SessionLogin;
import com.app.core.inuha.views.quanly.InuhaNhanVienView;
import com.app.views.UI.sidebarmenu.SidebarMenuItem;
import lombok.Getter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author InuHa
 */
public class QuanLyRoute {

    private static QuanLyRoute instance;

    @Getter
    private List<SidebarMenuItem> itemSideMenu = new ArrayList<>();

    private QuanLyRoute() {
        this.init();
    }

    public static QuanLyRoute getInstance() {
        if (instance == null) {
            instance = new QuanLyRoute();
        }
        return instance;
    }

    private void init() {
        itemSideMenu.add(new SidebarMenuItem(1, "statistic", "Thống kê", RouterConstant.THONG_KE));
        itemSideMenu.add(new SidebarMenuItem(2, "sell", "Bán hàng", RouterConstant.BAN_HANG));
        itemSideMenu.add(new SidebarMenuItem(3, "product", "Sản phẩm", RouterConstant.SAN_PHAM));
        itemSideMenu.add(new SidebarMenuItem(4, "voucher", "Phiếu giảm giá", RouterConstant.PHIEU_GIAM_GIA));
        itemSideMenu.add(new SidebarMenuItem(5, "sale", "Đợt giảm giá", RouterConstant.DOT_GIAM_GIA));
        itemSideMenu.add(new SidebarMenuItem(6, "receipt", "Hoá đơn", RouterConstant.HOA_DON));
        itemSideMenu.add(new SidebarMenuItem(7, "customer", "Khách hàng", RouterConstant.KHACH_HANG));
        itemSideMenu.add(new SidebarMenuItem(8, "users", "Nhân viên", RouterConstant.NHAN_VIEN));
        itemSideMenu.add(new SidebarMenuItem(9, "password", "Đổi mật khẩu", (button) -> {
            SessionLogin.getInstance().changePassword();
        }));
        itemSideMenu.add(new SidebarMenuItem(10, "logout", "Đăng xuất", (button) -> {
            SessionLogin.getInstance().logout();
        }));
    }

}
