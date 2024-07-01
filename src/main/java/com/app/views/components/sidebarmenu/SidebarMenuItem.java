package com.app.views.components.sidebarmenu;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class SidebarMenuItem {

    @Getter
    private int index;

    @Getter
    private String icon;

    @Getter
    private String label;

    @Getter
    private JComponent component;

    @Getter
    private ISidebarMenuButtonCallback callback;

    public SidebarMenuItem(int index, String icon, String label, JComponent component) {
        this.index = index;
        this.icon = icon;
        this.label = label;
        this.component = component;
    }

    public SidebarMenuItem(int index, String icon, String label, ISidebarMenuButtonCallback callback) {
        this.index = index;
        this.icon = icon;
        this.label = label;
        this.callback = callback;
    }

}
