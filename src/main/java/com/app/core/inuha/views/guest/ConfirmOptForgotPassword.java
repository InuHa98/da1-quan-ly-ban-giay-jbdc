/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

package com.app.core.inuha.views.guest;

import com.app.common.controller.ApplicationController;
import com.app.common.helper.MessageModal;
import com.app.common.helper.MessageToast;
import com.app.common.infrastructure.constants.ErrorConstant;
import com.app.common.infrastructure.exceptions.ServiceResponseException;
import com.app.core.inuha.services.InuhaTaiKhoanService;
import com.app.views.UI.dialog.LoadingDialog;
import com.formdev.flatlaf.FlatClientProperties;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author inuHa
 */
public class ConfirmOptForgotPassword extends javax.swing.JPanel {

    private final InuhaTaiKhoanService nhanVienService = InuhaTaiKhoanService.getInstance();

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private String email = null;

    public ConfirmOptForgotPassword(String email) {
        this();
        this.email = email;
    }

    public void setupComponents() {
        setOpaque(false);

        txtOTP.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Vui lòng nhập mã OTP");


        KeyListener keyEnter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleOk();
                }
            }
        };

        txtOTP.addKeyListener(keyEnter);
        txtOTP.requestFocus();
    }

    /** Creates new form ConfirmOptForgotPassword */
    public ConfirmOptForgotPassword() {
        initComponents();
        setupComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtOTP = new javax.swing.JTextField();
        btnCancel = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();

        setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getSize()+1f));
        jLabel1.setText("Vui lòng kiểm tra thư mục Spam nếu không nhận được email.");

        txtOTP.setMaximumSize(new java.awt.Dimension(355, 35));

        btnCancel.setText("Huỷ");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnOk.setText("Tiếp tục");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOk))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtOTP, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(42, 42, 42))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtOTP, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        // TODO add your handling code here:
        handleOk();
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        handleCancel();
    }//GEN-LAST:event_btnCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField txtOTP;
    // End of variables declaration//GEN-END:variables


    public String getOTP() {
        return txtOTP.getText().trim();
    }

    private void handleOk() {
        String otp = getOTP();

        if (otp.isEmpty()) {
            MessageToast.error("Vui lòng nhập mã OTP");
            txtOTP.requestFocus();
            return;
        }

        if (otp.length() != 6) {
            MessageToast.error("Mã OTP không hợp lệ");
            txtOTP.requestFocus();
            return;
        }

        LoadingDialog loading = new LoadingDialog(ForgotPasswordView.getInstance());

        executorService.submit(() -> {
            try {
                nhanVienService.validOtp(this.email, otp);
                loading.dispose();
                MessageModal.closeAll();
                MessageToast.clearAll();
                MessageToast.success("Xác nhận mã OTP thành công. Vui lòng xác nhận mật khẩu mới");
                ApplicationController.getInstance().show(new ChangePasswordForgotPasswordView(this.email, otp));
            } catch(ServiceResponseException e) {
                loading.dispose();
                MessageModal.error(e.getMessage());
            } catch(Exception e) {
                e.printStackTrace();
                loading.dispose();
                MessageModal.error(ErrorConstant.DEFAULT_ERROR);
            }
            txtOTP.requestFocus();
        });

        loading.setVisible(true);
    }

    private void handleCancel() {
        MessageModal.close();
    }

}
