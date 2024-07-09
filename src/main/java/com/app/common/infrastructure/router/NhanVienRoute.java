package com.app.common.infrastructure.router;

import com.app.common.infrastructure.session.SessionLogin;
import com.app.views.UI.sidebarmenu.SidebarMenuItem;
import lombok.Getter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author InuHa
 */
public class NhanVienRoute {

    private static NhanVienRoute instance;

    @Getter
    private List<SidebarMenuItem> itemSideMenu = new ArrayList<>();

    private NhanVienRoute() {
        this.init();
    }

    public static NhanVienRoute getInstance() {
        if (instance == null) {
            instance = new NhanVienRoute();
        }
        return instance;
    }

    private void init() {
        itemSideMenu.add(new SidebarMenuItem(1, "sell", "Bán hàng", new JLabel("1")));
        itemSideMenu.add(new SidebarMenuItem(2, "receipt", "Hoá đơn", new JLabel("2")));
        itemSideMenu.add(new SidebarMenuItem(3, "customer", "Khách hàng", new JLabel("3")));
        itemSideMenu.add(new SidebarMenuItem(4, "password", "Đổi mật khẩu", (button) -> {
            SessionLogin.getInstance().changePassword();
        }));
        itemSideMenu.add(new SidebarMenuItem(5, "logout", "Đăng xuất", (button) -> {
            SessionLogin.getInstance().logout();
        }));
    }

}
