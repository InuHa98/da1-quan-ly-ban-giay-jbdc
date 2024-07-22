package com.app.core.inuha.views.quanly.components.table.danhmuc;

import com.app.utils.ColorUtils;
import com.app.views.UI.table.celll.TableActionCellRender;
import java.awt.Component;
import javax.swing.JTable;

/**
 *
 * @author InuHa
 */
public class InuhaDanhMucTableActionCellRender extends TableActionCellRender {
    
    public InuhaDanhMucTableActionCellRender(JTable table) {
        super(table);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object o, boolean isSeleted, boolean bln1, int row, int column) {
        Component com = super.getTableCellRendererComponent(table, o, isSeleted, bln1, row, column);
        
        InuhaDanhMucTableActionPanel actionPanel = new InuhaDanhMucTableActionPanel();
        
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
