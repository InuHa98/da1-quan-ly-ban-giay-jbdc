/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.app.views.UI.dialog;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.app.Application;
import com.app.views.UI.panel.RoundPanel;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author inuHa
 */
@Setter
@Getter
public class LoadingDialog extends JDialog {

    private String title = "Vui lòng chờ giây lát...";

    public LoadingDialog(Component parent) {
	    setUndecorated(true);
        getRootPane().setBackground(new Color(0, 0, 0, 0));
        getRootPane().putClientProperty("Window.shadow", Boolean.FALSE);
        setBackground(new Color(0, 0, 0, 0));
        getRootPane().setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));

        setContentPane(contentPane());
        setModal(true);
        setSize(parent.getSize());
        setLocationRelativeTo(parent);
    }
    
    private JPanel contentPane() {
        JPanel container = new JPanel() {
            @Override
            protected void paintComponent(Graphics grphcs) {
                Graphics2D g2 = (Graphics2D) grphcs;
                g2.setColor(new Color(255, 255, 255));
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setComposite(AlphaComposite.SrcOver);
                super.paintComponent(grphcs);
            }

            @Override
            public boolean isOpaque() {
                return false;
            }
        };
        container.setLayout(new MigLayout("fill", "[center]", "[center]"));

        JPanel panel = new RoundPanel();
        panel.setLayout(new MigLayout("fill, insets 20, wrap", "[center]", "[center]"));
        JLabel text = new JLabel(title);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setStringPainted(false);

        panel.add(text);
        panel.add(progressBar, "gapy 10");
        container.add(panel);
        return container;
    }

}
