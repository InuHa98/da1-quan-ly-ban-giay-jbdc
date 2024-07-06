package com.app.views.UI.table;

import com.app.utils.ColorUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 *
 * @author InuHa
 */
public class TableCustomCellRender extends DefaultTableCellRenderer {

    private final HoverIndex hoverRow;

    public TableCustomCellRender(HoverIndex hoverRow) {
        this.hoverRow = hoverRow;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        if (isSelected) {
            com.setBackground(table.getSelectionBackground());
        } else {
            if (row == hoverRow.getIndex()) {
                com.setBackground(ColorUtils.BACKGROUND_HOVER);
            } else {
                com.setBackground(ColorUtils.BACKGROUND_TABLE);
            }
        }
        com.setFont(table.getFont());
        return com;
    }

}
