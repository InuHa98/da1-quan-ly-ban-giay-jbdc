package com.app.views.UI.sidebarmenu;

import com.app.utils.ColorUtils;
import com.app.views.UI.panel.RoundPanel;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author InuHa
 */
public class SidebarMenuContent extends RoundPanel {

    public SidebarMenuContent() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 10));
        this.setBackground(ColorUtils.BACKGROUND_DASHBOARD);
        setOpaque(false);
    }

}
