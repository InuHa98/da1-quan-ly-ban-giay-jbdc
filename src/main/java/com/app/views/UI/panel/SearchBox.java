package com.app.views.UI.panel;

import com.app.utils.ColorUtils;
import com.app.utils.ResourceUtils;
import com.app.views.UI.panel.RoundPanel;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatLineBorder;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author inuHa
 */
public class SearchBox extends RoundPanel {

    /** Creates new form SearchBox2 */
    public SearchBox() {
	initComponents();
	setBackground(ColorUtils.INPUT_PRIMARY);
	setBorder(new FlatLineBorder(new Insets(2, 5, 2, 5), ColorUtils.BORDER, 1, 20));
	txtKeyword.setBorder(new EmptyBorder(0, 0, 0, 0));
        txtKeyword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập từ khoá...");
	txtKeyword.setOpaque(false);
	lblIcon.setIcon(ResourceUtils.getSVG("/svg/search.svg", new Dimension(24, 24)));
       
    }
    
    public void setPlaceholder(String text) {
        txtKeyword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, text);
    }
    
    public JTextField getKeyword() {    
        return txtKeyword;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIcon = new javax.swing.JLabel();
        txtKeyword = new javax.swing.JTextField();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtKeyword, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(txtKeyword, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblIcon;
    private javax.swing.JTextField txtKeyword;
    // End of variables declaration//GEN-END:variables

}
