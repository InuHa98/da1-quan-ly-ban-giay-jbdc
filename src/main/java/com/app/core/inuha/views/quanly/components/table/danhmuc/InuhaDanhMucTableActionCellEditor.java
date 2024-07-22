package com.app.core.inuha.views.quanly.components.table.danhmuc;

import com.app.views.UI.table.ITableActionEvent;
import com.app.views.UI.table.celll.TableActionCellEditor;
import java.awt.Component;
import javax.swing.JTable;

/**
 *
 * @author InuHa
 */
public class InuhaDanhMucTableActionCellEditor extends TableActionCellEditor {
    
    public InuhaDanhMucTableActionCellEditor(ITableActionEvent event) {
        super(event);
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
        InuhaDanhMucTableActionPanel actionPanel = new InuhaDanhMucTableActionPanel();
        actionPanel.initEvent(event, row);
        actionPanel.setBackground(jtable.getSelectionBackground());
        return actionPanel;
    }
    
}
