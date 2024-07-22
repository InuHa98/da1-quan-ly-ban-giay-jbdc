package com.app.views.UI.table.celll;

import com.app.utils.ColorUtils;
import com.app.views.UI.table.HoverIndex;
import com.app.views.UI.table.ITableActionPanel;
import com.app.views.UI.table.TableActionPanel;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
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

    @Override
    public Component getTableCellRendererComponent(JTable table, Object o, boolean isSeleted, boolean bln1, int row, int column) {
        Component com = super.getTableCellRendererComponent(table, o, isSeleted, bln1, row, column);
        
        TableActionPanel actionPanel = new TableActionPanel();
        
        if (isSeleted == false) {
            if (row == hoverRow.getIndex()) {
                actionPanel.setBackground(ColorUtils.BACKGROUND_HOVER);
            } else {
                actionPanel.setBackground(table.getBackground());
            }
        } else {
            actionPanel.setBackground(table.getSelectionBackground());
        }
        
        return actionPanel;
    }
}
