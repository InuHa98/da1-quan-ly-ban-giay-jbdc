/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.app.core.lam.views;

import com.app.core.lam.models.DiaChiModels;
import com.app.core.lam.models.KhachHangModels;
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
    private final KhachHangRepositories repodc = new KhachHangRepositories();
    private DefaultTableModel dtm = new DefaultTableModel();
    private DefaultTableModel dtmdc = new DefaultTableModel();
    private ArrayList<KhachHangModels> listKH = new ArrayList<>();
    private ArrayList<DiaChiModels> listDC = new ArrayList<>();

    public void detailTable(int row) {
        KhachHangModels kh = listKH.get(row);
        txtMaKH.setText(String.valueOf(kh.getIdKH()));
        txtTenKH.setText(kh.getTenKH());
        txtSoDienThoai.setText(kh.getSoDienThoai());
        if (kh.isGioiTinh()) {
            rbnNam.setSelected(true);
        } else {
            rbnNu.setSelected(true);
        }

        if (kh.isTrangThaiXoa()) {
            rbnChuaXoa.setSelected(true);
        } else {
            rbnDaXoa.setSelected(true);
        }
    }

    public void showData(ArrayList<KhachHangModels> listKH) {
        dtm.setNumRows(0);
        for (KhachHangModels kh : listKH) {
            dtm.addRow(new Object[]{
                kh.getIdKH(), kh.getTenKH(), kh.getSoDienThoai(), kh.isGioiTinh(), kh.isTrangThaiXoa()
            });
        }
    }

    public KhachHangModels getFormData() {
        if (txtMaKH.getText().isEmpty()) {
            return null;
        }
        int idKH = Integer.parseInt(txtMaKH.getText());
        String tenKH = txtTenKH.getText();
        String soDienThoai = txtSoDienThoai.getText();
        boolean gioiTinh = rbnNam.isSelected();
        boolean trangThaiXoa = rbnChuaXoa.isSelected();
        KhachHangModels kh = new KhachHangModels(idKH, tenKH, soDienThoai, gioiTinh, trangThaiXoa);
        return kh;
    }

    public KhachHangView() {
        initComponents();
        listKH = repo.getKH();
        dtm = (DefaultTableModel) tblKhachHang.getModel();
        showData(listKH);
        listDC = repodc.getDC();
        dtmdc = (DefaultTableModel) tblDiaChiKhachHang.getModel();
        showDataDC(listDC);
    }

    public void detailTableDiaChi(int row) {
        DiaChiModels dc = listDC.get(row);
        txtMaDC.setText(String.valueOf(dc.getIdDC()));
        txtMaKH.setText(String.valueOf(dc.getIdKH()));
        txtDiaChi.setText(dc.getDiaChi());
        if (dc.isTrangThaiXoa()) {
            rbnChuaXoa.setSelected(true);
        } else {
            rbnDaXoa.setSelected(true);
        }
    }

    public void showDataDC(ArrayList<DiaChiModels> listDC) {
        dtmdc.setNumRows(0);
        for (DiaChiModels dc : listDC) {
            dtmdc.addRow(new Object[]{
                dc.getIdDC(), dc.getIdKH(), dc.getDiaChi(), dc.isTrangThaiXoa()
            });
        }
    }

    public DiaChiModels getFormDataDC() {
        String diaChi = txtDiaChi.getText();
        boolean trangThaiXoa = rbnChuaXoa.isSelected();
        DiaChiModels dc = new DiaChiModels(diaChi, trangThaiXoa);
        return dc;
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
        jLabel3 = new javax.swing.JLabel();
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
        txtMaKH = new javax.swing.JTextField();
        txtTenKH = new javax.swing.JTextField();
        txtMaDC = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
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
        jTextField4 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDiaChiKhachHang = new javax.swing.JTable();
        btnThemDC = new javax.swing.JButton();
        btnSuaDC = new javax.swing.JButton();
        btnXoaDC = new javax.swing.JButton();

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 255));
        jLabel2.setText("Thiết lập thông tin khách hàng");

        jLabel3.setText("ID Khách Hàng");

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

        btnLamMoi.setText("Làm Mới");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 204));
        jLabel1.setText("Quản lý khách hàng");

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        jLabel7.setText("ID Địa Chỉ");

        jLabel10.setText("Địa Chỉ");

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin khách hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(0, 51, 204))); // NOI18N

        jLabel9.setText("Tìm kiếm theo tên, mã khách hàng");

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
                "ID Khách Hàng", "Tên Khách Hàng", "Số Điện Thoại", "Giới Tính", "Trạng Thái Xóa"
            }
        ));
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
                .addContainerGap(506, Short.MAX_VALUE))
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

        jLabel11.setText("Tìm kiếm theo mã , địa chỉ...");

        jButton7.setText("Tìm");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7))
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
                    .addComponent(jButton7)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        tblDiaChiKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Địa Chỉ", "ID Khách Hàng", "Địa Chỉ", "Trạng Thái Xóa"
            }
        ));
        tblDiaChiKhachHang.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblDiaChiKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDiaChiKhachHangMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblDiaChiKhachHang);

        btnThemDC.setText("Thêm Địa Chỉ");
        btnThemDC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemDCActionPerformed(evt);
            }
        });

        btnSuaDC.setText("Chỉnh Sửa");

        btnXoaDC.setText("Xóa");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(183, 183, 183)
                        .addComponent(btnThemDC)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSuaDC)
                        .addGap(81, 81, 81))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnXoaDC)
                        .addGap(171, 171, 171))))
            .addComponent(jScrollPane3)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThemDC)
                            .addComponent(btnSuaDC))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnXoaDC)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Danh sách địa chỉ khách hàng", jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(btnThem)
                                .addGap(31, 31, 31)
                                .addComponent(btnSua)
                                .addGap(27, 27, 27)
                                .addComponent(btnXoa)
                                .addGap(31, 31, 31)
                                .addComponent(btnLamMoi)
                                .addGap(251, 251, 251))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(322, 322, 322))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel2))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel6))
                                .addGap(33, 33, 33)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(rbnNam)
                                        .addGap(31, 31, 31)
                                        .addComponent(rbnNu))
                                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel8))
                                .addGap(23, 23, 23)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(rbnDaXoa)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rbnChuaXoa))
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtMaDC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtSoDienThoai, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtDiaChi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(4, 4, 4)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMaDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbnDaXoa)
                            .addComponent(rbnChuaXoa))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(rbnNu)
                            .addComponent(rbnNam)
                            .addComponent(jLabel6))
                        .addGap(29, 29, 29)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLamMoi)
                    .addComponent(btnSua)
                    .addComponent(btnThem)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        String soDienThoai = txtSoDienThoai.getText();

        if (tenKH.isEmpty() || soDienThoai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (check) {
            JOptionPane.showMessageDialog(this, "Thêm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            listKH.add(getFormData());
            showData(listKH);
        } else {
            JOptionPane.showMessageDialog(this, "Thêm không thành công", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        // TODO add your handling code here:
        int row = tblKhachHang.getSelectedRow();
        if (row >= 0) {
            detailTable(row);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        int row = tblKhachHang.getSelectedRow();
        KhachHangModels kh = listKH.get(row);
        boolean check = repo.deleteKhachHang(kh.getIdKH());
        if (check) {
            if (row >= 0) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                listKH.remove(row);
                showData(listKH);
            } else {
                JOptionPane.showMessageDialog(this, "Xóa không thành công", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 khách hàng để xóa", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
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
            kh.setTrangThaiXoa(rbnDaXoa.equals(kh.isTrangThaiXoa()));
            boolean check = repo.updateKhachHang(kh);
            if (check) {
                JOptionPane.showMessageDialog(this, "Sửa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                showData(listKH);
            } else {
                JOptionPane.showMessageDialog(this, "Sửa không thành công", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 khách hàng để sửa", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnThemDCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemDCActionPerformed
        // TODO add your handling code here:
        DiaChiModels newDC = getFormDataDC();
        if (newDC == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        boolean checkDC = repodc.addDiaChi(getFormDataDC());

        String diaChi = txtDiaChi.getText();
        if (diaChi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (checkDC) {
            JOptionPane.showMessageDialog(this, "Thêm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            listDC.add(getFormDataDC());
            showDataDC(listDC);
        } else {
            JOptionPane.showMessageDialog(this, "Thêm không thành công", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnThemDCActionPerformed

    private void tblDiaChiKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDiaChiKhachHangMouseClicked
        // TODO add your handling code here:
        int row = tblDiaChiKhachHang.getSelectedRow();
        detailTableDiaChi(row);
    }//GEN-LAST:event_tblDiaChiKhachHangMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSuaDC;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemDC;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaDC;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JRadioButton rbnChuaXoa;
    private javax.swing.JRadioButton rbnDaXoa;
    private javax.swing.JRadioButton rbnNam;
    private javax.swing.JRadioButton rbnNu;
    private javax.swing.JTable tblDiaChiKhachHang;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtMaDC;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTenKH;
    // End of variables declaration//GEN-END:variables
}
