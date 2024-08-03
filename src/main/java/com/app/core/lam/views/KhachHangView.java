/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.app.core.lam.views;
import com.app.Application;
import com.app.common.helper.MessageModal;
import com.app.common.helper.MessageToast;
import com.app.common.infrastructure.constants.ErrorConstant;
import com.app.common.infrastructure.exceptions.ServiceResponseException;
import com.app.core.lam.models.KhachHangModels;
import com.app.core.lam.models.LichSuModels;
import com.app.core.lam.repositories.KhachHangRepositories;
import com.app.utils.NumberPhoneUtils;
import com.app.utils.ResourceUtils;
import com.app.views.UI.dialog.LoadingDialog;
import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public final class KhachHangView extends javax.swing.JPanel {

    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    private final KhachHangRepositories repo = new KhachHangRepositories();

    private DefaultTableModel dtm = new DefaultTableModel();

    private ArrayList<KhachHangModels> listKH = new ArrayList<>();

    private DefaultTableModel dtmLS = new DefaultTableModel();

    private ArrayList<LichSuModels> listLS = new ArrayList<>();

    public void detailTable(int row) {

        KhachHangModels kh = listKH.get(row);

        txtTenKH.setText(kh.getTenKH());

        txtSoDienThoai.setText(kh.getSoDienThoai());

        if (kh.isGioiTinh()) {
            rbnNam.setSelected(true);
        } else {
            rbnNu.setSelected(true);
        }

        txtDiaChi.setText(kh.getDiaChi());

        if (kh.isTrangThaiXoa()) {
            rbnDaMua.setSelected(true);
        } else {
            rbnChuaMua.setSelected(true);
        }

    }

    public void showData(ArrayList<KhachHangModels> listKH) {
        dtm.setRowCount(0);
        for (KhachHangModels kh : listKH) {
            dtm.addRow(new Object[]{
                kh.getIdKH(), kh.getTenKH(), kh.getSoDienThoai(), kh.isGioiTinh() ? "Nam" : "Nữ", kh.getDiaChi(), kh.isTrangThaiXoa() ? "Đã Mua" : "Chưa Mua"
            });
        }
    }

    public KhachHangModels getFormData() {
        String tenKH = txtTenKH.getText();
        String soDienThoai = txtSoDienThoai.getText();
        boolean gioiTinh = rbnNam.isSelected();
        boolean trangThaiXoa = !rbnChuaMua.isSelected();
        KhachHangModels kh = new KhachHangModels(WIDTH, tenKH, soDienThoai, gioiTinh, tenKH, trangThaiXoa);
        return kh;
    }

    public void detailTableLS(int row) {

        LichSuModels ls = listLS.get(row);

        txtTenKH.setText(ls.getTenKH());

        txtSoDienThoai.setText(ls.getSoDienThoai());

        if (ls.isGioiTinh()) {
            rbnNam.setSelected(true);
        } else {
            rbnNu.setSelected(true);
        }

        txtDiaChi.setText(ls.getDiaChi());

        if (ls.isTrangThaiXoa()) {
            rbnDaMua.setSelected(true);
        } else {
            rbnChuaMua.setSelected(false);
        }
    }

    public void showDataLS(ArrayList<LichSuModels> listLS) {
        dtmLS.setRowCount(0);
        for (LichSuModels ls : listLS) {
            if (ls.isTrangThaiXoa()) {
                dtmLS.addRow(new Object[]{
                    ls.getIdKH(), ls.getTenKH(), ls.getSoDienThoai(), ls.isGioiTinh() ? "Nam" : "Nữ", ls.getDiaChi(), ls.isTrangThaiXoa() ? "Đã Mua" : "Chưa Mua"
                });
            }
        }
    }

    public LichSuModels getFormDataLS() {
        String tenKH = txtTenKH.getText();
        String soDienThoai = txtSoDienThoai.getText();
        boolean gioiTinh = rbnNam.isSelected();
        boolean trangThaiXoa = rbnDaMua.isSelected();
        LichSuModels ls = new LichSuModels(WIDTH, tenKH, soDienThoai, gioiTinh, tenKH, trangThaiXoa);
        return ls;
    }

    public KhachHangView() {
        initComponents();
        
        btnThem.setIcon(ResourceUtils.getSVG("/svg/plus.svg", new Dimension(20, 20)));
        btnSua.setIcon(ResourceUtils.getSVG("/svg/edit.svg", new Dimension(20, 20)));
        btnXoa.setIcon(ResourceUtils.getSVG("/svg/trash.svg", new Dimension(20, 20)));
        btnLamMoi.setIcon(ResourceUtils.getSVG("/svg/reload.svg", new Dimension(20, 20)));
        
        listKH = repo.getKH();
        dtm = (DefaultTableModel) tblKhachHang.getModel();
        showData(listKH);
        
        

        listLS = repo.getLS();
        dtmLS = (DefaultTableModel) tblLichSuGD.getModel();
        showDataLS(listLS);
        txtSoDienThoai.setFormatterFactory(NumberPhoneUtils.getDefaultFormat());

    }

    private boolean isCheckSDT(String soDienThoai) {
        for (KhachHangModels kh : listKH) {
            if (kh.getSoDienThoai().equals(soDienThoai)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates new form KhachHangView
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        rbnNam = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        rbnNu = new javax.swing.JRadioButton();
        rbnChuaMua = new javax.swing.JRadioButton();
        btnThem = new javax.swing.JButton();
        rbnDaMua = new javax.swing.JRadioButton();
        btnSua = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        txtTenKH = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        btnSearch = new javax.swing.JButton();
        txtTimKiemKH = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        txtTimKiemLichSu = new javax.swing.JTextField();
        btnSearchLichSu = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblLichSuGD = new javax.swing.JTable();
        btnSuaLS = new javax.swing.JButton();
        btnXoaLS = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        txtSoDienThoai = new javax.swing.JFormattedTextField();

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Quản Lý Khách Hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 204, 0))); // NOI18N
        jPanel3.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 204, 0));
        jLabel2.setText("Thiết lập thông tin khách hàng");

        jLabel4.setText("Tên Khách Hàng");

        jLabel5.setText("Số Điện Thoại");

        jLabel6.setText("Giới Tính");

        buttonGroup2.add(rbnNam);
        rbnNam.setText("Nam");

        jLabel8.setText("Trạng Thái ");

        buttonGroup2.add(rbnNu);
        rbnNu.setText("Nữ");

        buttonGroup1.add(rbnChuaMua);
        rbnChuaMua.setText("Chưa Mua");

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbnDaMua);
        rbnDaMua.setText("Đã Mua");

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnLamMoi.setText("Làm Mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        jLabel10.setText("Địa Chỉ");

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin khách hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 153, 0))); // NOI18N

        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "#", "Tên Khách Hàng", "Số Điện Thoại", "Giới Tính", "Địa Chỉ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhachHang.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblKhachHang.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKhachHang);
        if (tblKhachHang.getColumnModel().getColumnCount() > 0) {
            tblKhachHang.getColumnModel().getColumn(0).setMinWidth(40);
            tblKhachHang.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        jLabel12.setText("Tìm kiếm theo số điện thoại.");

        btnSearch.setText("Tìm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(0, 98, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtTimKiemKH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSearch)
                        .addComponent(txtTimKiemKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 874, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Danh sách khách hàng", jPanel1);

        jLabel11.setText("Tìm kiếm theo số điện thoại.");

        btnSearchLichSu.setText("Tìm");
        btnSearchLichSu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchLichSuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtTimKiemLichSu, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearchLichSu)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTimKiemLichSu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSearchLichSu)))
                .addGap(12, 12, 12))
        );

        tblLichSuGD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "#", "Tên Khách Hàng", "Số Điện Thoại", "Giới Tính", "Địa Chỉ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLichSuGD.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblLichSuGD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLichSuGDMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblLichSuGD);
        if (tblLichSuGD.getColumnModel().getColumnCount() > 0) {
            tblLichSuGD.getColumnModel().getColumn(0).setMinWidth(40);
            tblLichSuGD.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        btnSuaLS.setText("Chỉnh Sửa");
        btnSuaLS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaLSActionPerformed(evt);
            }
        });

        btnXoaLS.setText("Xóa");
        btnXoaLS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaLSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnXoaLS)
                        .addGap(31, 31, 31)
                        .addComponent(btnSuaLS))
                    .addComponent(jSeparator1))
                .addGap(31, 31, 31))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 874, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSuaLS)
                            .addComponent(btnXoaLS))
                        .addGap(2, 2, 2)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Lịch sử giao dịch khách hàng", jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTenKH, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                                    .addComponent(txtDiaChi)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addComponent(rbnDaMua)
                                        .addGap(18, 18, 18)
                                        .addComponent(rbnChuaMua))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(rbnNam)
                                        .addGap(18, 18, 18)
                                        .addComponent(rbnNu))
                                    .addComponent(txtSoDienThoai))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(btnThem)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnSua)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnXoa)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnLamMoi))
                                    .addComponent(jSeparator2)))
                            .addComponent(jLabel2))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(rbnNam)
                            .addComponent(rbnNu))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(rbnChuaMua)
                            .addComponent(rbnDaMua)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSua)
                            .addComponent(btnXoa)
                            .addComponent(btnLamMoi)
                            .addComponent(btnThem))
                        .addGap(2, 2, 2)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)))
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        LoadingDialog loading = new LoadingDialog();
        executorService.submit(() -> {
            try {
                KhachHangModels newKH = getFormData();
                String soDienThoai = newKH.getSoDienThoai();
                if (soDienThoai == null) {
                    MessageModal.warning("Vui lòng điền số điện thoại!");
                    return;
                }
                if (isCheckSDT(newKH.getSoDienThoai())) {
                    MessageModal.error("Số điện thoại đã tồn tại!");
                    return;
                }
                String tenKH = newKH.getTenKH();
                String diaChi = newKH.getDiaChi();

                if (tenKH == null || tenKH.isEmpty() || diaChi == null || diaChi.isEmpty()) {
                    MessageModal.warning("Vui lòng nhập đầy đủ thông tin trên hệ thống !");
                    return;
                }
                boolean check = repo.addKhachHang(newKH);

                if (check) {
                    MessageModal.success("Thêm thông tin khách hàng thành công!");
                    listKH.add(newKH);
                    showData(listKH);
                    if (newKH.isTrangThaiXoa()) {
                        listLS.add(new LichSuModels(newKH.getIdKH(), newKH.getTenKH(), newKH.getSoDienThoai(), newKH.isGioiTinh(), newKH.getDiaChi(), true));
                        showDataLS(listLS);
                    }
                } else {
                    MessageModal.error("Không thể thêm thông tin khách hàng!");
                }
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
    }//GEN-LAST:event_btnThemActionPerformed

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        // TODO add your handling code here:
        int row = tblKhachHang.getSelectedRow();
        if (row >= 0) {
            detailTable(row);
        } else {
            MessageModal.warning("Vui lòng chọn một dòng hợp lệ!!");
        }
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        LoadingDialog loading = new LoadingDialog();
        executorService.submit(() -> {
            try {
                int row = tblKhachHang.getSelectedRow();
                KhachHangModels newKH = listKH.get(row);
                boolean check = repo.deleteKhachHang(newKH.getSoDienThoai());
                if (row >= 0) {
                    if (check) {
                        MessageModal.success("Xóa thông tin khách hàng thành công!");
                        listKH.remove(row);
                        showData(listKH);
                        if (newKH.isTrangThaiXoa()) {
                            listLS.remove(new LichSuModels(newKH.getIdKH(), newKH.getTenKH(), newKH.getSoDienThoai(), newKH.isGioiTinh(), newKH.getDiaChi(), true));
                            showData(listKH);
                        }
                    } else {
                        MessageModal.error("Xóa thông tin khách hàng không thành công!");
                    }
                } else {
                    MessageModal.warning("Vui lòng hãy chọn vào một khách hàng");
                }
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
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed

        LoadingDialog loading = new LoadingDialog();
        executorService.submit(() -> {
            try {
                int row = tblKhachHang.getSelectedRow();
                if (row >= 0) {
                    KhachHangModels newKH = listKH.get(row);
                    newKH.setTenKH(txtTenKH.getText());
                    newKH.setSoDienThoai(txtSoDienThoai.getText());
                    newKH.setGioiTinh(rbnNam.isSelected());
                    newKH.setDiaChi(txtDiaChi.getText());
                    newKH.setTrangThaiXoa(rbnDaMua.isSelected());

                    boolean check = repo.updateKhachHang(newKH);
                    if (check) {
                        MessageModal.success("Sửa thông tin khách hàng thành công!");

                        showData(listKH);

                        boolean found = false;

                        for (int i = 0; i < listLS.size(); i++) {
                            LichSuModels ls = listLS.get(i);
                            if (ls.getSoDienThoai().equals(newKH.getSoDienThoai())) {

                                ls.setTenKH(newKH.getTenKH());
                                ls.setGioiTinh(newKH.isGioiTinh());
                                ls.setDiaChi(newKH.getDiaChi());
                                ls.setTrangThaiXoa(newKH.isTrangThaiXoa());
                                found = true;
                                break;
                            }
                        }

                        if (!found && newKH.isTrangThaiXoa()) {
                            listLS.add(new LichSuModels(newKH.getIdKH(), newKH.getTenKH(), newKH.getSoDienThoai(), newKH.isGioiTinh(), newKH.getDiaChi(), true));
                        }

                        if (found && !newKH.isTrangThaiXoa()) {
                            listLS.removeIf(ls -> ls.getSoDienThoai().equals(newKH.getSoDienThoai()));
                        }

                        showDataLS(listLS);
                    } else {
                        MessageModal.error("Sửa thông tin khách hàng không thành công!");
                    }
                } else {
                    MessageModal.warning("Vui lòng chọn 1 khách hàng để sửa!");
                }
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
    }//GEN-LAST:event_btnSuaActionPerformed

    private void tblLichSuGDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLichSuGDMouseClicked
        // TODO add your handling code here:
        int row = tblLichSuGD.getSelectedRow();
        if (row >= 0) {
            detailTable(row);
        } else {
            MessageModal.warning("Vui lòng chọn một dòng hợp lệ!!");
        }
    }//GEN-LAST:event_tblLichSuGDMouseClicked

    private void btnSearchLichSuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchLichSuActionPerformed
        // TODO add your handling code here:
        LoadingDialog loading = new LoadingDialog();
        executorService.submit(() -> {
            try {
                String soDienThoai = txtTimKiemLichSu.getText().trim();

                ArrayList<LichSuModels> listSearchLichSu = new ArrayList<>();

                if (!soDienThoai.isEmpty()) {
                    for (LichSuModels ls : listLS) {

                        if (ls.getSoDienThoai().equals(soDienThoai)) {
                            listSearchLichSu.add(ls);
                        }
                    }

                    if (!listSearchLichSu.isEmpty()) {
                        showDataLS(listSearchLichSu);
                    } else {
                        MessageModal.warning("Không tìm thấy khách hàng với số điện thoại: " + soDienThoai);
                        showDataLS(listLS);
                    }
                } else {
                    MessageModal.warning("Vui lòng nhập số điện thoại để tìm kiếm!");
                    showDataLS(listLS);
                }
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
    }//GEN-LAST:event_btnSearchLichSuActionPerformed

    private void btnXoaLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaLSActionPerformed
        // TODO add your handling code here:
        LoadingDialog loading = new LoadingDialog();
        executorService.submit(() -> {
            try {
                int row = tblLichSuGD.getSelectedRow();
                LichSuModels ls = listLS.get(row);
                boolean check = repo.deleteLichSu(ls.getSoDienThoai());
                if (row >= 0) {
                    if (check) {
                        MessageModal.success("Xóa thông tin khách hàng thành công!");
                        listLS.remove(row);
                        listKH.remove(row);
                        showDataLS(listLS);
                        showData(listKH);
                    } else {
                        MessageModal.error("Xóa thông tin khách hàng không thành công!");
                    }
                } else {
                    MessageModal.warning("Vui lòng hãy chọn vào một khách hàng");
                }
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

    }//GEN-LAST:event_btnXoaLSActionPerformed

    private void btnSuaLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaLSActionPerformed
        // TODO add your handling code here:
        LoadingDialog loading = new LoadingDialog();
        executorService.submit(() -> {
            try {
                int row = tblLichSuGD.getSelectedRow();
                if (row >= 0) {
                    LichSuModels newLS = listLS.get(row);
                    newLS.setTenKH(txtTenKH.getText());
                    newLS.setSoDienThoai(txtSoDienThoai.getText());
                    newLS.setGioiTinh(rbnNam.isSelected());
                    newLS.setDiaChi(txtDiaChi.getText());
                    newLS.setTrangThaiXoa(rbnDaMua.isSelected());

                    boolean check = repo.updateLichSu(newLS);
                    if (check) {
                        MessageModal.success("Sửa thông tin khách hàng thành công!");

                        showDataLS(listLS);

                        boolean found = false;

                        for (int i = 0; i < listKH.size(); i++) {
                            KhachHangModels kh = listKH.get(i);
                            if (kh.getSoDienThoai().equals(newLS.getSoDienThoai())) {

                                kh.setTenKH(newLS.getTenKH());
                                kh.setGioiTinh(newLS.isGioiTinh());
                                kh.setDiaChi(newLS.getDiaChi());
                                kh.setTrangThaiXoa(newLS.isTrangThaiXoa());
                                found = true;
                                break;
                            }
                        }

                        if (!found && newLS.isTrangThaiXoa()) {
                            listKH.add(new KhachHangModels(newLS.getIdKH(), newLS.getTenKH(), newLS.getSoDienThoai(), newLS.isGioiTinh(), newLS.getDiaChi(), true));
                        }

                        if (found && !newLS.isTrangThaiXoa()) {
                            listKH.removeIf(kh -> kh.getSoDienThoai().equals(newLS.getSoDienThoai()));
                        }

                        showData(listKH);
                    } else {
                        MessageModal.error("Sửa thông tin khách hàng không thành công!");
                    }
                } else {
                    MessageModal.warning("Vui lòng chọn 1 khách hàng để sửa!");
                }
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
    }//GEN-LAST:event_btnSuaLSActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        LoadingDialog loading = new LoadingDialog();
        executorService.submit(() -> {
            try {
                listKH = repo.getKH();
                listLS = repo.getLS();

                showData(listKH);
                showDataLS(listLS);

                txtTenKH.setText("");
                txtSoDienThoai.setText("");
                txtDiaChi.setText("");
                rbnNam.setSelected(false);
                rbnNu.setSelected(false);
                rbnDaMua.setSelected(false);
                rbnChuaMua.setSelected(false);
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
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        LoadingDialog loading = new LoadingDialog();
        executorService.submit(() -> {
            try {
                String soDienThoai = txtTimKiemKH.getText().trim();

                ArrayList<KhachHangModels> listTimKiemKH = new ArrayList<>();

                if (!soDienThoai.isEmpty()) {
                    for (KhachHangModels kh : listKH) {

                        if (kh.getSoDienThoai().equals(soDienThoai)) {
                            listTimKiemKH.add(kh);
                        }
                    }

                    if (!listTimKiemKH.isEmpty()) {
                        showData(listTimKiemKH);
                    } else {
                        MessageModal.warning("Không tìm thấy khách hàng với số điện thoại: " + soDienThoai);
                        showDataLS(listLS);
                    }
                } else {
                    MessageModal.warning("Vui lòng nhập số điện thoại để tìm kiếm!");
                    showData(listKH);
                }
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
    }//GEN-LAST:event_btnSearchActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearchLichSu;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSuaLS;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaLS;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rbnChuaMua;
    private javax.swing.JRadioButton rbnDaMua;
    private javax.swing.JRadioButton rbnNam;
    private javax.swing.JRadioButton rbnNu;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTable tblLichSuGD;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JFormattedTextField txtSoDienThoai;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JFormattedTextField txtTimKiemKH;
    private javax.swing.JTextField txtTimKiemLichSu;
    // End of variables declaration//GEN-END:variables

    private void MessageModal(KhachHangView aThis, String vui_lòng_điền_đầy_đủ_thông_tin, String cảnh_báo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private boolean validateLength(String tenKH, String soDienThoai, String diaChi) {
        int maxTenKHLength = 250;
        int maxSoDienThoaiLength = 13;
        int maxDiaChiLength = 2000;

        if (tenKH.length() > maxTenKHLength) {
            MessageModal.warning("Tên khách hàng không được vượt quá " + maxTenKHLength + " ký tự.");
            return false;
        }
        if (soDienThoai.length() > maxSoDienThoaiLength) {
            MessageModal.warning("Số điện thoại không được vượt quá " + maxSoDienThoaiLength + " ký tự.");
            return false;
        }
        if (diaChi.length() > maxDiaChiLength) {
            MessageModal.warning("Địa chỉ không được vượt quá " + maxDiaChiLength + " ký tự.");
            return false;
        }
        return true;
    }
}
