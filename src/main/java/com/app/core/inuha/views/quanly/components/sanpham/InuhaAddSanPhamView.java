package com.app.core.inuha.views.quanly.components.sanpham;

import com.app.Application;
import com.app.common.helper.MessageModal;
import com.app.common.helper.MessageToast;
import com.app.common.infrastructure.constants.ErrorConstant;
import com.app.common.infrastructure.exceptions.ServiceResponseException;
import com.app.common.infrastructure.session.AvatarUpload;
import com.app.common.infrastructure.session.SessionLogin;
import com.app.core.inuha.models.InuhaSanPhamModel;
import com.app.core.inuha.models.sanpham.InuhaChatLieuModel;
import com.app.core.inuha.models.sanpham.InuhaDanhMucModel;
import com.app.core.inuha.models.sanpham.InuhaDeGiayModel;
import com.app.core.inuha.models.sanpham.InuhaKieuDangModel;
import com.app.core.inuha.models.sanpham.InuhaThuongHieuModel;
import com.app.core.inuha.models.sanpham.InuhaXuatXuModel;
import com.app.core.inuha.services.InuhaChatLieuService;
import com.app.core.inuha.services.InuhaDanhMucService;
import com.app.core.inuha.services.InuhaDeGiayService;
import com.app.core.inuha.services.InuhaKieuDangService;
import com.app.core.inuha.services.InuhaSanPhamService;
import com.app.core.inuha.services.InuhaThuongHieuService;
import com.app.core.inuha.services.InuhaXuatXuService;
import com.app.core.inuha.views.quanly.components.chatlieu.InuhaListChatLieuView;
import com.app.core.inuha.views.quanly.components.danhmuc.InuhaListDanhMucView;
import com.app.core.inuha.views.quanly.components.degiay.InuhaListDeGiayView;
import com.app.core.inuha.views.quanly.components.thuonghieu.InuhaListThuongHieuView;
import com.app.core.inuha.views.quanly.components.xuatxu.InuhaListXuatXuView;
import com.app.core.inuha.views.quanly.components.kieudang.InuhaListKieuDangView;
import com.app.utils.ColorUtils;
import com.app.utils.CurrencyUtils;
import com.app.utils.ProductUtils;
import com.app.utils.ResourceUtils;
import com.app.utils.SessionUtils;
import com.app.views.DashboardView;
import com.app.views.UI.combobox.ComboBoxItem;
import com.app.views.UI.dialog.LoadingDialog;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import jnafilechooser.api.JnaFileChooser;
import raven.modal.ModalDialog;
import raven.modal.component.SimpleModalBorder;

/**
 *
 * @author InuHa
 */
public class InuhaAddSanPhamView extends javax.swing.JPanel {

    private static InuhaAddSanPhamView instance;
    
    private final InuhaSanPhamService sanPhamService = new InuhaSanPhamService();
    
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
        
    private final static InuhaDanhMucService danhMucService = new InuhaDanhMucService();
    
    private final static InuhaThuongHieuService thuongHieuService = new InuhaThuongHieuService();
    
    private final static InuhaXuatXuService xuatXuService = new InuhaXuatXuService();
    
    private final static InuhaKieuDangService kieuDangervice = new InuhaKieuDangService();
    
    private final static InuhaChatLieuService chatLieuService = new InuhaChatLieuService();
    
    private final static InuhaDeGiayService deGiayService = new InuhaDeGiayService();
    
    private List<InuhaDanhMucModel> dataDanhMuc = new ArrayList<>();
    
    private List<InuhaThuongHieuModel> dataThuongHieu = new ArrayList<>();
    
    private List<InuhaXuatXuModel> dataXuatXu = new ArrayList<>();
    
    private List<InuhaKieuDangModel> dataKieuDang = new ArrayList<>();
    
    private List<InuhaChatLieuModel> dataChatLieu = new ArrayList<>();
    
    private List<InuhaDeGiayModel> dataDeGiay = new ArrayList<>();
    
    private final Color currentColor;
    
    public static InuhaAddSanPhamView getInstance() {
        if (instance == null) {
            instance = new InuhaAddSanPhamView();
        }
        return instance;
    }
    
    
    /**
     * Creates new form InuhThemSanPhamView
     */
    public InuhaAddSanPhamView() {
        instance = this;
        initComponents();
        currentColor = lblTen.getForeground();
        
        btnSubmit.setBackground(ColorUtils.BUTTON_PRIMARY);
        
        txtTen.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tối đa 250 ký tự...");
        txtGiaBan.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "VNĐ");

        
        btnCmdDanhMuc.setIcon(ResourceUtils.getSVG("/svg/plus.svg", new Dimension(20, 20)));
        btnCmdThuongHieu.setIcon(ResourceUtils.getSVG("/svg/plus.svg", new Dimension(20, 20)));
        btnCmdXuatXu.setIcon(ResourceUtils.getSVG("/svg/plus.svg", new Dimension(20, 20)));
        btnCmdKieuDang.setIcon(ResourceUtils.getSVG("/svg/plus.svg", new Dimension(20, 20)));
        btnCmdDeGiay.setIcon(ResourceUtils.getSVG("/svg/plus.svg", new Dimension(20, 20)));
        btnCmdChatLieu.setIcon(ResourceUtils.getSVG("/svg/plus.svg", new Dimension(20, 20)));
        btnUploadImage.setIcon(ResourceUtils.getSVG("/svg/file.svg", new Dimension(20, 20)));
        
        txtGiaBan.setFormatterFactory(CurrencyUtils.getDefaultFormatVND());
        
        cboDanhMuc.setModel(new DefaultComboBoxModel<ComboBoxItem<Integer>>());
        cboThuongHieu.setModel(new DefaultComboBoxModel<ComboBoxItem<Integer>>());
        cboXuatXu.setModel(new DefaultComboBoxModel<ComboBoxItem<Integer>>());
        cboKieuDang.setModel(new DefaultComboBoxModel<ComboBoxItem<Integer>>());
        cboChatLieu.setModel(new DefaultComboBoxModel<ComboBoxItem<Integer>>());
        cboDeGiay.setModel(new DefaultComboBoxModel<ComboBoxItem<Integer>>());
        
        LoadingDialog loading = new LoadingDialog();
        executorService.submit(() -> {
            loadDataDanhMuc();
            loadDataThuongHieu();
            loadDataXuatXu();
            loadDataKieuDang();
            loadDataChatLieu();
            loadDataDeGiay();
            loading.dispose();
        });
        loading.setVisible(true);
    }
    
    public void loadDataDanhMuc() { 
        dataDanhMuc = danhMucService.getAll();
        cboDanhMuc.removeAllItems();
       
        if (dataDanhMuc.isEmpty()) { 
            cboDanhMuc.addItem(new ComboBoxItem<>("-- Chưa có mục nào --", -1));
            cboDanhMuc.setEnabled(false);
            return;
        }
        
        cboDanhMuc.setEnabled(true);
        for(InuhaDanhMucModel m: dataDanhMuc) { 
            cboDanhMuc.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
        }
    }

    public void loadDataThuongHieu() { 
        dataThuongHieu = thuongHieuService.getAll();
        cboThuongHieu.removeAllItems();
        
        if (dataThuongHieu.isEmpty()) { 
            cboThuongHieu.addItem(new ComboBoxItem<>("-- Chưa có mục nào --", -1));
            cboThuongHieu.setEnabled(false);
            return;
        }
        
        cboThuongHieu.setEnabled(true);
        
        for(InuhaThuongHieuModel m: dataThuongHieu) { 
            cboThuongHieu.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
        }
    }
    
    public void loadDataXuatXu() { 
        dataXuatXu = xuatXuService.getAll();
        cboXuatXu.removeAllItems();
        
        if (dataXuatXu.isEmpty()) { 
            cboXuatXu.addItem(new ComboBoxItem<>("-- Chưa có mục nào --", -1));
            cboXuatXu.setEnabled(false);
            return;
        }
        
        cboXuatXu.setEnabled(true);
        
        for(InuhaXuatXuModel m: dataXuatXu) { 
            cboXuatXu.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
        }
    }
    
    public void loadDataKieuDang() { 
        dataKieuDang = kieuDangervice.getAll();
        cboKieuDang.removeAllItems();
        
        if (dataKieuDang.isEmpty()) { 
            cboKieuDang.addItem(new ComboBoxItem<>("-- Chưa có mục nào --", -1));
            cboKieuDang.setEnabled(false);
            return;
        }
        
        cboKieuDang.setEnabled(true);
        
        for(InuhaKieuDangModel m: dataKieuDang) { 
            cboKieuDang.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
        }
    }
    
    public void loadDataChatLieu() { 
        dataChatLieu = chatLieuService.getAll();
        cboChatLieu.removeAllItems();
        
        if (dataChatLieu.isEmpty()) { 
            cboChatLieu.addItem(new ComboBoxItem<>("-- Chưa có mục nào --", -1));
            cboChatLieu.setEnabled(false);
            return;
        }
        
        cboChatLieu.setEnabled(true);
        
        for(InuhaChatLieuModel m: dataChatLieu) { 
            cboChatLieu.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
        }
    }
    
    public void loadDataDeGiay() { 
        dataDeGiay = deGiayService.getAll();
        cboDeGiay.removeAllItems();
        
        if (dataDeGiay.isEmpty()) { 
            cboDeGiay.addItem(new ComboBoxItem<>("-- Chưa có mục nào --", -1));
            cboDeGiay.setEnabled(false);
            return;
        }
        
        cboDeGiay.setEnabled(true);
        
        for(InuhaDeGiayModel m: dataDeGiay) { 
            cboDeGiay.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
        }
    }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupTrangThai = new javax.swing.ButtonGroup();
        roundPanel1 = new com.app.views.UI.panel.RoundPanel();
        lblTen = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        lblGia = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        rdoDangBan = new javax.swing.JRadioButton();
        rdoNgungBan = new javax.swing.JRadioButton();
        lblMoTa = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        txtGiaBan = new javax.swing.JFormattedTextField();
        roundPanel2 = new com.app.views.UI.panel.RoundPanel();
        lblDeGiay = new javax.swing.JLabel();
        cboDeGiay = new javax.swing.JComboBox();
        btnCmdDeGiay = new javax.swing.JButton();
        roundPanel3 = new com.app.views.UI.panel.RoundPanel();
        lblHinhAnh = new javax.swing.JLabel();
        splitLine3 = new com.app.views.UI.label.SplitLine();
        btnUploadImage = new javax.swing.JButton();
        lblDataAnh = new javax.swing.JLabel();
        btnSubmit = new javax.swing.JButton();
        roundPanel4 = new com.app.views.UI.panel.RoundPanel();
        lblDanhMuc = new javax.swing.JLabel();
        cboDanhMuc = new javax.swing.JComboBox();
        lblThuongHieu = new javax.swing.JLabel();
        cboThuongHieu = new javax.swing.JComboBox();
        cboXuatXu = new javax.swing.JComboBox();
        lblXuatXu = new javax.swing.JLabel();
        cboKieuDang = new javax.swing.JComboBox();
        lblKieuDang = new javax.swing.JLabel();
        lblChatLieu = new javax.swing.JLabel();
        cboChatLieu = new javax.swing.JComboBox();
        btnCmdDanhMuc = new javax.swing.JButton();
        btnCmdXuatXu = new javax.swing.JButton();
        btnCmdKieuDang = new javax.swing.JButton();
        btnCmdChatLieu = new javax.swing.JButton();
        btnCmdThuongHieu = new javax.swing.JButton();

        lblTen.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTen.setText("Tên sản phẩm:");

        lblGia.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblGia.setText("Giá bán:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Trạng thái:");

        btnGroupTrangThai.add(rdoDangBan);
        rdoDangBan.setSelected(true);
        rdoDangBan.setText("Đang bán");

        btnGroupTrangThai.add(rdoNgungBan);
        rdoNgungBan.setText("Ngừng bán");

        lblMoTa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblMoTa.setText("Mô tả:");

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane1.setViewportView(txtMoTa);

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel1Layout.createSequentialGroup()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addContainerGap(10, Short.MAX_VALUE)
                        .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rdoDangBan)
                        .addGap(18, 18, 18)
                        .addComponent(rdoNgungBan))
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundPanel1Layout.createSequentialGroup()
                                .addComponent(lblGia)
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addGap(103, 103, 103))
                            .addComponent(jScrollPane1)
                            .addComponent(txtTen, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundPanel1Layout.createSequentialGroup()
                                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblTen, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblMoTa, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(10, 10, 10))
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(lblTen)
                .addGap(10, 10, 10)
                .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGia)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdoDangBan)
                        .addComponent(rdoNgungBan)))
                .addGap(27, 27, 27)
                .addComponent(lblMoTa)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        lblDeGiay.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDeGiay.setText("Đế giày:");

        cboDeGiay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDeGiayActionPerformed(evt);
            }
        });

        btnCmdDeGiay.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCmdDeGiay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCmdDeGiayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addComponent(cboDeGiay, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCmdDeGiay, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblDeGiay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblDeGiay)
                .addGap(10, 10, 10)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboDeGiay)
                    .addComponent(btnCmdDeGiay, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        lblHinhAnh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblHinhAnh.setText("Hình ảnh");

        javax.swing.GroupLayout splitLine3Layout = new javax.swing.GroupLayout(splitLine3);
        splitLine3.setLayout(splitLine3Layout);
        splitLine3Layout.setHorizontalGroup(
            splitLine3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        splitLine3Layout.setVerticalGroup(
            splitLine3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 9, Short.MAX_VALUE)
        );

        btnUploadImage.setText("Chọn ảnh");
        btnUploadImage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUploadImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadImageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel3Layout = new javax.swing.GroupLayout(roundPanel3);
        roundPanel3.setLayout(roundPanel3Layout);
        roundPanel3Layout.setHorizontalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(roundPanel3Layout.createSequentialGroup()
                .addGroup(roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(splitLine3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(roundPanel3Layout.createSequentialGroup()
                        .addGroup(roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundPanel3Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(lblDataAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(roundPanel3Layout.createSequentialGroup()
                                .addGap(71, 71, 71)
                                .addComponent(btnUploadImage, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 6, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roundPanel3Layout.setVerticalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(splitLine3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDataAnh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnUploadImage)
                .addGap(20, 20, 20))
        );

        btnSubmit.setText("Thêm ngay");
        btnSubmit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        lblDanhMuc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDanhMuc.setText("Danh mục:");

        cboDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDanhMucActionPerformed(evt);
            }
        });

        lblThuongHieu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblThuongHieu.setText("Thương hiệu:");

        cboThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboThuongHieuActionPerformed(evt);
            }
        });

        cboXuatXu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboXuatXuActionPerformed(evt);
            }
        });

        lblXuatXu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblXuatXu.setText("Xuất xứ:");

        cboKieuDang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKieuDangActionPerformed(evt);
            }
        });

        lblKieuDang.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblKieuDang.setText("Kiểu dáng:");

        lblChatLieu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblChatLieu.setText("Chất liệu:");

        cboChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChatLieuActionPerformed(evt);
            }
        });

        btnCmdDanhMuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCmdDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCmdDanhMucActionPerformed(evt);
            }
        });

        btnCmdXuatXu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCmdXuatXu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCmdXuatXuActionPerformed(evt);
            }
        });

        btnCmdKieuDang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCmdKieuDang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCmdKieuDangActionPerformed(evt);
            }
        });

        btnCmdChatLieu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCmdChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCmdChatLieuActionPerformed(evt);
            }
        });

        btnCmdThuongHieu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCmdThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCmdThuongHieuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel4Layout = new javax.swing.GroupLayout(roundPanel4);
        roundPanel4.setLayout(roundPanel4Layout);
        roundPanel4Layout.setHorizontalGroup(
            roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblChatLieu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDanhMuc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblThuongHieu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblXuatXu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblKieuDang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundPanel4Layout.createSequentialGroup()
                        .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(roundPanel4Layout.createSequentialGroup()
                                .addComponent(cboXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCmdXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel4Layout.createSequentialGroup()
                                    .addComponent(cboChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCmdChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(roundPanel4Layout.createSequentialGroup()
                                    .addComponent(cboKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCmdKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(cboDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(roundPanel4Layout.createSequentialGroup()
                                .addComponent(cboThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCmdThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCmdDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        roundPanel4Layout.setVerticalGroup(
            roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblDanhMuc)
                .addGap(10, 10, 10)
                .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboDanhMuc, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(btnCmdDanhMuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(lblThuongHieu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCmdThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblXuatXu)
                .addGap(10, 10, 10)
                .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboXuatXu, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(btnCmdXuatXu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(lblKieuDang)
                .addGap(10, 10, 10)
                .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboKieuDang, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(btnCmdKieuDang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(lblChatLieu)
                .addGap(10, 10, 10)
                .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboChatLieu, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(btnCmdChatLieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(roundPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(roundPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(roundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(roundPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(roundPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(btnSubmit, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDanhMucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboDanhMucActionPerformed

    private void cboThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboThuongHieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboThuongHieuActionPerformed

    private void cboXuatXuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboXuatXuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboXuatXuActionPerformed

    private void cboKieuDangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKieuDangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboKieuDangActionPerformed

    private void cboDeGiayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDeGiayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboDeGiayActionPerformed

    private void cboChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChatLieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboChatLieuActionPerformed

    private void btnCmdDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCmdDanhMucActionPerformed
        // TODO add your handling code here:
        handleClickButtonDanhMuc();
    }//GEN-LAST:event_btnCmdDanhMucActionPerformed

    private void btnCmdThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCmdThuongHieuActionPerformed
        // TODO add your handling code here:
        handleClickButtonThuongHieu();
    }//GEN-LAST:event_btnCmdThuongHieuActionPerformed

    private void btnCmdXuatXuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCmdXuatXuActionPerformed
        // TODO add your handling code here:
        handleClickButtonXuatXu();
    }//GEN-LAST:event_btnCmdXuatXuActionPerformed

    private void btnCmdKieuDangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCmdKieuDangActionPerformed
        // TODO add your handling code here:
        handleClickButtonKieuDang();
    }//GEN-LAST:event_btnCmdKieuDangActionPerformed

    private void btnCmdChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCmdChatLieuActionPerformed
        // TODO add your handling code here:
        handleClickButtonChatLieu();
    }//GEN-LAST:event_btnCmdChatLieuActionPerformed

    private void btnCmdDeGiayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCmdDeGiayActionPerformed
        // TODO add your handling code here:
        handleClickButtonDeGiay();
    }//GEN-LAST:event_btnCmdDeGiayActionPerformed

    private void btnUploadImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadImageActionPerformed
        // TODO add your handling code here:
        handleClickButtonImage();
    }//GEN-LAST:event_btnUploadImageActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
        handleClickButtonSubmit();
    }//GEN-LAST:event_btnSubmitActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCmdChatLieu;
    private javax.swing.JButton btnCmdDanhMuc;
    private javax.swing.JButton btnCmdDeGiay;
    private javax.swing.JButton btnCmdKieuDang;
    private javax.swing.JButton btnCmdThuongHieu;
    private javax.swing.JButton btnCmdXuatXu;
    private javax.swing.ButtonGroup btnGroupTrangThai;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnUploadImage;
    private javax.swing.JComboBox cboChatLieu;
    private javax.swing.JComboBox cboDanhMuc;
    private javax.swing.JComboBox cboDeGiay;
    private javax.swing.JComboBox cboKieuDang;
    private javax.swing.JComboBox cboThuongHieu;
    private javax.swing.JComboBox cboXuatXu;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblChatLieu;
    private javax.swing.JLabel lblDanhMuc;
    private javax.swing.JLabel lblDataAnh;
    private javax.swing.JLabel lblDeGiay;
    private javax.swing.JLabel lblGia;
    private javax.swing.JLabel lblHinhAnh;
    private javax.swing.JLabel lblKieuDang;
    private javax.swing.JLabel lblMoTa;
    private javax.swing.JLabel lblTen;
    private javax.swing.JLabel lblThuongHieu;
    private javax.swing.JLabel lblXuatXu;
    private javax.swing.JRadioButton rdoDangBan;
    private javax.swing.JRadioButton rdoNgungBan;
    private com.app.views.UI.panel.RoundPanel roundPanel1;
    private com.app.views.UI.panel.RoundPanel roundPanel2;
    private com.app.views.UI.panel.RoundPanel roundPanel3;
    private com.app.views.UI.panel.RoundPanel roundPanel4;
    private com.app.views.UI.label.SplitLine splitLine3;
    private javax.swing.JFormattedTextField txtGiaBan;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables

    private void handleClickButtonDanhMuc() {
        ModalDialog.showModal(this, new SimpleModalBorder(new InuhaListDanhMucView(), "Danh mục sản phẩm"));
    }

    private void handleClickButtonThuongHieu() {
        ModalDialog.showModal(this, new SimpleModalBorder(new InuhaListThuongHieuView(), "Thương hiệu sản phẩm"));
    }

    private void handleClickButtonXuatXu() {
        ModalDialog.showModal(this, new SimpleModalBorder(new InuhaListXuatXuView(), "Xuất xứ sản phẩm"));
    }

    private void handleClickButtonKieuDang() {
        ModalDialog.showModal(this, new SimpleModalBorder(new InuhaListKieuDangView(), "Kiểu dáng sản phẩm"));
    }

    private void handleClickButtonChatLieu() {
        ModalDialog.showModal(this, new SimpleModalBorder(new InuhaListChatLieuView(), "Chất liệu sản phẩm"));
    }

    private void handleClickButtonDeGiay() {
        ModalDialog.showModal(this, new SimpleModalBorder(new InuhaListDeGiayView(), "Đế giày sản phẩm"));
    }

    private void handleClickButtonImage() {
        JnaFileChooser ch = new JnaFileChooser();
        ch.addFilter("Image", "png", "jpg", "jpeg");
        boolean act = ch.showOpenDialog(SwingUtilities.getWindowAncestor(this));
        if (act) {
            File file = ch.getSelectedFile();

            LoadingDialog loading = new LoadingDialog();

            executorService.submit(() -> {
                try {
                    ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
                    Image image = imageIcon.getImage().getScaledInstance(lblDataAnh.getWidth(), lblDataAnh.getHeight(), Image.SCALE_SMOOTH);
                    loading.dispose();
                    lblDataAnh.setIcon(new ImageIcon(image));
                    lblDataAnh.putClientProperty("path-image", file.getAbsolutePath());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    loading.dispose();
                    MessageToast.error(ErrorConstant.DEFAULT_ERROR);
                } 
            });
            loading.setVisible(true);

        }

    }

    private void handleClickButtonSubmit() {
        String ten = txtTen.getText().trim();
        String gia = txtGiaBan.getText().trim();
        boolean trangThai = rdoDangBan.isSelected();
        String moTa = txtMoTa.getText().trim();
        ComboBoxItem<Integer> danhMuc = (ComboBoxItem<Integer>) cboDanhMuc.getSelectedItem();
        ComboBoxItem<Integer> thuongHieu = (ComboBoxItem<Integer>) cboThuongHieu.getSelectedItem();
        ComboBoxItem<Integer> xuatXu = (ComboBoxItem<Integer>) cboXuatXu.getSelectedItem();
        ComboBoxItem<Integer> kieuDang = (ComboBoxItem<Integer>) cboKieuDang.getSelectedItem();
        ComboBoxItem<Integer> chatLieu = (ComboBoxItem<Integer>) cboChatLieu.getSelectedItem();
        ComboBoxItem<Integer> deGiay = (ComboBoxItem<Integer>) cboDeGiay.getSelectedItem();
        String selectHinhAnh = (String) lblDataAnh.getClientProperty("path-image");
                
        ten = ten.replaceAll("\\s+"," ");
        
        lblTen.setForeground(ColorUtils.DANGER_COLOR);
        if (ten.isEmpty()) { 
            MessageToast.error("Tên sản phẩm không được bỏ trống");
            txtTen.requestFocus();
            return;
        }
        if (ten.length() > 250) { 
            MessageToast.error("Tên sản phẩm không được vượt quá 250 ký tự");
            txtTen.requestFocus();
            return;
        }
        lblTen.setForeground(currentColor);
        
        lblGia.setForeground(ColorUtils.DANGER_COLOR);
        if (gia.isEmpty()) { 
            MessageToast.error("Tên giá bán không được bỏ trống");
            txtGiaBan.requestFocus();
            return;
        }
        lblGia.setForeground(currentColor);

        lblMoTa.setForeground(ColorUtils.DANGER_COLOR);
        if (moTa.length() > 2000) { 
            MessageToast.error("Mô tả không được vượt quá 2000 ký tự");
            txtMoTa.requestFocus();
            return;
        }
        lblMoTa.setForeground(currentColor);
        
        lblDanhMuc.setForeground(ColorUtils.DANGER_COLOR);
        if (danhMuc.getValue() < 0) { 
            MessageToast.error("Vui lòng chọn một danh mục");
            cboDanhMuc.requestFocus();
            return;
        }
        lblDanhMuc.setForeground(currentColor);
        
        lblThuongHieu.setForeground(ColorUtils.DANGER_COLOR);
        if (thuongHieu.getValue() < 0) { 
            MessageToast.error("Vui lòng chọn một thương hiệu");
            cboThuongHieu.requestFocus();
            return;
        }
        lblThuongHieu.setForeground(currentColor);
        
        lblXuatXu.setForeground(ColorUtils.DANGER_COLOR);
        if (xuatXu.getValue() < 0) { 
            MessageToast.error("Vui lòng chọn một xuất xứ");
            cboXuatXu.requestFocus();
            return;
        }
        lblXuatXu.setForeground(currentColor);
        
        lblKieuDang.setForeground(ColorUtils.DANGER_COLOR);
        if (kieuDang.getValue() < 0) { 
            MessageToast.error("Vui lòng chọn một kiểu dáng");
            cboKieuDang.requestFocus();
            return;
        }
        lblKieuDang.setForeground(currentColor);
        
        lblChatLieu.setForeground(ColorUtils.DANGER_COLOR);
        if (chatLieu.getValue() < 0) { 
            MessageToast.error("Vui lòng chọn một chất liệu");
            cboChatLieu.requestFocus();
            return;
        }
        lblChatLieu.setForeground(currentColor);
        
        lblDeGiay.setForeground(ColorUtils.DANGER_COLOR);
        if (deGiay.getValue() < 0) { 
            MessageToast.error("Vui lòng chọn một đế giày");
            cboDeGiay.requestFocus();
            return;
        }
        lblDeGiay.setForeground(currentColor);
        
        lblHinhAnh.setForeground(ColorUtils.DANGER_COLOR);
        if (selectHinhAnh == null || selectHinhAnh.isEmpty()) { 
            MessageToast.error("Vui lòng chọn một hình ảnh");
            btnUploadImage.requestFocus();
            return;
        }
        lblHinhAnh.setForeground(currentColor);
        
        String ma = ProductUtils.generateCode();

        InuhaDanhMucModel danhMucModel = new InuhaDanhMucModel();
        danhMucModel.setId(danhMuc.getValue());
        
        InuhaThuongHieuModel thuongHieuModel = new InuhaThuongHieuModel();
        thuongHieuModel.setId(thuongHieu.getValue());
        
        InuhaXuatXuModel xuatXuModel = new InuhaXuatXuModel();
        xuatXuModel.setId(xuatXu.getValue());
        
        InuhaKieuDangModel kieuDangModel = new InuhaKieuDangModel();
        kieuDangModel.setId(kieuDang.getValue());
        
        InuhaChatLieuModel chatLieuModel = new InuhaChatLieuModel();
        chatLieuModel.setId(chatLieu.getValue());
        
        InuhaDeGiayModel deGiayModel = new InuhaDeGiayModel();
        deGiayModel.setId(deGiay.getValue());
        
        InuhaSanPhamModel model = new InuhaSanPhamModel();
        model.setMa(ma);
        model.setTen(ten);
        model.setGiaBan(Double.parseDouble(String.valueOf(CurrencyUtils.parseNumber(gia))));
        model.setTrangThai(trangThai);
        model.setMoTa(moTa);
        model.setDanhMuc(danhMucModel);
        model.setThuongHieu(thuongHieuModel);
        model.setXuatXu(xuatXuModel);
        model.setKieuDang(kieuDangModel);
        model.setChatLieu(chatLieuModel);
        model.setDeGiay(deGiayModel);
        
        LoadingDialog loading = new LoadingDialog();
        executorService.submit(() -> {
            if (MessageModal.confirmInfo("Thêm mới sản phẩm này?")) {

                executorService.submit(() -> {

                    String pathImage = ProductUtils.uploadImage(ma, selectHinhAnh);
                    MessageToast.clearAll();

                    if (pathImage == null) {
                        loading.dispose();
                        MessageToast.error("Không thể upload hình ảnh!");
                        return;
                    }
                    
                    model.setHinhAnh(pathImage);
                    
                    try {
                        sanPhamService.insert(model);
                        loading.dispose();
                        MessageToast.success("Thếm mới sản phẩm thành công.");
                        ModalDialog.closeAllModal();
                    } catch (ServiceResponseException e) {
                        loading.dispose();
                        MessageToast.error(e.getMessage());
                    } catch (Exception e) {
                        loading.dispose();
                        MessageToast.error(ErrorConstant.DEFAULT_ERROR);
                    }
                });

                loading.setVisible(true);
            }
        });
        
    }
}
