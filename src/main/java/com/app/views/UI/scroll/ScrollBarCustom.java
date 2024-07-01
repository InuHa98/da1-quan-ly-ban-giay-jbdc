package com.app.views.UI.scroll;

import com.app.utils.ColorUtils;

import javax.swing.*;
import java.awt.*;

public class ScrollBarCustom extends JScrollBar {

    public ScrollBarCustom() {
        setUI(new ModernScrollBarUI());
        setPreferredSize(new Dimension(5, 5));
        setForeground(ColorUtils.PRIMARY_COLOR);
        setOpaque(false);
        setUnitIncrement(20);
    }

}
