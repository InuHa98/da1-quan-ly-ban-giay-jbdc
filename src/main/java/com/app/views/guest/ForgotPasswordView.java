/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

package com.app.views.guest;

import com.app.common.controller.ApplicationController;
import com.app.common.helper.MessageModal;
import com.app.common.infrastructure.constants.ErrorConstant;
import com.app.common.infrastructure.exceptions.ServiceResponseException;
import com.app.services.NhanVienService;
import com.app.utils.ColorUtils;
import com.app.utils.ComponentUtils;
import com.app.utils.ContextUtils;
import com.app.utils.ResourceUtils;
import com.app.views.UI.dialog.LoadingDialog;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import lombok.Getter;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author inuHa
 */


public class ForgotPasswordView extends javax.swing.JPanel {

    private final NhanVienService nhanVienService = ContextUtils.getBean(NhanVienService.class);

    private String currentEmail = null;

    @Getter
    private static ForgotPasswordView instance;

    /** Creates new form LoginView */
    public ForgotPasswordView() {
        instance = this;
        initComponents();
        setLayout(new MigLayout("fill, insets 20", "[center]", "[center]"));
        pnlForgotPassword.setBackground(ColorUtils.BACKGROUND_PRIMARY);
        lbLogin.setForeground(ColorUtils.PRIMARY_COLOR);

        txtEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Vui lòng nhập email đã đăng ký tài khoản");


        lbEmail.setForeground(ColorUtils.PRIMARY_TEXT);

        currentEmail = lbEmail.getText();

        KeyListener keyEnter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleSubmit();
                }
            }
        };

        txtEmail.addKeyListener(keyEnter);
        txtEmail.requestFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlForgotPassword = new com.app.views.UI.panel.RoundPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        btnSubmit = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        lbLogin = new javax.swing.JLabel();

        pnlForgotPassword.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getStyle() | java.awt.Font.BOLD, jLabel1.getFont().getSize()+10));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Quên mật khẩu!");

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getSize()+1f));
        jLabel2.setForeground(new java.awt.Color(204, 204, 204));
        jLabel2.setText("Vui lòng kiểm tra thư mục spam nếu không nhận được email");

        lbEmail.setFont(lbEmail.getFont().deriveFont(lbEmail.getFont().getStyle() | java.awt.Font.BOLD, lbEmail.getFont().getSize()+2));
        lbEmail.setForeground(new java.awt.Color(204, 204, 204));
        lbEmail.setText("Email");

        btnSubmit.setText("Gửi mã");
        btnSubmit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        jLabel5.setFont(jLabel5.getFont().deriveFont(jLabel5.getFont().getSize()+1f));
        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Đã có tài khoản?");

        lbLogin.setFont(lbLogin.getFont().deriveFont(lbLogin.getFont().getSize()+2f));
        lbLogin.setForeground(new java.awt.Color(255, 102, 0));
        lbLogin.setText("Đăng nhập ngay");
        lbLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbLoginMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlForgotPasswordLayout = new javax.swing.GroupLayout(pnlForgotPassword);
        pnlForgotPassword.setLayout(pnlForgotPasswordLayout);
        pnlForgotPasswordLayout.setHorizontalGroup(
            pnlForgotPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlForgotPasswordLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(pnlForgotPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlForgotPasswordLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtEmail)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        pnlForgotPasswordLayout.setVerticalGroup(
            pnlForgotPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlForgotPasswordLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlForgotPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlForgotPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlForgotPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
        handleSubmit();
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void lbLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbLoginMouseClicked
        // TODO add your handling code here:
        redirectLogin(evt);
    }//GEN-LAST:event_lbLoginMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSubmit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lbEmail;
    private javax.swing.JLabel lbLogin;
    private com.app.views.UI.panel.RoundPanel pnlForgotPassword;
    private javax.swing.JTextField txtEmail;
    // End of variables declaration//GEN-END:variables

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon imageIcon = ResourceUtils.getImageAssets("images/bg-login.jpg");
        g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    private void handleSubmit() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        LoadingDialog loadingDialog = new LoadingDialog(this);

        String email = txtEmail.getText().trim();

        String txtErrorEmail = email.isEmpty() ? currentEmail + " - Vui lòng nhập trường này" : null;

        boolean isErrorEmail = txtErrorEmail != null;

        ComponentUtils.setErrorLabel(lbEmail, isErrorEmail, (txtErrorEmail != null ? txtErrorEmail : currentEmail));

        if (isErrorEmail) {
            return;
        }

        executorService.submit(() -> {
            try {
                nhanVienService.requestForgotPassword(email);
                loadingDialog.dispose();
                MessageModal.confirm("Mã xác nhận", new ConfirmOptForgotPassword(email), null);
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
    
    private void redirectLogin(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            ApplicationController.getInstance().show(new LoginView());
        }
    }
}
