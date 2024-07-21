package com.app.views.UI.table.celll;

import com.app.core.inuha.views.quanly.components.sanpham.table.*;
import com.app.utils.ColorUtils;
import com.app.views.UI.table.HoverIndex;
import com.app.views.UI.table.celll.TableCustomCellRender;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author InuHa
 */
public class TableActionCellRender extends DefaultTableCellRenderer {

    protected HoverIndex hoverRow = new HoverIndex();
    
    public TableActionCellRender(JTable table) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoverRow.setIndex(-1);
                table.repaint();
            }

        });
        table.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row != hoverRow.getIndex()) {
                    hoverRow.setIndex(row);
                    table.repaint();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row != hoverRow.getIndex()) {
                    hoverRow.setIndex(row);
                    table.repaint();
                }
            }
        });
    }

    
}
