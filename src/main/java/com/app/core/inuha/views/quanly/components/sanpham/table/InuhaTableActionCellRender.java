package com.app.core.inuha.views.quanly.components.sanpham.table;

import com.app.utils.ColorUtils;
import com.app.views.UI.table.celll.TableActionCellRender;
import java.awt.Component;
import javax.swing.JTable;

/**
 *
 * @author InuHa
 */
public class InuhaTableActionCellRender extends TableActionCellRender {

   
    public InuhaTableActionCellRender(JTable table) {
        super(table);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object o, boolean isSeleted, boolean bln1, int row, int column) {
        Component com = super.getTableCellRendererComponent(table, o, isSeleted, bln1, row, column);
        InuhaTableActionPanel action = new InuhaTableActionPanel();
        
        if (isSeleted == false) {
            if (row == hoverRow.getIndex()) {
                action.setBackground(ColorUtils.BACKGROUND_HOVER);
            } else {
                action.setBackground(ColorUtils.BACKGROUND_TABLE);
            }
        } else {
            action.setBackground(table.getSelectionBackground());
        }
        
        return action;
    }
    
}
