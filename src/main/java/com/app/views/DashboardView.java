package com.app.views;

import com.app.Application;
import com.app.common.controller.DashboardController;
import com.app.common.infrastructure.router.NhanVienRoute;
import com.app.common.infrastructure.router.QuanLyRoute;
import com.app.utils.ColorUtils;
import com.app.utils.SessionUtils;
import com.app.views.UI.sidebarmenu.SidebarMenu;
import com.app.views.UI.sidebarmenu.SidebarMenuContent;
import com.app.views.UI.sidebarmenu.SidebarMenuItem;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.List;
import java.util.TimerTask;

/**
 *
 * @author InuHa
 */
public class DashboardView extends JPanel {

    public static DashboardView context;

    private SidebarMenu sidebarMenu;

    private SidebarMenuContent sidebarMenuContent;

    public DashboardView() {
        initComponents();
        context = this;
        sidebarMenu = new SidebarMenu();
        sidebarMenuContent = new SidebarMenuContent();

        add(sidebarMenu);
        add(sidebarMenuContent, "grow, push, wrap");
        
        DashboardController.getInstance().setContext(context);
        DashboardController.getInstance().setContent(sidebarMenuContent);

        List<SidebarMenuItem> itemsMenu = SessionUtils.isManager() ? QuanLyRoute.getInstance().getItemSideMenu() : NhanVienRoute.getInstance().getItemSideMenu();


        if(!itemsMenu.isEmpty()) {
            sidebarMenu.setSelected(itemsMenu.get(0).getIndex());
        }


    }

    private void initComponents() {
        this.setBackground(ColorUtils.BACKGROUND_DASHBOARD);
        this.setLayout(new MigLayout("fill, insets 0 0 0 0", "[left]0[grow,fill]", "[top]"));
    }

}
