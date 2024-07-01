/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.app.views.UI;

import com.app.utils.ColorUtils;
import com.app.views.UI.table.TableHeaderCustom;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author inuHa
 */
public class TableCustom extends JTable {

    public TableCustom() {
	setFocusable(false);
	setBorder(BorderFactory.createEmptyBorder());
	setBackground(ColorUtils.BACKGROUND_TABLE);
	setForeground(ColorUtils.PRIMARY_TEXT);
        setSelectionForeground(ColorUtils.PRIMARY_TEXT);
        setSelectionBackground(ColorUtils.BACKGROUND_TABLE_SELECTION);
        getTableHeader().setDefaultRenderer(new TableHeaderCustom());
	getTableHeader().setReorderingAllowed(false);
        setRowHeight(47);
        setShowHorizontalLines(false);
	//setShowVerticalLines(false);
        setGridColor(ColorUtils.BACKGROUND_TABLE_SELECTION);
	//setOpaque(false);
    }
    
    @Override
    public Component prepareRenderer(TableCellRenderer tcr, int i, int i1) {
        Component com = super.prepareRenderer(tcr, i, i1);
        if (!isCellSelected(i, i1)) {
            com.setBackground(ColorUtils.BACKGROUND_TABLE);
        }
        return com;
    }
    
}
