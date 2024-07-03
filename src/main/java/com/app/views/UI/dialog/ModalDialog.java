package com.app.views.UI.dialog;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class ModalDialog extends JDialog {

    public ModalDialog(JComponent content) {
        this(null, content);
    }

    public ModalDialog(String title, JComponent content) {
        super((Frame) null, title, true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setContentPane(content);
        pack();
        setLocationRelativeTo(null);
    }

    public static void closeLatestDialog() {
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        Window activeWindow = manager.getActiveWindow();

        if (activeWindow instanceof Dialog) {
            Dialog dialog = (Dialog) activeWindow;
            dialog.dispose();
        }
    }

    public static void closeAllDialogs() {
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof ModalDialog) {
                ModalDialog dialog = (ModalDialog) window;
                dialog.dispose();
            }
        }
    }
}
