package com.app.views.UI.table;

import com.app.core.inuha.views.quanly.components.sanpham.table.InuhaTableActionCellEditor;
import com.app.core.inuha.views.quanly.components.sanpham.table.InuhaTableActionCellRender;
import com.app.views.UI.table.celll.TableCustomCellRender;
import com.app.views.UI.table.celll.TableHeaderCustomCellRender;
import com.app.views.UI.table.celll.BooleanCellRenderer;
import com.app.views.UI.table.celll.TextAreaCellRenderer;
import com.app.utils.ColorUtils;
import com.app.views.UI.scroll.ScrollBarCustomUI;
import com.app.views.UI.table.celll.CheckBoxTableHeaderRenderer;
import com.app.views.UI.table.celll.TableActionEvent;
import com.app.views.UI.table.celll.TableHeaderAlignment;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

/**
 *
 * @author InuHa
 */
public class TableCustomUI {

    private final static Color COLOR_HEADER = ColorUtils.BACKGROUND_TABLE;
    private final static Color COLOR_HEADER_TEXT = ColorUtils.TEXT_TABLE;
    private final static Color COLOR_BACKGROUND = ColorUtils.BACKGROUND_TABLE;
    private final static Color COLOR_GRID_COLOR = ColorUtils.BACKGROUND_GRAY;
    private final static Color COLOR_SELECTION_TEXT = ColorUtils.PRIMARY_TEXT;
    private final static Color COLOR_SELECTION_BACKGROUND = ColorUtils.BACKGROUND_TABLE_SELECTION;

    
       
    public static void apply(JScrollPane scroll, TableType type) {

        JTable table = (JTable) scroll.getViewport().getComponent(0);

        table.setFocusable(false);
        table.setBorder(BorderFactory.createEmptyBorder());
        table.setBackground(COLOR_BACKGROUND);
        table.setSelectionForeground(COLOR_SELECTION_TEXT);
        table.setSelectionBackground(COLOR_SELECTION_BACKGROUND);
        table.setGridColor(COLOR_GRID_COLOR);

        //table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().putClientProperty(FlatClientProperties.STYLE, """
            height:30;
            hoverBackground:null;
            pressedBackground:null;
            separatorColor:null;
            font:bold;
        """);
        table.getTableHeader().setDefaultRenderer(new TableHeaderCustomCellRender(table));
        //table.getTableHeader().setDefaultRenderer(new TableHeaderAlignment(table));

        
        
        table.setRowHeight(30);
        HoverIndex hoverRow = new HoverIndex();
        TableCellRenderer cellRender;
        if (type == TableType.DEFAULT) {
            cellRender = new TableCustomCellRender(hoverRow);
        } else {
            cellRender = new TextAreaCellRenderer(hoverRow);
        }
        table.setDefaultRenderer(Object.class, cellRender);
        table.setDefaultRenderer(Boolean.class, new BooleanCellRenderer(hoverRow));
        
        
        table.setShowVerticalLines(true);

        scroll.setBorder(BorderFactory.createEmptyBorder());
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics grphcs) {
                super.paint(grphcs);
                grphcs.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
                grphcs.dispose();
            }
        };

        panel.setBackground(COLOR_HEADER);
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, panel);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.getVerticalScrollBar().setUI(new ScrollBarCustomUI());
        scroll.getHorizontalScrollBar().setUI(new ScrollBarCustomUI());
        table.getTableHeader().setBackground(COLOR_HEADER);
        table.getTableHeader().setForeground(COLOR_HEADER_TEXT);

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

    public static enum TableType {
        MULTI_LINE, DEFAULT
    }

}
