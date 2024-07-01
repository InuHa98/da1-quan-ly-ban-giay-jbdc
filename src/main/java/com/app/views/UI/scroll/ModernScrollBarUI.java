package com.app.views.UI.scroll;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class ModernScrollBarUI extends BasicScrollBarUI {

    private int thumbSize = 80;

    @Override
    protected Dimension getMaximumThumbSize() {
        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            return new Dimension(0, thumbSize);
        } else {
            return new Dimension(thumbSize, 0);
        }
    }

    @Override
    protected Dimension getMinimumThumbSize() {
        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            return new Dimension(0, thumbSize);
        } else {
            return new Dimension(thumbSize, 0);
        }
    }

    @Override
    protected JButton createIncreaseButton(int i) {
        return new ScrollBarButton();
    }

    @Override
    protected JButton createDecreaseButton(int i) {
        return new ScrollBarButton();
    }

    @Override
    protected void paintTrack(Graphics grphcs, JComponent jc, Rectangle rctngl) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setColor(new Color(50, 50, 50));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        int x = rctngl.x;
        int y = rctngl.y;
        int width = rctngl.width;
        int height = rctngl.height;
        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            y += 4;
            height -= 10;
        } else {
            x += 4;
            width -= 10;
        }
        g2.fillRoundRect(x, y, width, height, 1, 1);
        g2.setComposite(AlphaComposite.SrcOver);
    }

    @Override
    protected void paintThumb(Graphics grphcs, JComponent jc, Rectangle rctngl) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int x = rctngl.x;
        int y = rctngl.y;
        int width = rctngl.width;
        int height = rctngl.height;
        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            y += 8;
            height -= 16;
        } else {
            x += 8;
            width -= 16;
        }
        g2.setColor(scrollbar.getForeground());
        g2.fillRoundRect(x, y, width, height, 1, 1);
    }

    @Override
    protected void layoutVScrollbar(JScrollBar sb) {
        super.layoutVScrollbar(sb);
        thumbSize = calculateThumbSize(sb);
    }

    @Override
    protected void layoutHScrollbar(JScrollBar sb) {
        super.layoutHScrollbar(sb);
        thumbSize = calculateThumbSize(sb);
    }

    @Override
    public void layoutContainer(Container scrollbarContainer) {
        thumbSize = calculateThumbSize((JScrollBar) scrollbarContainer);
        scrollbarContainer.revalidate();
        scrollbarContainer.repaint();
        super.layoutContainer(scrollbarContainer);
    }

    private int calculateThumbSize(JScrollBar sb) {
        int visibleAmount = sb.getVisibleAmount();
        int minimum = sb.getMinimum();
        int maximum = sb.getMaximum();
        int extent = maximum - minimum - visibleAmount;
        int thumbSize = extent <= 0 ? 0 : (visibleAmount * (sb.getOrientation() == JScrollBar.VERTICAL ? sb.getHeight() : sb.getWidth()) ) / (maximum - minimum);
        if (thumbSize < 20) {
            thumbSize = 20;
        }
        return thumbSize;
    }

    private class ScrollBarButton extends JButton {

        public ScrollBarButton() {
            setBorder(BorderFactory.createEmptyBorder());
        }

        @Override
        public void paint(Graphics grphcs) {
        }
    }
}
