/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.app.core.lam.views;

import com.app.common.helper.MessageModal;
import com.app.core.lam.models.KhachHangModels;
import com.app.core.lam.models.LichSuModels;
import com.app.core.lam.repositories.KhachHangRepositories;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Admin
 */
public final class KhachHangView extends javax.swing.JPanel {

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
            rbnChuaXoa.setSelected(true);
        } else {
            rbnDaXoa.setSelected(true);
        }
    }

    public void showData(ArrayList<KhachHangModels> listKH) {
        dtm.setRowCount(0);
        for (KhachHangModels kh : listKH) {
            dtm.addRow(new Object[]{
                kh.getTenKH(), kh.getSoDienThoai(), kh.isGioiTinh(), kh.getDiaChi(), kh.isTrangThaiXoa()
            });
        }
    }

    public KhachHangModels getFormData() {
        String tenKH = txtTenKH.getText();
        String soDienThoai = txtSoDienThoai.getText();
        boolean gioiTinh = rbnNam.isSelected();
        boolean trangThaiXoa = rbnChuaXoa.isSelected();
        KhachHangModels kh = new KhachHangModels(tenKH, soDienThoai, gioiTinh, tenKH, trangThaiXoa);
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
            rbnChuaXoa.setSelected(true);
        } else {
            rbnDaXoa.setSelected(true);
        }
    }

    public void showDataLS(ArrayList<LichSuModels> listLS) {
        dtmLS.setRowCount(0);
        for (LichSuModels ls : listLS) {
            dtmLS.addRow(new Object[]{
                ls.getTenKH(), ls.getSoDienThoai(), ls.isGioiTinh(), ls.getDiaChi(), ls.isTrangThaiXoa()
            });
        }
    }

    public LichSuModels getFormDataLS() {
        String tenKH = txtTenKH.getText();
        String soDienThoai = txtSoDienThoai.getText();
        boolean gioiTinh = rbnNam.isSelected();
        boolean trangThaiXoa = rbnChuaXoa.isSelected();
        LichSuModels ls = new LichSuModels(tenKH, soDienThoai, gioiTinh, tenKH, trangThaiXoa);
        return ls;
    }
    
    
    public KhachHangView() {
        initComponents();
        listKH = repo.getKH();
        dtm = (DefaultTableModel) tblKhachHang.getModel();
        showData(listKH);
        
        listLS = repo.getLS();
        dtmLS = (DefaultTableModel) tblLichSuGD.getModel();
        showDataLS(listLS);
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
        rbnDaXoa = new javax.swing.JRadioButton();
        btnThem = new javax.swing.JButton();
        rbnChuaXoa = new javax.swing.JRadioButton();
        btnSua = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnXoa = new javax.swing.JButton();
        txtTenKH = new javax.swing.JTextField();
        txtSoDienThoai = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
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
        btnQRCode = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 255));
        jLabel2.setText("Thiết lập thông tin khách hàng");

        jLabel4.setText("Tên Khách Hàng");

        jLabel5.setText("Số Điện Thoại");

        jLabel6.setText("Giới Tính");

        buttonGroup2.add(rbnNam);
        rbnNam.setText("Nam");

        jLabel8.setText("Trạng Thái Xóa");

        buttonGroup2.add(rbnNu);
        rbnNu.setText("Nữ");

        buttonGroup1.add(rbnDaXoa);
        rbnDaXoa.setText("Đã Xóa");

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbnChuaXoa);
        rbnChuaXoa.setText("Chưa Xóa");

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnLamMoi.setText("Lọc");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 204));
        jLabel1.setText("Quản lý khách hàng");

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        jLabel10.setText("Địa Chỉ");

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin khách hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(0, 51, 204))); // NOI18N

        jLabel9.setText("Tìm kiếm theo số điện thoại");

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
                "Tên Khách Hàng", "Số Điện Thoại", "Giới Tính", "Địa Chỉ", "Trạng Thái Xóa"
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

        btnSearch.setText("Tìm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch))
                    .addComponent(jLabel9)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(513, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearch)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtTimKiemLichSu, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearchLichSu))
                    .addComponent(jLabel11)
                    .addComponent(jSeparator3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearchLichSu)
                    .addComponent(txtTimKiemLichSu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                "Tên Khách Hàng", "Số Điện Thoại", "Giới Tính", "Địa chỉ", "Trạng Thái Xóa"
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
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 267, Short.MAX_VALUE)
                .addComponent(btnXoaLS)
                .addGap(18, 18, 18)
                .addComponent(btnSuaLS)
                .addGap(72, 72, 72))
            .addComponent(jScrollPane3)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSuaLS)
                            .addComponent(btnXoaLS))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Lịch sử giao dịch khách hàng", jPanel2);

        btnQRCode.setText("Quét QR Code");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addGap(12, 12, 12))
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
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtTenKH, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                                .addComponent(txtSoDienThoai)
                                .addComponent(txtDiaChi))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(rbnDaXoa)
                                .addGap(18, 18, 18)
                                .addComponent(rbnChuaXoa))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(rbnNam)
                                .addGap(18, 18, 18)
                                .addComponent(rbnNu)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnThem)
                                .addGap(17, 17, 17)
                                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnQRCode))
                            .addComponent(jSeparator2)))
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(290, 290, 290))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel2))
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
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
                            .addComponent(rbnDaXoa)
                            .addComponent(rbnChuaXoa)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSua)
                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLamMoi)
                            .addComponent(btnQRCode)
                            .addComponent(btnThem))
                        .addGap(2, 2, 2)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        KhachHangModels newKH = getFormData();
        if (newKH == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền số điện thoại!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (isCheckSDT(newKH.getSoDienThoai())) {
            JOptionPane.showMessageDialog(this, "Số điện thoại đã tồn tại!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean check = repo.addKhachHang(getFormData());

        String tenKH = txtTenKH.getText();

        String diaChi = txtDiaChi.getText();

        if (tenKH.isEmpty() || diaChi.isEmpty()) {
            MessageModal.warning("Vui lòng nhập đầy đủ thông tin trên hệ thống !");
            return;
        }
        if (check) {
            MessageModal.success("Thêm thông tin khách hàng thành công!");
            listKH.add(getFormData());
            showData(listKH);
            showDataLS(listLS);
        } else {
            MessageModal.error("Không thể thêm thông tin khách hàng!");
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        // TODO add your handling code here:
        int row = tblKhachHang.getSelectedRow();
        detailTable(row);
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        int row = tblKhachHang.getSelectedRow();
        KhachHangModels kh = listKH.get(row);
        boolean check = repo.deleteKhachHang(kh.getSoDienThoai());
        if (row >= 0) {
            if (check) {
                MessageModal.success("Xóa thông tin khách hàng thành công!");
                listKH.remove(row);
                showData(listKH);
            } else {
                MessageModal.error("Xóa thông tin khách hàng không thành công!");
            }
        } else {
            MessageModal.warning("Vui lòng hãy chọn vào một khách hàng");
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        int row = tblKhachHang.getSelectedRow();
        if (row >= 0) {
            KhachHangModels kh = listKH.get(row);
            kh.setTenKH(txtTenKH.getText());
            kh.setSoDienThoai(txtSoDienThoai.getText());
            kh.setGioiTinh(rbnNam.isSelected());
            kh.setDiaChi(txtDiaChi.getText());
            kh.setTrangThaiXoa(rbnDaXoa.isSelected());
            boolean check = repo.updateKhachHang(kh);
            if (check) {
                MessageModal.success("Sửa thông tin khách hàng thành công!");
                showData(listKH);
            } else {
                MessageModal.error("Sửa thông tin khách hàng không thành công!");
            }
        } else {
            MessageModal.warning("Vui lòng chọn 1 khách hàng để sửa!");
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed

        String soDienThoai = txtSearch.getText().trim();

        ArrayList<KhachHangModels> listSearch = new ArrayList<>();

        if (!soDienThoai.isEmpty()) {
            for (KhachHangModels kh : listKH) {

                if (kh.getSoDienThoai().equals(soDienThoai)) {
                    listSearch.add(kh);
                }
            }

            if (!listSearch.isEmpty()) {
                showData(listSearch);
            } else {
                MessageModal.warning("Không tìm thấy khách hàng với số điện thoại: " + soDienThoai);
                showData(listKH);
            }
        } else {
            MessageModal.warning("Vui lòng nhập số điện thoại để tìm kiếm!");
            showData(listKH);  
        }

    }//GEN-LAST:event_btnSearchActionPerformed

    private void tblLichSuGDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLichSuGDMouseClicked
        // TODO add your handling code here:
            int row = tblLichSuGD.getSelectedRow();
            detailTableLS(row);
    }//GEN-LAST:event_tblLichSuGDMouseClicked

    private void btnSearchLichSuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchLichSuActionPerformed
        // TODO add your handling code here:
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
    }//GEN-LAST:event_btnSearchLichSuActionPerformed

    private void btnXoaLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaLSActionPerformed
        // TODO add your handling code here:
        int row = tblLichSuGD.getSelectedRow();
        KhachHangModels kh = listKH.get(row);
        boolean check = repo.deleteKhachHang(kh.getSoDienThoai());
        if (row >= 0) {
            if (check) {
                MessageModal.success("Xóa thông tin khách hàng thành công!");
                listKH.remove(row);
                showData(listKH);
            } else {
                MessageModal.error("Xóa thông tin khách hàng không thành công!");
            }
        } else {
            MessageModal.warning("Vui lòng hãy chọn vào một khách hàng");
        }
    }//GEN-LAST:event_btnXoaLSActionPerformed

    private void btnSuaLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaLSActionPerformed
        // TODO add your handling code here:
        int row = tblLichSuGD.getSelectedRow();
        if (row >= 0) {
            KhachHangModels kh = listKH.get(row);
            kh.setTenKH(txtTenKH.getText());
            kh.setSoDienThoai(txtSoDienThoai.getText());
            kh.setGioiTinh(rbnNam.isSelected());
            kh.setDiaChi(txtDiaChi.getText());
            kh.setTrangThaiXoa(rbnDaXoa.isSelected());
            boolean check = repo.updateKhachHang(kh);
            if (check) {
                MessageModal.success("Sửa thông tin khách hàng thành công!");
                showData(listKH);
            } else {
                MessageModal.error("Sửa thông tin khách hàng không thành công!");
            }
        } else {
            MessageModal.warning("Vui lòng chọn 1 khách hàng để sửa!");
        }
    }//GEN-LAST:event_btnSuaLSActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnQRCode;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearchLichSu;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSuaLS;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaLS;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rbnChuaXoa;
    private javax.swing.JRadioButton rbnDaXoa;
    private javax.swing.JRadioButton rbnNam;
    private javax.swing.JRadioButton rbnNu;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTable tblLichSuGD;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTimKiemLichSu;
    // End of variables declaration//GEN-END:variables

    private void MessageModal(KhachHangView aThis, String vui_lòng_điền_đầy_đủ_thông_tin, String cảnh_báo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
