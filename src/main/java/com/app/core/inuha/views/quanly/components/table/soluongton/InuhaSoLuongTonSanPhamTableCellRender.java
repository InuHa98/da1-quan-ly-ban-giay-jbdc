package com.app.core.inuha.views.quanly.components.table.soluongton;

import com.app.core.inuha.views.quanly.components.table.trangthai.*;
import com.app.utils.ColorUtils;
import com.app.utils.CurrencyUtils;
import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author InuHa
 */
public class InuhaSoLuongTonSanPhamTableCellRender implements TableCellRenderer {
    
    private final TableCellRenderer oldCellRenderer;
    
    public InuhaSoLuongTonSanPhamTableCellRender(JTable table) {
	this.oldCellRenderer = table.getDefaultRenderer(Object.class);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object o, boolean isSeleted, boolean bln1, int row, int column) {
        Component com = oldCellRenderer.getTableCellRendererComponent(table, o, isSeleted, bln1, row, column);
        
	int soLuong = (int) CurrencyUtils.parseNumber(String.valueOf(o));
        InuhaSoLuongTonSanPhamTablePanel cell = new InuhaSoLuongTonSanPhamTablePanel(soLuong) { 
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(ColorUtils.BACKGROUND_GRAY);
                g2.setStroke(new BasicStroke(1));
                //g2.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());
                g2.drawLine(0, getHeight() - 1, getWidth() - 1, getHeight() - 1);
                g2.dispose();
            }
	};
        cell.setBackground(com.getBackground());
        return cell;
    }
}
