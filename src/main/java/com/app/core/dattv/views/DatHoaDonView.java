/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.app.core.dattv.views;


import com.app.Application;
import com.app.common.helper.Pagination;
import com.app.core.dattv.models.DatHoaDonChiTietModel;
import com.app.core.dattv.models.DatHoaDonModel;
import com.app.core.dattv.models.DatKhachHangModel;
import com.app.core.dattv.models.DatNhanvienModel;
import com.app.core.dattv.models.DatPhuongThucThanhToanModel;
import java.util.ArrayList;
import com.app.core.dattv.repositoris.DatHoaDonRepository;
import com.app.core.dattv.repositoris.DatHoaDonRepository2;
import com.app.core.dattv.repositoris.DatNhanVienRepository;
import com.app.core.dattv.repositoris.DatPhuongThucThanhToanRepository;
import com.app.core.dattv.request.DatFillerHoaDonRequest;
import com.app.core.dattv.request.DatHoaDonRequest;
import com.app.views.UI.combobox.ComboBoxItem;
import com.app.views.UI.panel.RoundPanel;
import com.app.views.UI.table.ITableActionEvent;
import com.app.views.UI.table.TableCustomUI;
import com.app.views.UI.table.celll.CheckBoxTableHeaderRenderer;
import com.app.views.UI.table.celll.TableActionCellEditor;
import com.app.views.UI.table.celll.TableActionCellRender;
import com.app.views.UI.table.celll.TableImageCellRender;
import com.toedter.calendar.JDateChooser;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import raven.modal.ModalDialog;
import raven.modal.component.SimpleModalBorder;


/**
 *
 * @author WIN
 */
public class DatHoaDonView  extends RoundPanel{
    DatHoaDonRepository2 datHoaDonRepository=new DatHoaDonRepository2();
    ArrayList<DatHoaDonRequest> list=datHoaDonRepository.getAll();
    
    private static DatHoaDonView instance;
    private Pagination pagination =new Pagination();
   
    /**
     * Creates new form DatHoaDon
     */
    
    public DatHoaDonView() {
        initComponents();
        instance = this;
        ArrayList<DatHoaDonRequest> lists=datHoaDonRepository.getAll();
        loadData(lists);
        loadCbbNguoitaoHD();
        
        setupTable(tblHoadon);
        renderPagination();
    }
    private void setupTable(JTable table){
        ITableActionEvent event = new ITableActionEvent(){
            @Override
            public void onEdit(int row) {
                System.out.println("aaaaaaaaaaaaa");
            }

            @Override
            public void onDelete(int row) {
                System.out.println("bbbbbbb");     
            }

            @Override
            public void onView(int row) {
                System.out.println("cccccc"); 
                ModalDialog.showModal(Application.app, new SimpleModalBorder(new DatHoaDonChiTietView(list.get(tblHoadon.getSelectedRow())),"Chi tiết hóa đơn"));
            }
            
        };
        TableCustomUI.apply(scrDanhsach, TableCustomUI.TableType.DEFAULT);
        table.setRowHeight(50);
        table.getColumnModel().getColumn(7).setCellRenderer(new TableActionCellRender(table));
        table.getColumnModel().getColumn(7).setCellEditor(new TableActionCellEditor(event));
    }
    public void renderPagination(){
        pagination.setPanel(plnPhantrang);
        pagination.setCurrentPage(1);
        pagination.setTotalPages(3);
        pagination.setCallback(new Pagination.Callback(){
            @Override
            public void onChangeLimitItem(JComboBox<Integer> comboBox) {
                System.err.println(comboBox.getSelectedItem());
            }

            @Override
            public void onClickPage(int page) {
                System.err.println(page);
            }
        
        });
    pagination.render();
    }
    
    
    public void loadCbbNguoitaoHD() {
        DatNhanVienRepository datNhanVienRepository=new DatNhanVienRepository();
        ArrayList<DatNhanvienModel> listNv=datNhanVienRepository.getAll();
        cbbNguoitao.removeAllItems();
        cbbNguoitao.addItem("---Chọn nhan viên--- ");
        for (DatNhanvienModel datNhanvienModel : listNv) {
            cbbNguoitao.addItem(datNhanvienModel);
        }
    }
    

    public void loadCbbTrangThai() {
        
    }
    public void loadCbbPhuongthucTT() {
       DatPhuongThucThanhToanRepository datPhuongThucThanhToanRepository=new DatPhuongThucThanhToanRepository();
        ArrayList<DatPhuongThucThanhToanModel> list=datPhuongThucThanhToanRepository.getAll();
        cbbPhuongthucTT.removeAllItems();
        cbbPhuongthucTT.addItem("---Chọn phương thứ thanh toán---");
        for (DatPhuongThucThanhToanModel datPhuongThucThanhToanModel : list) {
            cbbPhuongthucTT.addItem(datPhuongThucThanhToanModel);
        }
    }
    
    void loadData(ArrayList<DatHoaDonRequest> list) {
        DefaultTableModel tableModel = (DefaultTableModel) tblHoadon.getModel();
        tableModel.setRowCount(0);
        for (DatHoaDonRequest datHoaDonRequest : list) {
           Object[] row={
               datHoaDonRequest.getMaHd(),
               datHoaDonRequest.getThoiGian(),
               datHoaDonRequest.getKhachHang(),
               datHoaDonRequest.getTongTienhang(),
               datHoaDonRequest.getGiamGia(),
               datHoaDonRequest.getThanhTien(),
               datHoaDonRequest.isTrangThai()?"Đã thanh toán":"Chưa thanh toán"};
           
               tableModel.addRow(row);
        }
    }
    void fillComBo() {
        DefaultTableModel model = (DefaultTableModel) tblHoadon.getModel();
        model.setRowCount(0);
        int mahd = 0;
        switch (cbbTrangthai.getSelectedItem().toString()) {
            case "Đã thanh toán":
                mahd = 1;

                break;
            case "Chưa thanh toán":
                mahd = 0;

                break;
            case "Đã hủy":
                mahd=2;
               

                break;
            default:
                throw new AssertionError();
        }
        try {

            List<DatHoaDonRequest> list = datHoaDonRepository.selectTrangThai(mahd);

            for (DatHoaDonRequest hd : list) {

                Object[] row = {
                    hd.getMaHd(),
                    hd.getThoiGian(),
                    hd.getKhachHang(),
                    hd.getTongTienhang(),
                    hd.getGiamGia(),
                    hd.getThanhTien(),
                    hd.isTrangThai()?"Đã thanh toán":"Chưa thanh toán"
                    
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }
  void fillComBoThanhToan() {
        DefaultTableModel model = (DefaultTableModel) tblHoadon.getModel();
        model.setRowCount(0);
        int pt = 0;
        int trangThai=0;
        switch (cbbPhuongthucTT.getSelectedItem().toString()) {
            case "Tiền mặt":
                pt = 0;

                break;
            case "Chuyển khoản":
                pt = 1;
           
                break;
            case "Tiền mặt +Chuyển khoản":
                pt=2;
                break;
               

               
            default:
                throw new AssertionError();
        }
        switch (cbbTrangthai.getSelectedItem().toString()) {
            case "Đã thanh toán":
                trangThai = 1;

                break;
            case "Chưa thanh toán":
                trangThai = 0;

                break;
            
            default:
                throw new AssertionError();
        }
        try {

            List<DatHoaDonRequest> list = datHoaDonRepository.selectThanhToan(pt,trangThai);

            for (DatHoaDonRequest hd : list) {

                Object[] row = {
                    hd.getMaHd(),
                    hd.getThoiGian(),
                    hd.getKhachHang(),
                    hd.getTongTienhang(),
                    hd.getGiamGia(),
                    hd.getThanhTien(),
                    hd.isTrangThai()?"Đã thanh toán":"Chưa thanh toán"
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            
            e.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        plnSearch = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        btnTimkiem = new javax.swing.JButton();
        btnQrcode = new javax.swing.JButton();
        txtTimkiem = new javax.swing.JTextField();
        plnTime = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        dateNgaybatdau = new com.toedter.calendar.JDateChooser();
        dateNgayketthuc = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        plnTrangthai = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        cbbTrangthai = new javax.swing.JComboBox<>();
        plnPhuongthucTT = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        cbbPhuongthucTT = new javax.swing.JComboBox<>();
        plnNguoitao = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cbbNguoitao = new javax.swing.JComboBox<>();
        btnLoc = new javax.swing.JButton();
        plnTable = new javax.swing.JPanel();
        scrDanhsach = new javax.swing.JScrollPane();
        tblHoadon = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnXuatdanhsach = new javax.swing.JButton();
        plnPhantrang = new javax.swing.JPanel();
        btnLoadhoadon = new javax.swing.JButton();

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        plnSearch.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 153, 255));
        jLabel6.setText("Tìm kiếm");

        btnTimkiem.setText("Tìm kiếm");
        btnTimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimkiemActionPerformed(evt);
            }
        });

        btnQrcode.setText("Quét QR");
        btnQrcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQrcodeActionPerformed(evt);
            }
        });

        txtTimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimkiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout plnSearchLayout = new javax.swing.GroupLayout(plnSearch);
        plnSearch.setLayout(plnSearchLayout);
        plnSearchLayout.setHorizontalGroup(
            plnSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plnSearchLayout.createSequentialGroup()
                .addGroup(plnSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(plnSearchLayout.createSequentialGroup()
                        .addGroup(plnSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(plnSearchLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(plnSearchLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(btnTimkiem)
                                .addGap(36, 36, 36)
                                .addComponent(btnQrcode)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtTimkiem, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        plnSearchLayout.setVerticalGroup(
            plnSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plnSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(plnSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQrcode)
                    .addComponent(btnTimkiem))
                .addGap(16, 16, 16))
        );

        plnTime.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Thời gian");

        jLabel4.setText("Từ ngày :");

        jLabel10.setText("Đến ngày :");

        dateNgaybatdau.setDateFormatString("yyyy-MM-dd");

        dateNgayketthuc.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout plnTimeLayout = new javax.swing.GroupLayout(plnTime);
        plnTime.setLayout(plnTimeLayout);
        plnTimeLayout.setHorizontalGroup(
            plnTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plnTimeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plnTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(plnTimeLayout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dateNgayketthuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, plnTimeLayout.createSequentialGroup()
                        .addGroup(plnTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(plnTimeLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(plnTimeLayout.createSequentialGroup()
                                .addGap(0, 14, Short.MAX_VALUE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)))
                        .addComponent(dateNgaybatdau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        plnTimeLayout.setVerticalGroup(
            plnTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plnTimeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(plnTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(dateNgaybatdau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(plnTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(dateNgayketthuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 153, 255));
        jLabel7.setText("Bộ lọc");

        plnTrangthai.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Trạng Thái");

        cbbTrangthai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chưa thanh toán", "Đã thanh toán", "Đã hủy" }));
        cbbTrangthai.setAutoscrolls(true);
        cbbTrangthai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTrangthaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout plnTrangthaiLayout = new javax.swing.GroupLayout(plnTrangthai);
        plnTrangthai.setLayout(plnTrangthaiLayout);
        plnTrangthaiLayout.setHorizontalGroup(
            plnTrangthaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plnTrangthaiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plnTrangthaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(plnTrangthaiLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cbbTrangthai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        plnTrangthaiLayout.setVerticalGroup(
            plnTrangthaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plnTrangthaiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(cbbTrangthai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        plnPhuongthucTT.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Phương thức thanh toán");

        cbbPhuongthucTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền mặt", "Chuyển khoản", "Tiền mặt +Chuyển khoản" }));
        cbbPhuongthucTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbPhuongthucTTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout plnPhuongthucTTLayout = new javax.swing.GroupLayout(plnPhuongthucTT);
        plnPhuongthucTT.setLayout(plnPhuongthucTTLayout);
        plnPhuongthucTTLayout.setHorizontalGroup(
            plnPhuongthucTTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plnPhuongthucTTLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plnPhuongthucTTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(plnPhuongthucTTLayout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cbbPhuongthucTT, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        plnPhuongthucTTLayout.setVerticalGroup(
            plnPhuongthucTTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plnPhuongthucTTLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbbPhuongthucTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        plnNguoitao.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Người tạo");

        cbbNguoitao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout plnNguoitaoLayout = new javax.swing.GroupLayout(plnNguoitao);
        plnNguoitao.setLayout(plnNguoitaoLayout);
        plnNguoitaoLayout.setHorizontalGroup(
            plnNguoitaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plnNguoitaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plnNguoitaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                    .addComponent(cbbNguoitao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        plnNguoitaoLayout.setVerticalGroup(
            plnNguoitaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plnNguoitaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(cbbNguoitao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(plnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(plnTime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(plnTrangthai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(plnPhuongthucTT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(plnNguoitao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(plnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plnTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(plnTrangthai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(plnPhuongthucTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(plnNguoitao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLoc)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        plnTable.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblHoadon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Thời gian", "Khách hàng", "Tổng tiền hàng", "Giảm giá", "Thành tiền", "Trạng thái", "Hành động"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoadon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoadonMouseClicked(evt);
            }
        });
        scrDanhsach.setViewportView(tblHoadon);

        javax.swing.GroupLayout plnTableLayout = new javax.swing.GroupLayout(plnTable);
        plnTable.setLayout(plnTableLayout);
        plnTableLayout.setHorizontalGroup(
            plnTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrDanhsach, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        );
        plnTableLayout.setVerticalGroup(
            plnTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plnTableLayout.createSequentialGroup()
                .addComponent(scrDanhsach)
                .addContainerGap())
        );

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 102, 255));
        jLabel13.setText("Hóa Đơn");

        btnThem.setText("Tạo mới");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXuatdanhsach.setText("Xuất dánh sách");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThem)
                .addGap(22, 22, 22)
                .addComponent(btnXuatdanhsach)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem)
                    .addComponent(btnXuatdanhsach))
                .addContainerGap())
        );

        javax.swing.GroupLayout plnPhantrangLayout = new javax.swing.GroupLayout(plnPhantrang);
        plnPhantrang.setLayout(plnPhantrangLayout);
        plnPhantrangLayout.setHorizontalGroup(
            plnPhantrangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 479, Short.MAX_VALUE)
        );
        plnPhantrangLayout.setVerticalGroup(
            plnPhantrangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 44, Short.MAX_VALUE)
        );

        btnLoadhoadon.setText("Tất cả HD");
        btnLoadhoadon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadhoadonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(plnTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(plnPhantrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLoadhoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(plnTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(plnPhantrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLoadhoadon)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimkiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimkiemActionPerformed

    private void btnLoadhoadonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadhoadonActionPerformed
        // TODO add your handling code here:
        ArrayList<DatHoaDonRequest> list=datHoaDonRepository.getAll();
        loadData(list);
        
    }//GEN-LAST:event_btnLoadhoadonActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnQrcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQrcodeActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btnQrcodeActionPerformed

    private void btnTimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimkiemActionPerformed
        // TODO add your handling code here: 
        try {
            
            String keyword = txtTimkiem.getText();
            ArrayList<DatHoaDonRequest> list = datHoaDonRepository.search(keyword);
            
            if(list.isEmpty()){
                 JOptionPane.showMessageDialog(btnTimkiem, "Không tìm thấy hóa đơn !");
            }else{
                loadData(list);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(btnTimkiem, "Không tìm thấy hóa đơn !");
        }
        
    }//GEN-LAST:event_btnTimkiemActionPerformed

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        // TODO add your handling code here:   
       fillComBoThanhToan();
    }//GEN-LAST:event_btnLocActionPerformed

    private void cbbTrangthaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTrangthaiActionPerformed
        // TODO add your handling code here:
       
        
    }//GEN-LAST:event_cbbTrangthaiActionPerformed

    private void cbbPhuongthucTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbPhuongthucTTActionPerformed
        // TODO add your handling code here:
      
        
    }//GEN-LAST:event_cbbPhuongthucTTActionPerformed

    private void tblHoadonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoadonMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()>1){
            ModalDialog.showModal(Application.app, new SimpleModalBorder(new DatHoaDonChiTietView(list.get(tblHoadon.getSelectedRow())),"Chi tiết hóa đơn"));
            
        }
    }//GEN-LAST:event_tblHoadonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoadhoadon;
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnQrcode;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimkiem;
    private javax.swing.JButton btnXuatdanhsach;
    private javax.swing.JComboBox<Object> cbbNguoitao;
    private javax.swing.JComboBox<Object> cbbPhuongthucTT;
    private javax.swing.JComboBox<String> cbbTrangthai;
    private com.toedter.calendar.JDateChooser dateNgaybatdau;
    private com.toedter.calendar.JDateChooser dateNgayketthuc;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel plnNguoitao;
    private javax.swing.JPanel plnPhantrang;
    private javax.swing.JPanel plnPhuongthucTT;
    private javax.swing.JPanel plnSearch;
    private javax.swing.JPanel plnTable;
    private javax.swing.JPanel plnTime;
    private javax.swing.JPanel plnTrangthai;
    private javax.swing.JScrollPane scrDanhsach;
    private javax.swing.JTable tblHoadon;
    private javax.swing.JTextField txtTimkiem;
    // End of variables declaration//GEN-END:variables
    
   
    
    private void showDetailSanPham(DatHoaDonRequest item) {
    }
}
