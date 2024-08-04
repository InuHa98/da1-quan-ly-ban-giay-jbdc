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
import com.app.core.lam.repositories.LichSuRepositories;
import com.app.utils.NumberPhoneUtils;
import com.app.utils.ResourceUtils;
import com.app.utils.SessionUtils;
import com.app.utils.TimeUtils;
import com.app.views.UI.dialog.LoadingDialog;
import java.awt.Dimension;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
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

    private final LichSuRepositories repoLS = new LichSuRepositories();

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
                kh.getIdKH(), kh.getTenKH(), kh.getSoDienThoai(), kh.isGioiTinh() ? "Nam" : "Nữ", kh.getDiaChi(), kh.getNgayTao(), kh.getNgayTao()
            });
        }
    }

    public KhachHangModels getFormData() {
        String tenKH = txtTenKH.getText();
        String soDienThoai = txtSoDienThoai.getText();
        boolean gioiTinh = rbnNam.isSelected();
        String diaChi = txtDiaChi.getText();
        String ngayTao = TimeUtils.currentDateTime();
        boolean trangThaiXoa = !rbnChuaMua.isSelected();
        KhachHangModels kh = new KhachHangModels(WIDTH, tenKH, soDienThoai, gioiTinh, diaChi, trangThaiXoa);
        return kh;
    }

    public void detailTableLS(int row) {

        LichSuModels ls = listLS.get(row);

        txtMaHD.setText(ls.getMaHD());
        txtGiaSP.setText(String.valueOf(ls.getGiaBan()));
        txtSoLuong.setText(String.valueOf(ls.getSoLuong()));
        if (ls.isTrangThaiXoa()) {
            rbnDaMua.setSelected(true);
        } else {
            rbnChuaMua.setSelected(true);
        }

    }

    public void showDataLS(ArrayList<LichSuModels> listLS) {
        dtmLS.setRowCount(0);
        for (LichSuModels ls : listLS) {
            if (ls.isTrangThaiXoa()) {
                dtmLS.addRow(new Object[]{
                    ls.getIdKH(), ls.getMaHD(), ls.getGiaBan(), ls.getSoLuong(),
                    ls.getNgayMua().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    true
                });
            }
        }
    }

    public LichSuModels getFormDataLS() {
        String maHD = txtMaHD.getText();
        Double giaBan = Double.valueOf(txtGiaSP.getText());
        int soLuong = Integer.valueOf(txtSoLuong.getText());
        String ngayMua = TimeUtils.currentDateTime();
        boolean trangThaiXoa = !rbnChuaMua.isSelected();
        LichSuModels ls = new LichSuModels(WIDTH, maHD, giaBan, soLuong, LocalDateTime.now());
        return ls;
    }

    public KhachHangView() {
        initComponents();
        
        btnLamMoiLS.setIcon(ResourceUtils.getSVG("/svg/reload.svg", new Dimension(20, 20)));
        btnThem.setIcon(ResourceUtils.getSVG("/svg/plus.svg", new Dimension(20, 20)));
        btnSua.setIcon(ResourceUtils.getSVG("/svg/edit.svg", new Dimension(20, 20)));
        btnXoa.setIcon(ResourceUtils.getSVG("/svg/trash.svg", new Dimension(20, 20)));
        btnLamMoi.setIcon(ResourceUtils.getSVG("/svg/reload.svg", new Dimension(20, 20)));
        btnXoaLS.setIcon(ResourceUtils.getSVG("/svg/trash.svg", new Dimension(20, 20)));
        btnSuaLS.setIcon(ResourceUtils.getSVG("/svg/edit.svg", new Dimension(20, 20)));

        listKH = repo.getKH();
        dtm = (DefaultTableModel) tblKhachHang.getModel();
        showData(listKH);

        listLS = repoLS.getLS();
        dtmLS = (DefaultTableModel) tblLichSuGD.getModel();
        showDataLS(listLS);

        txtSoDienThoai.setFormatterFactory(NumberPhoneUtils.getDefaultFormat());

        if (!SessionUtils.isManager()) {
            btnXoa.setVisible(false);
            revalidate();
            repaint();
        }
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
        jSeparator2 = new javax.swing.JSeparator();
        txtSoDienThoai = new javax.swing.JFormattedTextField();
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
        btnLamMoiLS = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        txtGiaSP = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        txtNgayMua = new javax.swing.JTextField();

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
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "#", "Tên Khách Hàng", "Số Điện Thoại", "Giới Tính", "Địa Chỉ", "Ngày Tạo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 720, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Danh sách khách hàng", jPanel1);

        jLabel11.setText("Tìm kiếm theo mã hóa đơn.");

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
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "#", "Mã Hóa Đơn", "Giá Sản Phẩm", "Số Lượng", "Ngày Mua"
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
            tblLichSuGD.getColumnModel().getColumn(0).setPreferredWidth(40);
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

        btnLamMoiLS.setText("Làm Mới");
        btnLamMoiLS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiLSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 426, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnLamMoiLS)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaLS)
                        .addGap(18, 18, 18)
                        .addComponent(btnSuaLS))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
            .addComponent(jScrollPane3)
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
                            .addComponent(btnXoaLS)
                            .addComponent(btnLamMoiLS))
                        .addGap(4, 4, 4)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Lịch sử giao dịch khách hàng", jPanel2);

        jLabel1.setText("Mã Hóa Đơn");

        jLabel3.setText("Giá Sản Phẩm");

        jLabel9.setText("Số Lượng");

        jLabel13.setText("Ngày mua hàng");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel2))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
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
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(rbnNam)
                                        .addGap(18, 18, 18)
                                        .addComponent(rbnNu))
                                    .addComponent(txtTenKH)
                                    .addComponent(txtDiaChi)
                                    .addComponent(txtSoDienThoai)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(rbnDaMua)
                                        .addGap(18, 18, 18)
                                        .addComponent(rbnChuaMua)))
                                .addGap(179, 179, 179)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel3))
                                        .addGap(27, 27, 27)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtGiaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel9))
                                        .addGap(15, 15, 15)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtNgayMua, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnThem)
                                .addGap(18, 18, 18)
                                .addComponent(btnSua)
                                .addGap(18, 18, 18)
                                .addComponent(btnXoa)
                                .addGap(18, 18, 18)
                                .addComponent(btnLamMoi)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbnNam)
                            .addComponent(rbnNu)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(rbnDaMua)
                            .addComponent(rbnChuaMua))
                        .addGap(18, 18, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(txtGiaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtNgayMua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSua)
                    .addComponent(btnThem)
                    .addComponent(btnXoa)
                    .addComponent(btnLamMoi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnXoaLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaLSActionPerformed
        // TODO add your handling code here:
        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() {
                return MessageModal.confirmInfo("Bạn thực sự muốn xóa lịch sử mua hàng?");
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        LoadingDialog loading = new LoadingDialog();
                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                        executorService.submit(() -> {
                            try {
                                int row = tblLichSuGD.getSelectedRow();
                                if (row >= 0) {
                                    LichSuModels ls = listLS.get(row);
                                    boolean check = repoLS.deleteHoaDon(ls.getMaHD());
                                    if (check) {
                                        MessageModal.success("Xóa lịch sử mua hàng thành công!");
                                        listLS.remove(row);
                                        SwingUtilities.invokeLater(() -> {
                                            showDataLS(listLS);
                                        });
                                    } else {
                                        MessageModal.error("Xóa lịch sử mua hàng không thành công!");
                                    }
                                } else {
                                    MessageModal.warning("Vui lòng chọn một hàng để xóa!");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                loading.dispose();
                                executorService.shutdown();
                            }
                        });
                        loading.setVisible(true);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }//GEN-LAST:event_btnXoaLSActionPerformed

    private void btnSuaLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaLSActionPerformed
        // TODO add your handling code here:
        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() {
                return MessageModal.confirmInfo("Bạn thực sự muốn sửa lịch sử mua hàng?");
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        LoadingDialog loading = new LoadingDialog();
                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                        executorService.submit(() -> {
                            try {
                                int row = tblLichSuGD.getSelectedRow();
                                if (row >= 0) {
                                    LichSuModels newLS = listLS.get(row);

                                    newLS.setMaHD(txtMaHD.getText());
                                    newLS.setGiaBan(Double.parseDouble(txtGiaSP.getText()));
                                    newLS.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
                                    newLS.setTrangThaiXoa(rbnDaMua.isSelected());


                                    boolean check = repoLS.updateLichSu(newLS.getMaHD());
                                    if (check) {
                                        MessageModal.success("Sửa lịch sử mua hàng thành công!");
                                        SwingUtilities.invokeLater(() -> {
                                            showDataLS(listLS);
                                        });
                                    } else {
                                        MessageModal.error("Sửa lịch sử mua hàng không thành công!");
                                    }
                                } else {
                                    MessageModal.warning("Vui lòng chọn một hàng để sửa!");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                loading.dispose();
                                executorService.shutdown();
                            }
                        });
                        loading.setVisible(true);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }//GEN-LAST:event_btnSuaLSActionPerformed

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
                String maHD = txtTimKiemLichSu.getText().trim();

                ArrayList<LichSuModels> listSearchLichSu = new ArrayList<>();

                if (!maHD.isEmpty()) {
                    for (LichSuModels ls : listLS) {

                        if (ls.getMaHD().equals(maHD)) {
                            listSearchLichSu.add(ls);
                        }
                    }

                    if (!listSearchLichSu.isEmpty()) {
                        showDataLS(listSearchLichSu);
                    } else {
                        MessageModal.warning("Không tìm thấy khách hàng với số điện thoại: " + maHD);
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

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        txtSoDienThoai.setFormatterFactory(NumberPhoneUtils.getDefaultFormat());
        LoadingDialog loading = new LoadingDialog();
        executorService.submit(() -> {
            try {
                String soDienThoai = PhoneUtils.number(txtTimKiemKH.getText().trim());

                ArrayList<KhachHangModels> listTimKiemKH = new ArrayList<>();

                if (!soDienThoai.isEmpty()) {
                    for (KhachHangModels kh : listKH) {
                        String khSoDienThoai = PhoneUtils.number(kh.getSoDienThoai());

                        if (khSoDienThoai.equals(soDienThoai)) {
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
        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() {
                return MessageModal.confirmInfo("Bạn thực sự muốn xóa khách hàng?");
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        LoadingDialog loading = new LoadingDialog();
                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                        executorService.submit(() -> {
                            try {
                                int row = tblKhachHang.getSelectedRow();
                                if (row >= 0) {
                                    KhachHangModels kh = listKH.get(row);
                                    boolean check = repo.deleteKhachHang(kh.getSoDienThoai());
                                    if (check) {
                                        MessageModal.success("Xóa thông tin khách hàng thành công!");
                                        listKH.remove(row);
                                        SwingUtilities.invokeLater(() -> {
                                            showData(listKH);
                                        });
                                    } else {
                                        MessageModal.error("Xóa thông tin khách hàng không thành công!");
                                    }
                                } else {
                                    MessageModal.warning("Vui lòng chọn một khách hàng để xóa!");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                loading.dispose();
                                executorService.shutdown();
                            }
                        });
                        loading.setVisible(true);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        LoadingDialog loading = new LoadingDialog();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                listKH = repo.getKH();

                SwingUtilities.invokeLater(() -> {
                    showData(listKH);
                });

                SwingUtilities.invokeLater(() -> {
                    txtTenKH.setText("");
                    txtSoDienThoai.setText("");
                    txtDiaChi.setText("");
                    rbnNam.setSelected(false);
                    rbnNu.setSelected(false);
                    rbnDaMua.setSelected(false);
                    rbnChuaMua.setSelected(false);
                });
            } catch (ServiceResponseException e) {
                e.printStackTrace();
                MessageToast.error(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                MessageToast.error(ErrorConstant.DEFAULT_ERROR);
            } finally {
                loading.dispose();
                executorService.shutdown();
            }
        });
        loading.setVisible(true);
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed

        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() {
                return MessageModal.confirmInfo("Bạn thực sự muốn sửa thông tin khách hàng?");
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        LoadingDialog loading = new LoadingDialog();
                        ExecutorService executorService = Executors.newSingleThreadExecutor();
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
                                        SwingUtilities.invokeLater(() -> {
                                            listKH.set(row, newKH);
                                            showData(listKH);
                                        });
                                    } else {
                                        MessageModal.error("Sửa thông tin khách hàng không thành công!");
                                    }
                                } else {
                                    MessageModal.warning("Vui lòng chọn 1 khách hàng để sửa!");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                loading.dispose();
                                executorService.shutdown();
                            }
                        });
                        loading.setVisible(true);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() {
                return MessageModal.confirmInfo("Bạn thực sự muốn thêm thông tin khách hàng?");
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        LoadingDialog loading = new LoadingDialog();
                        ExecutorService executorService = Executors.newSingleThreadExecutor();
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

                                if ((tenKH == null || tenKH.isEmpty()) || (diaChi == null || diaChi.isEmpty())) {
                                    MessageModal.warning("Vui lòng nhập đầy đủ thông tin trên hệ thống !");
                                    return;
                                }
                                boolean check = repo.addKhachHang(newKH);

                                if (check) {
                                    MessageModal.success("Thêm thông tin khách hàng thành công!");
                                    listKH.add(newKH);
                                    showData(listKH);
                                } else {
                                    MessageModal.error("Không thể thêm thông tin khách hàng!");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                loading.dispose();
                                executorService.shutdown();
                            }
                        });
                        loading.setVisible(true);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnLamMoiLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiLSActionPerformed
        // TODO add your handling code here:
        LoadingDialog loading = new LoadingDialog();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                listLS = repoLS.getLS();

                SwingUtilities.invokeLater(() -> {
                    showDataLS(listLS);
                });

                SwingUtilities.invokeLater(() -> {
                    txtMaHD.setText("");
                    txtGiaSP.setText("");
                    txtSoLuong.setText("");
                    txtNgayMua.setText("");
                    rbnDaMua.setSelected(false);
                    rbnChuaMua.setSelected(false);
                });
            } catch (ServiceResponseException e) {
                e.printStackTrace();
                MessageToast.error(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                MessageToast.error(ErrorConstant.DEFAULT_ERROR);
            } finally {
                loading.dispose();
                executorService.shutdown();
            }
        });
        loading.setVisible(true);
    }//GEN-LAST:event_btnLamMoiLSActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLamMoiLS;
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
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JTextField txtGiaSP;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtNgayMua;
    private javax.swing.JFormattedTextField txtSoDienThoai;
    private javax.swing.JTextField txtSoLuong;
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

    public class PhoneUtils {

        public static String number(String soDienThoai) {
            return soDienThoai.replaceAll("\\D", "");
        }
    }
}
