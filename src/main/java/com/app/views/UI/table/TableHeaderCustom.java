/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.app.views.UI.table;

import com.app.utils.ColorUtils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author inuHa
 */
public class TableHeaderCustom extends DefaultTableCellRenderer {

    public TableHeaderCustom() {
        setPreferredSize(new Dimension(0, 35));
        setBackground(ColorUtils.BACKGROUND_TABLE);
        setForeground(new Color(200, 200, 200));
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        grphcs.setColor(new Color(100, 100, 100));
        grphcs.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
    }
    
}
