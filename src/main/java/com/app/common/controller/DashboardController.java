package com.app.common.controller;

import com.app.Application;
import com.app.views.common.DashboardView;
import com.app.views.components.sidebarmenu.SidebarMenuContent;

import javax.swing.*;
import java.awt.*;

public class DashboardController {

    private DashboardView context;

    private SidebarMenuContent content;

    private static DashboardController instance;

    private DashboardController() {
    }

    public static DashboardController getInstance() {
        if (instance == null) {
            instance = new DashboardController();
        }
        return instance;
    }

    public DashboardView getContext() {
        return context;
    }

    public DashboardController setContext(DashboardView context) {
        this.context = context;
        return this;
    }

    public SidebarMenuContent getContent() {
        return content;
    }

    public DashboardController setContent(SidebarMenuContent content) {
        this.content = content;
        return this;
    }

    public void show(JComponent component) {
        if (this.context == null) {
            System.out.println("Context not found");
            return;
        }

        EventQueue.invokeLater(() -> {
            content.removeAll();
            content.add(component);
            content.revalidate();
            content.repaint();
        });
    }

}
