package com.app.views.components.sidebarmenu;

import javax.swing.*;
import java.awt.*;

public class SidebarMenuContent extends JPanel {

    public SidebarMenuContent() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 10));
        setOpaque(false);
    }

}
