package com.app.core.inuha.views.quanly.sanpham.thuoctinhsanpham.thuonghieu;

import com.app.Application;
import com.app.common.helper.MessageModal;
import com.app.common.helper.MessageToast;
import com.app.common.infrastructure.constants.ErrorConstant;
import com.app.common.infrastructure.exceptions.ServiceResponseException;
import com.app.core.inuha.models.sanpham.InuhaThuongHieuModel;
import com.app.core.inuha.services.InuhaThuongHieuService;
import com.app.core.inuha.views.quanly.InuhaSanPhamView;
import com.app.core.inuha.views.quanly.sanpham.InuhaAddSanPhamView;
import com.app.utils.ColorUtils;
import com.app.utils.ValidateUtils;
import com.app.views.UI.dialog.LoadingDialog;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JPanel;
import raven.modal.ModalDialog;

/**
 *
 * @author InuHa
 */
public class InuhaAddThuongHieuView extends JPanel {

    private final InuhaThuongHieuService thuongHieuService = InuhaThuongHieuService.getInstance();
    
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    
    private final Color currentColor;
    
    /**
     * Creates new form InuhaThemThuongHieuView
     */
    public InuhaAddThuongHieuView() {
        initComponents();
        currentColor = lblTen.getForeground();
        txtTen.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tối đa 250 ký tự...");
        btnSubmit.setBackground(ColorUtils.BUTTON_PRIMARY);
        btnSubmit.setForeground(Color.WHITE);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSubmit = new javax.swing.JButton();
        lblTen = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        btnCancel = new javax.swing.JButton();

        setOpaque(false);

        btnSubmit.setText("Thêm mới");
        btnSubmit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        lblTen.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTen.setText("Tên thương hiệu:");

        txtTen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTenKeyPressed(evt);
            }
        });

        btnCancel.setText("Huỷ bỏ");
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(lblTen)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtTen, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(40, 40, 40))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblTen)
                .addGap(10, 10, 10)
                .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        handleClickButtoncancel();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
        handleClickButtonSubmit();
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void txtTenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { 
            handleClickButtonSubmit();
        }
    }//GEN-LAST:event_txtTenKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JLabel lblTen;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables

    private void handleClickButtoncancel() {
        ModalDialog.closeModal(InuhaListThuongHieuView.MODAL_ID_CREATE);
    }

    private void handleClickButtonSubmit() {
        String ten = txtTen.getText().trim();
        ten = ten.replaceAll("\\s+"," ");
        
        lblTen.setForeground(ColorUtils.DANGER_COLOR);
        
        if (ten.isEmpty()) { 
            MessageToast.error("Tên thương hiệu không được bỏ trống");
            return;
        }
        
        if (ten.length() > 250) { 
            MessageToast.error("Tên thương hiệu không được vượt quá 250 ký tự");
            return;
        }
        
        if (ValidateUtils.isSpecialCharacters(ten)) {
            MessageToast.error("Tên thương hiệu không được chứa ký tự đặc biệt");
            return;
        }
        
        lblTen.setForeground(currentColor);
        
        InuhaThuongHieuModel model = new InuhaThuongHieuModel();
        model.setTen(ten);
        
        LoadingDialog loadingDialog = new LoadingDialog();
        
        executorService.submit(() -> {
            try {
                thuongHieuService.insert(model);
                loadingDialog.dispose();
                InuhaSanPhamView.getInstance().loadDataThuongHieu();
                InuhaAddSanPhamView.getInstance().loadDataThuongHieu();
                InuhaListThuongHieuView.getInstance().loadDataPage(1);
                ModalDialog.closeModal(InuhaListThuongHieuView.MODAL_ID_CREATE);
                MessageToast.success("Thêm mới thương hiệu thành công!");
            } catch (ServiceResponseException e) {
                loadingDialog.dispose();
                MessageToast.error(e.getMessage());
                lblTen.setForeground(ColorUtils.DANGER_COLOR);
            } catch (Exception e) {
                loadingDialog.dispose();
                MessageModal.error(ErrorConstant.DEFAULT_ERROR);
            }            
        });
        loadingDialog.setVisible(true);
    }
}
