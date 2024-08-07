package com.app.core.inuha.views.quanly.phieugiamgia;

import com.app.core.inuha.views.quanly.InuhaPhieuGiamGiaView;
import com.app.common.helper.MessageToast;
import com.app.common.infrastructure.constants.ErrorConstant;
import com.app.common.infrastructure.exceptions.ServiceResponseException;
import com.app.core.inuha.models.InuhaPhieuGiamGiaModel;
import com.app.core.inuha.services.InuhaPhieuGiamGiaService;
import com.app.utils.ColorUtils;
import com.app.utils.CurrencyUtils;
import com.app.views.UI.combobox.ComboBoxItem;
import com.app.views.UI.dialog.LoadingDialog;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.text.DefaultFormatterFactory;
import raven.datetime.component.date.DatePicker;
import raven.modal.ModalDialog;

/**
 *
 * @author inuHa
 */
public class InuhaAddPhieuGiamGiaView extends javax.swing.JPanel {
    
    private final InuhaPhieuGiamGiaService phieuGiamGiaService = InuhaPhieuGiamGiaService.getInstance();
    
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private DatePicker datePicker = new DatePicker();
    
    private Color currentColor;
    
    private int MAX = 9999;
    
    private InuhaPhieuGiamGiaModel model;
    
    /** Creates new form InuhaAddPhieuGiamGiaView */
    
    public InuhaAddPhieuGiamGiaView(InuhaPhieuGiamGiaModel model) {
	this();
	this.model = model;
	
	txtMa.setText(model.getMa());
	txtTen.setText(model.getTen());
	txtSoLuong.setText(CurrencyUtils.parseTextField(model.getSoLuong()));
	txtHoaDonToiThieu.setText(CurrencyUtils.parseTextField(model.getDonToiThieu()));
	txtGiamToiDa.setText(CurrencyUtils.parseTextField(model.getGiamToiDa()));
	txtGiaTriGiam.setText(CurrencyUtils.parseTextField(model.getGiaTriGiam()));
	if (model.isGiamTheoPhanTram()) {
	    cboKieuGiamGia.setSelectedItem(new ComboBoxItem<>("Giảm theo phần trăm (%)", true));
	} else {
	    cboKieuGiamGia.setSelectedItem(new ComboBoxItem<>("Giảm theo số tiền (vnđ)", false));
	}
	datePicker.setSelectedDateRange(LocalDate.parse(model.getNgayBatDau()), LocalDate.parse(model.getNgayKetThuc()));
	btnSubmit.setText("Cập nhật phiếu giảm giá");
    }
    
    public InuhaAddPhieuGiamGiaView() {
	initComponents();
	
	currentColor = lblMa.getForeground();
	
	txtMa.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tối đa 50 kí tự");
	txtTen.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tối đa 250 kí tự");
	txtSoLuong.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tối đa " + MAX);
	txtHoaDonToiThieu.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "0");
	txtGiamToiDa.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "0");
	txtGiaTriGiam.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "0");
	
	datePicker.setEditor(txtThoiGian);
	datePicker.setDateSelectionMode(DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED);
	datePicker.setSeparator("  tới ngày  ");
	datePicker.setUsePanelOption(true);
	datePicker.setDateSelectionAble(localDate -> !localDate.isBefore(LocalDate.now()));
	datePicker.setCloseAfterSelected(true);
	
	DefaultFormatterFactory formatterFactory = CurrencyUtils.getDefaultFormat();
	txtSoLuong.setFormatterFactory(formatterFactory);
	txtHoaDonToiThieu.setFormatterFactory(formatterFactory);
	txtGiamToiDa.setFormatterFactory(formatterFactory);
	txtGiaTriGiam.setFormatterFactory(formatterFactory);
		
	KeyAdapter eventSubmit = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
                    handleClickButtonSubmit();
                }
            }
        };
		
	txtMa.addKeyListener(eventSubmit);
	txtTen.addKeyListener(eventSubmit);
	txtSoLuong.addKeyListener(eventSubmit);
	txtHoaDonToiThieu.addKeyListener(eventSubmit);
	txtGiamToiDa.addKeyListener(eventSubmit);
	txtGiaTriGiam.addKeyListener(eventSubmit);
	
	cboKieuGiamGia.removeAllItems();
	cboKieuGiamGia.addItem(new ComboBoxItem<>("Giảm theo phần trăm (%)", true));
	cboKieuGiamGia.addItem(new ComboBoxItem<>("Giảm theo số tiền (vnđ)", false));
	
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

        lblMa = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        lblTen = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        lblSoLuong = new javax.swing.JLabel();
        lblHoaDonToiThieu = new javax.swing.JLabel();
        lblGiamToiDa = new javax.swing.JLabel();
        lblThoiGian = new javax.swing.JLabel();
        txtThoiGian = new javax.swing.JFormattedTextField();
        lblKieuGiam = new javax.swing.JLabel();
        cboKieuGiamGia = new javax.swing.JComboBox();
        lblGiaTriGiam = new javax.swing.JLabel();
        btnSubmit = new javax.swing.JButton();
        txtSoLuong = new javax.swing.JFormattedTextField();
        txtGiaTriGiam = new javax.swing.JFormattedTextField();
        txtHoaDonToiThieu = new javax.swing.JFormattedTextField();
        txtGiamToiDa = new javax.swing.JFormattedTextField();
        btnCancel = new javax.swing.JButton();

        lblMa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblMa.setText("Mã khuyến mãi:");

        lblTen.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTen.setText("Tên khuyến mãi:");

        lblSoLuong.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSoLuong.setText("Số lượng mã:");

        lblHoaDonToiThieu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblHoaDonToiThieu.setText("Hoá đơn tối thiểu:");

        lblGiamToiDa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblGiamToiDa.setText("Giảm tối đa:");

        lblThoiGian.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblThoiGian.setText("Thời gian khuyến mãi:");

        lblKieuGiam.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblKieuGiam.setText("Kiểu giảm giá:");

        cboKieuGiamGia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Giảm theo phần trăm" }));

        lblGiaTriGiam.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblGiaTriGiam.setText("Giá trị giảm:");

        btnSubmit.setText("Thêm phiếu giảm giá");
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboKieuGiamGia, 0, 276, Short.MAX_VALUE)
                            .addComponent(lblMa, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMa)
                            .addComponent(lblSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblKieuGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoLuong))
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTen)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblGiaTriGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtGiaTriGiam, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                                        .addComponent(lblHoaDonToiThieu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblTen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtHoaDonToiThieu, javax.swing.GroupLayout.Alignment.LEADING)))
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtGiamToiDa, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblGiamToiDa, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblGiamToiDa, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblMa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTen)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblHoaDonToiThieu)
                            .addComponent(lblSoLuong))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHoaDonToiThieu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGiamToiDa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblKieuGiam)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboKieuGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblThoiGian)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(txtThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblGiaTriGiam)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGiaTriGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
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
    private javax.swing.JComboBox cboKieuGiamGia;
    private javax.swing.JLabel lblGiaTriGiam;
    private javax.swing.JLabel lblGiamToiDa;
    private javax.swing.JLabel lblHoaDonToiThieu;
    private javax.swing.JLabel lblKieuGiam;
    private javax.swing.JLabel lblMa;
    private javax.swing.JLabel lblSoLuong;
    private javax.swing.JLabel lblTen;
    private javax.swing.JLabel lblThoiGian;
    private javax.swing.JFormattedTextField txtGiaTriGiam;
    private javax.swing.JFormattedTextField txtGiamToiDa;
    private javax.swing.JFormattedTextField txtHoaDonToiThieu;
    private javax.swing.JTextField txtMa;
    private javax.swing.JFormattedTextField txtSoLuong;
    private javax.swing.JTextField txtTen;
    private javax.swing.JFormattedTextField txtThoiGian;
    // End of variables declaration//GEN-END:variables

    private void handleClickButtonSubmit() {
        String ma = txtMa.getText().trim();
        ma = ma.replaceAll("\\s+"," ");
        String ten = txtTen.getText().trim();
        ten = ten.replaceAll("\\s+"," ");
	
	String soLuong = txtSoLuong.getText().trim();
	String hoaDonToiThieu = txtHoaDonToiThieu.getText().trim();
	String giamToiDa = txtGiamToiDa.getText().trim();
	String giaTriGiam = txtGiaTriGiam.getText().trim();
	String ngayBatDau = "";
	String ngayKetThuc = "";
	
	LocalDate[] dates = datePicker.getSelectedDateRange();
	if (dates != null) { 
	    ngayBatDau = dates[0].toString();
	    ngayKetThuc = dates[1].toString();
	}
	
	ComboBoxItem<Boolean> kieuGiam = (ComboBoxItem<Boolean>) cboKieuGiamGia.getSelectedItem();
	boolean giamTheoPhanTram = kieuGiam.getValue();
	

	lblMa.setForeground(ColorUtils.DANGER_COLOR);
        if (ma.isEmpty()) { 
            MessageToast.error("Mã phiếu không được bỏ trống");
            return;
        }
        if (ten.length() > 50) { 
            MessageToast.error("Mã phiếu không được vượt quá 50 ký tự");
            return;
        }
        lblMa.setForeground(currentColor);
	
        lblTen.setForeground(ColorUtils.DANGER_COLOR);
        if (ten.isEmpty()) { 
            MessageToast.error("Tên phiếu không được bỏ trống");
            return;
        }
        if (ten.length() > 250) { 
            MessageToast.error("Tên phiếu không được vượt quá 250 ký tự");
            return;
        }
        lblTen.setForeground(currentColor);
	
        lblSoLuong.setForeground(ColorUtils.DANGER_COLOR);
        if (soLuong.isEmpty()) { 
            MessageToast.error("Số lượng không hợp lệ");
            txtSoLuong.requestFocus();
            return;
        }
		
	try {
	    int sl = (int) CurrencyUtils.parseNumber(soLuong);
	    if (sl > MAX) { 
		MessageToast.error("Số lượng không được vượt quá " + MAX);
		txtSoLuong.requestFocus();
		return;
	    }
	} catch (NumberFormatException e) { 
	    MessageToast.error("Số lượng vượt quá giới hạn cho phép");
	    txtSoLuong.requestFocus();
	    return;
	}
        lblSoLuong.setForeground(currentColor);
	
        lblHoaDonToiThieu.setForeground(ColorUtils.DANGER_COLOR);
        if (hoaDonToiThieu.isEmpty()) { 
            MessageToast.error("Hoá đơn tối thiểu không hợp lệ");
            txtHoaDonToiThieu.requestFocus();
            return;
        }
		
	try {
	    CurrencyUtils.parseNumber(hoaDonToiThieu);
	} catch (NumberFormatException e) { 
	    MessageToast.error("Hoá đơn tối thiểu vượt quá giới hạn cho phép");
	    txtHoaDonToiThieu.requestFocus();
	    return;
	}
        lblHoaDonToiThieu.setForeground(currentColor);
	

	lblGiamToiDa.setForeground(ColorUtils.DANGER_COLOR);
        if (giamToiDa.isEmpty()) { 
            MessageToast.error("Giảm tối đa không hợp lệ");
            txtGiamToiDa.requestFocus();
            return;
        }
		
	try {
	    CurrencyUtils.parseNumber(giamToiDa);
	} catch (NumberFormatException e) { 
	    MessageToast.error("Giảm tối đa vượt quá giới hạn cho phép");
	    txtGiamToiDa.requestFocus();
	    return;
	}
        lblGiamToiDa.setForeground(currentColor);
	
	
	lblGiaTriGiam.setForeground(ColorUtils.DANGER_COLOR);
        if (giaTriGiam.isEmpty()) { 
            MessageToast.error("Giá trị giảm không hợp lệ");
            txtGiaTriGiam.requestFocus();
            return;
        }
	
	try {
	    int gtg = (int) CurrencyUtils.parseNumber(giaTriGiam);
	    if (gtg < 1) {
		MessageToast.error("Giá trị giảm phải lớn hơn 0");
		txtGiaTriGiam.requestFocus();
		return;
	    }
	    
	    if (giamTheoPhanTram && gtg > 100) {
		MessageToast.error("Phần trăm tối đa là 100%");
		txtGiaTriGiam.requestFocus();
		return;
	    }
	} catch (NumberFormatException e) { 
	    MessageToast.error("Giá trị giảm vượt quá giới hạn cho phép");
	    txtGiaTriGiam.requestFocus();
	    return;
	}
        lblGiaTriGiam.setForeground(currentColor);

	lblThoiGian.setForeground(ColorUtils.DANGER_COLOR);
        if (ngayBatDau.isEmpty() || ngayKetThuc.isEmpty()) { 
            MessageToast.error("Thời gian diễn ra không hợp lệ");
            txtThoiGian.requestFocus();
            return;
        }
        lblThoiGian.setForeground(currentColor);
	
	if (!giamTheoPhanTram) { 
	    giamToiDa = giaTriGiam;
	}
	
	boolean isEdited = this.model != null;
		
	InuhaPhieuGiamGiaModel phieuGiamGia = new InuhaPhieuGiamGiaModel();
	phieuGiamGia.setMa(ma);
        phieuGiamGia.setTen(ten);
        phieuGiamGia.setSoLuong((int) CurrencyUtils.parseNumber(soLuong));
	phieuGiamGia.setDonToiThieu(CurrencyUtils.parseNumber(hoaDonToiThieu));
	phieuGiamGia.setGiamToiDa(CurrencyUtils.parseNumber(giamToiDa));
	phieuGiamGia.setGiamTheoPhanTram(giamTheoPhanTram);
	phieuGiamGia.setGiaTriGiam(CurrencyUtils.parseNumber(giaTriGiam));
	phieuGiamGia.setNgayBatDau(ngayBatDau);
	phieuGiamGia.setNgayKetThuc(ngayKetThuc);
	
	if (isEdited) { 
            phieuGiamGia.setId(model.getId());
            phieuGiamGia.setTrangThaiXoa(model.isTrangThaiXoa());
        }
		
        LoadingDialog loading = new LoadingDialog();
        executorService.submit(() -> {
	    try {
		if (!isEdited) { 
		    phieuGiamGiaService.insert(phieuGiamGia);
		    MessageToast.success("Thêm mới phiếu giảm giá thành công.");
		    InuhaPhieuGiamGiaView.getInstance().loadDataPage(1);
		} else {
		    phieuGiamGiaService.update(phieuGiamGia);
		    InuhaPhieuGiamGiaView.getInstance().loadDataPage();
		    MessageToast.success("Cập nhật phiếu giảm giá thành công.");
		}
		ModalDialog.closeAllModal();
	    } catch (ServiceResponseException e) {
		e.printStackTrace();
		MessageToast.error(e.getMessage());
	    } catch (Exception e) {
		e.printStackTrace();
		MessageToast.error(ErrorConstant.DEFAULT_ERROR);
	    } finally {
		loading.dispose();
	    }  
        });
        loading.setVisible(true);
    }
}
