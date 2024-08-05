package com.app.core.inuha.views.quanly.nhanvien;

import com.app.common.helper.MessageModal;
import com.app.common.helper.MessageToast;
import com.app.common.infrastructure.constants.ErrorConstant;
import com.app.common.infrastructure.exceptions.ServiceResponseException;
import com.app.core.inuha.models.InuhaTaiKhoanModel;
import com.app.core.inuha.services.InuhaTaiKhoanService;
import com.app.utils.ColorUtils;
import com.app.utils.ValidateUtils;
import com.app.views.UI.dialog.LoadingDialog;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import raven.modal.ModalDialog;

/**
 *
 * @author inuHa
 */
public class InuhaChangePasswordNhanVienView extends javax.swing.JPanel {

    private final InuhaTaiKhoanService taiKhoanService = InuhaTaiKhoanService.getInstance();
    
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    
    private final Color currentColor;

    private final InuhaTaiKhoanModel taiKhoan;
    
    /** Creates new form InuhaChangePasswordNhanVienView */
    public InuhaChangePasswordNhanVienView(InuhaTaiKhoanModel taiKhoan) {
	initComponents();
	this.taiKhoan = taiKhoan;
	
	currentColor = lblPassword.getForeground();
	
	txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Vui lòng nhập mật khẩu mới");
        txtConfirmPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Vui lòng xác nhận mật khẩu mới");
	
	KeyAdapter eventSubmit = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
                    handleClickButtonSubmit();
                }
            }
        };
	
	lblDesc.setText("Mật khẩu phải có từ " + ValidateUtils.MIN_LENGTH_PASSWORD + " đến " + ValidateUtils.MAX_LENGTH_PASSWORD + " ký tự");
	
	txtPassword.addKeyListener(eventSubmit);
	txtConfirmPassword.addKeyListener(eventSubmit);
	
	btnSubmit.setBackground(ColorUtils.PRIMARY_COLOR);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblConfirmPassword = new javax.swing.JLabel();
        txtConfirmPassword = new javax.swing.JPasswordField();
        btnSubmit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblDesc = new javax.swing.JLabel();

        lblPassword.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPassword.setText("Mật khẩu mới:");

        lblConfirmPassword.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblConfirmPassword.setText("Nhập lại mật khẩu mới:");

        btnSubmit.setText("Cập nhật mật khẩu");
        btnSubmit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        btnCancel.setText("Huỷ");
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        lblDesc.setText("jLabel3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblConfirmPassword, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtConfirmPassword, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                    .addComponent(lblPassword, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDesc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDesc)
                .addGap(20, 20, 20)
                .addComponent(lblPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblConfirmPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
	handleClickButtonSubmit();
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
	ModalDialog.closeAllModal();
    }//GEN-LAST:event_btnCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JLabel lblConfirmPassword;
    private javax.swing.JLabel lblDesc;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JPasswordField txtConfirmPassword;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables

    private void handleClickButtonSubmit() { 
	String password = txtPassword.getText().trim();
        password = password.replaceAll("\\s+"," ");
	
	String confirmPassword = txtConfirmPassword.getText().trim();
        confirmPassword = confirmPassword.replaceAll("\\s+"," ");
	
	lblPassword.setForeground(ColorUtils.DANGER_COLOR);
        if (password.isEmpty()) { 
            MessageToast.error("Mật khẩu không được bỏ trống");
            return;
        }
	
	if (password.length() < ValidateUtils.MIN_LENGTH_PASSWORD) { 
            MessageToast.error("Mật khẩu phải có ít nhất " + ValidateUtils.MIN_LENGTH_USERNAME + " ký tự");
            return;
        }
		
        if (password.length() > ValidateUtils.MAX_LENGTH_PASSWORD) { 
            MessageToast.error("Mật khẩu không được vượt quá " + ValidateUtils.MAX_LENGTH_USERNAME + " ký tự");
            return;
        }
        lblPassword.setForeground(currentColor);
	
	lblConfirmPassword.setForeground(ColorUtils.DANGER_COLOR);
        if (!password.equals(confirmPassword)) { 
            MessageToast.error("Mật khẩu nhập lại không chính xác");
            return;
        }
        lblConfirmPassword.setForeground(currentColor);
	
	LoadingDialog loadingDialog = new LoadingDialog();
	
	final String fnPassword = password;
	final String fnConfirmPassword = confirmPassword;
	
	executorService.submit(() -> {
            try {
                taiKhoanService.changePassword(taiKhoan, fnPassword, fnConfirmPassword);
                MessageModal.success("Thay đổi mật khẩu thành công.");
		ModalDialog.closeAllModal();
            } catch(ServiceResponseException e) {
                MessageModal.error(e.getMessage());
            } catch(Exception e) {
                MessageModal.error(ErrorConstant.DEFAULT_ERROR);
            } finally {
		loadingDialog.dispose();
	    }
        });

        loadingDialog.setVisible(true);
    }
}
