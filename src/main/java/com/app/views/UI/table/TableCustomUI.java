package com.app.views.UI.table;


import com.app.views.UI.table.celll.TableCustomCellRender;
import com.app.views.UI.table.celll.TableHeaderCustomCellRender;
import com.app.views.UI.table.celll.BooleanCellRenderer;
import com.app.views.UI.table.celll.TextAreaCellRenderer;
import com.app.utils.ColorUtils;
import com.app.views.UI.scroll.ScrollBarCustomUI;
import com.app.views.UI.table.celll.TableImageCellRender;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 *
 * @author InuHa
 */
public class TableCustomUI {

    private final static Color COLOR_HEADER = ColorUtils.BACKGROUND_TABLE;
    private final static Color COLOR_HEADER_TEXT = ColorUtils.TEXT_TABLE;
    private final static Color COLOR_BACKGROUND = ColorUtils.BACKGROUND_TABLE;
    private final static Color COLOR_GRID_COLOR = ColorUtils.lighten(ColorUtils.BACKGROUND_TABLE, 0.1f);
    private final static Color COLOR_SELECTION_TEXT = ColorUtils.PRIMARY_TEXT;
    private final static Color COLOR_SELECTION_BACKGROUND = ColorUtils.BACKGROUND_HOVER;

    
       
    public static void apply(JScrollPane scroll, TableType type) {

        JTable table = (JTable) scroll.getViewport().getComponent(0);

        table.setFocusable(false);
        table.setBorder(BorderFactory.createEmptyBorder());
        table.setBackground(COLOR_BACKGROUND);
        table.setSelectionForeground(COLOR_SELECTION_TEXT);
        table.setSelectionBackground(COLOR_SELECTION_BACKGROUND);
        table.setGridColor(COLOR_GRID_COLOR);

        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().putClientProperty(FlatClientProperties.STYLE, """
            height:30;
            hoverBackground:null;
            pressedBackground:null;
            separatorColor:null;
            font:bold;
        """);
        table.getTableHeader().setDefaultRenderer(new TableHeaderCustomCellRender(table));
        //table.getTableHeader().setDefaultRenderer(new TableHeaderAlignment(table));

        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        
        table.setRowHeight(40);
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
        table.setShowHorizontalLines(false);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        scroll.setBorder(BorderFactory.createEmptyBorder());
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(ColorUtils.BACKGROUND_GRAY);
                g2.setStroke(new BasicStroke(1));
                //g2.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());
                g2.drawLine(0, getHeight() - 1, getWidth() - 1, getHeight() - 1);
                g2.dispose();
            }
        };
        
        panel.setOpaque(false);
        panel.setBackground(COLOR_HEADER);
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, panel);
        scroll.setOpaque(false);
        scroll.getViewport().setBackground(table.getBackground());
        scroll.getVerticalScrollBar().setUI(new ScrollBarCustomUI());
        scroll.getHorizontalScrollBar().setUI(new ScrollBarCustomUI());
        
        table.getTableHeader().setBackground(table.getBackground());
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
