/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

package com.app.core.inuha.views.guest;

import com.app.common.controller.ApplicationController;
import com.app.common.helper.MessageModal;
import com.app.common.infrastructure.session.SessionLogin;
import com.app.common.infrastructure.constants.ErrorConstant;
import com.app.common.infrastructure.exceptions.ServiceResponseException;
import com.app.core.inuha.models.InuhaTaiKhoanModel;
import com.app.core.inuha.services.InuhaTaiKhoanService;
import com.app.utils.ColorUtils;
import com.app.utils.ComponentUtils;
import com.app.utils.ResourceUtils;
import com.app.utils.ThemeUtils;
import com.app.views.UI.dialog.LoadingDialog;
import com.app.views.DashboardView;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author inuHa
 */


public class LoginView extends javax.swing.JPanel {

    private final InuhaTaiKhoanService nhanVienService = InuhaTaiKhoanService.getInstance();

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private String currentUsername = null;

    private String currentPassword = null;

    public LoginView(String username) {
        this();
        txtUsername.setText(username);
        txtPassword.requestFocus();
       
    }

    /** Creates new form LoginView */
    public LoginView() {
        initComponents();
        setLayout(new MigLayout("fill, insets 20", "[center]", "[center]"));
	lblTitle.setForeground(ColorUtils.PRIMARY_COLOR);
        lbForgotPassword.setForeground(ColorUtils.PRIMARY_COLOR);

        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Vui lòng nhập email hoặc tên người dùng");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Vui lòng nhập mật khẩu");

        txtUsername.setText("admin");
        txtPassword.setText("123");

        currentUsername = lbUsername.getText();
        currentPassword = lbPassword.getText();

        KeyListener keyEnter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleSubmit();
                }
            }
        };

        txtUsername.addKeyListener(keyEnter);
        txtPassword.addKeyListener(keyEnter);

        txtUsername.requestFocus();
        
        lblTheme.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblTheme.setIcon(ComponentUtils.resizeImageByWidth(ResourceUtils.getImageAssets("/sidemenu/theme.png"), 20));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlLogin = new com.app.views.UI.panel.RoundPanel();
        lblTitle = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lbPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        btnSubmit = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        lbForgotPassword = new javax.swing.JLabel();
        lblTheme = new javax.swing.JLabel();

        lblTitle.setFont(lblTitle.getFont().deriveFont(lblTitle.getFont().getStyle() | java.awt.Font.BOLD, lblTitle.getFont().getSize()+10));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTitle.setText("I-SHOES");

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getSize()+1f));
        jLabel2.setText("Vui lòng đăng nhập để sử dụng các chức năng");

        lbUsername.setFont(lbUsername.getFont().deriveFont(lbUsername.getFont().getStyle() | java.awt.Font.BOLD, lbUsername.getFont().getSize()+2));
        lbUsername.setText("Username");

        lbPassword.setFont(lbPassword.getFont().deriveFont(lbPassword.getFont().getStyle() | java.awt.Font.BOLD, lbPassword.getFont().getSize()+2));
        lbPassword.setText("Password");

        btnSubmit.setText("Đăng nhập");
        btnSubmit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        jLabel5.setFont(jLabel5.getFont().deriveFont(jLabel5.getFont().getSize()+1f));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Quên mật khẩu?");

        lbForgotPassword.setFont(lbForgotPassword.getFont().deriveFont(lbForgotPassword.getFont().getSize()+2f));
        lbForgotPassword.setForeground(new java.awt.Color(255, 102, 0));
        lbForgotPassword.setText("Tìm mật khẩu");
        lbForgotPassword.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbForgotPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbForgotPasswordMouseClicked(evt);
            }
        });

        lblTheme.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblThemeMouseClicked(evt);
            }
        });
        lblTheme.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lblThemeKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout pnlLoginLayout = new javax.swing.GroupLayout(pnlLogin);
        pnlLogin.setLayout(pnlLoginLayout);
        pnlLoginLayout.setHorizontalGroup(
            pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoginLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtUsername)
                    .addComponent(lbPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPassword)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlLoginLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbForgotPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(42, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLoginLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTheme, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        pnlLoginLayout.setVerticalGroup(
            pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoginLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbUsername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbForgotPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTheme, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
	handleSubmit();
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void lbForgotPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbForgotPasswordMouseClicked
        // TODO add your handling code here:
	redirectForgotPassword(evt);
    }//GEN-LAST:event_lbForgotPasswordMouseClicked

    private void lblThemeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblThemeKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_lblThemeKeyPressed

    private void lblThemeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThemeMouseClicked
        // TODO add your handling code here:
        if (SwingUtilities.isLeftMouseButton(evt)) { 
            ThemeUtils.switchTheme();
        }
    }//GEN-LAST:event_lblThemeMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSubmit;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lbForgotPassword;
    private javax.swing.JLabel lbPassword;
    private javax.swing.JLabel lbUsername;
    private javax.swing.JLabel lblTheme;
    private javax.swing.JLabel lblTitle;
    private com.app.views.UI.panel.RoundPanel pnlLogin;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon imageIcon = ResourceUtils.getImageAssets("images/bg-login.jpg");
        g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    private void handleSubmit() {
        LoadingDialog loadingDialog = new LoadingDialog(this);

        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        String txtErrorUsername = username.isEmpty() ? currentUsername + " - Vui lòng nhập trường này" : null;
        String txtErrorPassword = password.isEmpty() ? currentPassword + " - Vui lòng nhập trường này" : null;

        boolean isErrorUsername = txtErrorUsername != null;
        boolean isErrorPassword = txtErrorPassword != null;

        ComponentUtils.setErrorLabel(lbUsername, isErrorUsername, (txtErrorUsername != null ? txtErrorUsername : currentUsername));
        ComponentUtils.setErrorLabel(lbPassword, isErrorPassword, (txtErrorPassword != null ? txtErrorPassword : currentPassword));

        if (isErrorUsername || isErrorPassword) {
            return;
        }

        executorService.submit(() -> {
            try {
                InuhaTaiKhoanModel user = nhanVienService.login(username, password);
                SessionLogin.getInstance().create(username, password, user);
                ApplicationController.getInstance().show(new DashboardView());
		loadingDialog.dispose();
            } catch(ServiceResponseException e) {
                loadingDialog.dispose();
                MessageModal.error(e.getMessage());
            } catch(Exception e) {
                e.printStackTrace();
                loadingDialog.dispose();
                MessageModal.error(ErrorConstant.DEFAULT_ERROR);
            }
        });

        loadingDialog.setVisible(true);
    }

    private void redirectForgotPassword(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            ApplicationController.getInstance().show(new ForgotPasswordView());
        }
    }
}
