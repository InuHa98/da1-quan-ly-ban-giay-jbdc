package com.app.common.infrastructure.router;

import com.app.common.infrastructure.session.SessionLogin;
import com.app.core.inuha.views.quanly.InuhaNhanVienView;
import com.app.views.UI.sidebarmenu.SidebarMenuItem;
import lombok.Getter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

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
        itemSideMenu.add(new SidebarMenuItem(1, "statistic", "Thống kê", new JLabel("Giáp ăn lozzzzzzzzz 1")));
        itemSideMenu.add(new SidebarMenuItem(2, "sell", "Bán hàng", new JLabel("Giáp ăn lozzzzzzzzz 2")));
        itemSideMenu.add(new SidebarMenuItem(3, "product", "Sản phẩm", new JLabel("Giáp ăn lozzzzzzzzz 3")));
        itemSideMenu.add(new SidebarMenuItem(4, "voucher", "Mã giảm giá", new JLabel("Giáp ăn lozzzzzzzzz 4")));
        itemSideMenu.add(new SidebarMenuItem(5, "receipt", "Hoá đơn", new JLabel("Giáp ăn lozzzzzzzzz 5")));
        itemSideMenu.add(new SidebarMenuItem(6, "customer", "Khách hàng", new JLabel("Giáp ăn lozzzzzzzzz 6")));
        itemSideMenu.add(new SidebarMenuItem(7, "users", "Nhân viên", new InuhaNhanVienView()));
        itemSideMenu.add(new SidebarMenuItem(8, "password", "Đổi mật khẩu", (button) -> {
            SessionLogin.getInstance().changePassword();
        }));
        itemSideMenu.add(new SidebarMenuItem(9, "logout", "Đăng xuất", (button) -> {
            SessionLogin.getInstance().logout();
        }));
    }

}
