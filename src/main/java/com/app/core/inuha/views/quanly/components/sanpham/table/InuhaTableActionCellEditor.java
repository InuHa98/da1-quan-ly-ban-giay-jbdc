package com.app.core.inuha.views.quanly.components.sanpham.table;

import com.app.core.inuha.views.quanly.components.sanpham.table.InuhaTableActionPanel;
import com.app.views.UI.table.celll.TableActionEvent;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author InuHa
 */
public class InuhaTableActionCellEditor extends DefaultCellEditor {

    private TableActionEvent event;

    public InuhaTableActionCellEditor(TableActionEvent event) {
        super(new JCheckBox());
        this.event = event;
    }

    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
        InuhaTableActionPanel action = new InuhaTableActionPanel();
        action.initEvent(event, row);
        action.setBackground(jtable.getSelectionBackground());
        return action;
    }
    
}