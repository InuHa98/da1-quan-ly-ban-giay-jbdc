/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.app.core.dattv.views;

import com.app.Application;
import com.app.common.configs.DBConnect;
import com.app.core.dattv.models.DatHoaDonChiTietModel;
import com.app.core.dattv.models.DatHoaDonModel;
import com.app.core.dattv.repositoris.DatHoaDonChiTietRepository;
import com.app.core.dattv.request.DatHoaDonRequest;
import com.app.views.UI.table.ITableActionEvent;
import com.app.views.UI.table.TableCustomUI;
import com.app.views.UI.table.celll.TableActionCellEditor;
import com.app.views.UI.table.celll.TableActionCellRender;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import raven.modal.ModalDialog;
import raven.modal.component.SimpleModalBorder;

/**
 *
 * @author WIN
 */
public class DatHoaDonChiTietView extends javax.swing.JPanel {
    DatHoaDonChiTietRepository datHoaDonChiTietRepository=new DatHoaDonChiTietRepository();
    DatHoaDonChiTietModel hoaDonChiTietModel=new DatHoaDonChiTietModel();
    DatHoaDonRequest datHoaDonRequest= null;
    DatHoaDonView datHoaDonView=new DatHoaDonView();
    private int tongSoLuong;
    
    /**
     * Creates new form DatHoaDon
     */
    public DatHoaDonChiTietView(DatHoaDonRequest datHoaDonRequest) {
        initComponents();
        this.datHoaDonRequest=datHoaDonRequest;
        String mahd=datHoaDonRequest.getMaHd();
        this.tongSoLuong(mahd);
        ArrayList<DatHoaDonChiTietModel> list=datHoaDonChiTietRepository.loadDatHoaDonChiTietTable(mahd);
        loadData(list);
        setupTable(tblHoadonchitiet);
        datHoaDonChiTietRepository.tongSoLuong(mahd);
        lblMaHd.setText(datHoaDonRequest.getMaHd());
        lblNgayTao.setText(String.valueOf(datHoaDonRequest.getThoiGian()));
        lblTenkhachhang.setText(datHoaDonRequest.getKhachHang());
        if(datHoaDonRequest.getTrangThai()==1){
            cboTrangthai.setSelectedIndex(0);
        }else if(datHoaDonRequest.getTrangThai()==0){
            cboTrangthai.setSelectedIndex(1);
        }else{
            cboTrangthai.setSelectedIndex(2);
        }
        lblNhanvien.setText(datHoaDonRequest.getTenNv());
        lblTongsoluong.setText(String.valueOf( ""+tongSoLuong));
        lblTongtienhang.setText(String.valueOf(datHoaDonRequest.getTongTienhang()));
        lblGiamgia.setText(String.valueOf(datHoaDonRequest.getGiamGia()));
        lblThanhtoan.setText(String.valueOf(datHoaDonRequest.getThanhTien()));
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
              
            }
            
        };
        
        table.setRowHeight(50);
        
    }
    void loadData(ArrayList<DatHoaDonChiTietModel> list) {
        DefaultTableModel tableModel = (DefaultTableModel) tblHoadonchitiet.getModel();
        tableModel.setRowCount(0);
        for (DatHoaDonChiTietModel datHoaDonChiTietModel : list) {
           Object[] row={
               datHoaDonChiTietModel.getMaSp(),
               datHoaDonChiTietModel.getTenSP(),
               datHoaDonChiTietModel.getGiaBan(),
               datHoaDonChiTietModel.getSoLuong(),
               datHoaDonChiTietModel.getGiaGiam(),
               datHoaDonChiTietModel.getThanhTien(),
           };
           
               tableModel.addRow(row);
        }
    }
    public void tongSoLuong(String maHd) {
        String sql = """
            SELECT	
                           
                            sum(hdct.so_luong)
              FROM HoaDonChiTiet3 hdct
                            INNER JOIN HoaDon3 hd ON hd.id = hdct.id_hoa_don
               where hd.ma=?	 
              
                     """;
        ArrayList<DatHoaDonChiTietModel> lists = new ArrayList<>();
        try (Connection con = DBConnect.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, maHd);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
               tongSoLuong=rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }

    }
    public void inDanhSach(){
        try {
            XSSFWorkbook workBook=new XSSFWorkbook();
            XSSFSheet sheet=workBook.createSheet("Danh sách hóa đơn");
            XSSFRow row=null;
            Cell cell=null;
            
            row=sheet.createRow(3);
            
            
            cell=row.createCell(0, CellType.STRING);
            cell.setCellValue("STT");
            
            cell=row.createCell(1, CellType.STRING);
            cell.setCellValue("Mã HĐ");
            
            cell=row.createCell(2, CellType.STRING);
            cell.setCellValue("Mã hàng");
            
            cell=row.createCell(3, CellType.STRING);
            cell.setCellValue("Tên Hàng");
            
            cell=row.createCell(4, CellType.STRING);
            cell.setCellValue("Đơn giá");
            
            cell=row.createCell(5, CellType.STRING);
            cell.setCellValue("Số lượng");
            
            cell=row.createCell(6, CellType.STRING);
            cell.setCellValue("Thành Tiền");
            
            cell=row.createCell(7, CellType.STRING);
            cell.setCellValue("Tổng số lượng");
            
            cell=row.createCell(8, CellType.STRING);
            cell.setCellValue("Tổng tiền hàng");
            
            
            this.datHoaDonRequest=datHoaDonRequest;
            String mahd=datHoaDonRequest.getMaHd();
            ArrayList<DatHoaDonChiTietModel> list=datHoaDonChiTietRepository.loadDatHoaDonChiTietTable(mahd);
            for (int i = 0; i < list.size(); i++) {
                DatHoaDonChiTietModel datHoaDonChiTietModel=list.get(i);
                row=sheet.createRow(4+i);
                
                cell=row.createCell(0,CellType.NUMERIC);
                cell.setCellValue(i+1);
                
                cell=row.createCell(1, CellType.STRING);
                cell.setCellValue(list.get(i).getMaHd());
                
                cell=row.createCell(2, CellType.STRING);
                cell.setCellValue(list.get(i).getMaSp());
            
                cell=row.createCell(3, CellType.STRING);
                cell.setCellValue(list.get(i).getTenSP());

                cell=row.createCell(4, CellType.STRING);
                cell.setCellValue(list.get(i).getGiaBan());

                cell=row.createCell(5, CellType.STRING);
                cell.setCellValue(list.get(i).getSoLuong());

                cell=row.createCell(6, CellType.STRING);
                cell.setCellValue(list.get(i).getThanhTien());

                cell=row.createCell(7, CellType.STRING);
                cell.setCellValue(list.get(i).getTongSoluong());
                
                cell=row.createCell(8, CellType.STRING);
                cell.setCellValue(list.get(i).getTongTienhang());
                
                
            }
            
            File file=new File("C:\\Users\\WIN\\Desktop\\qr\\danhsachhoadonchitiet.xlsx");
            try {
                FileOutputStream fileOutputStream=new FileOutputStream(file);
                workBook.write(fileOutputStream);
                fileOutputStream.close();
            } catch (Exception e) {
                
            }
            JOptionPane.showMessageDialog(this, "In thành công !");
        } catch (Exception e) {
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        roundPanel1 = new com.app.views.UI.panel.RoundPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        roundPanel2 = new com.app.views.UI.panel.RoundPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHoadonchitiet = new javax.swing.JTable();
        roundPanel4 = new com.app.views.UI.panel.RoundPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        lblTongsoluong = new javax.swing.JLabel();
        lblTongtienhang = new javax.swing.JLabel();
        lblThanhtoan = new javax.swing.JLabel();
        lblGiamgia = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblMaHd = new javax.swing.JLabel();
        lblNgayTao = new javax.swing.JLabel();
        lblTenkhachhang = new javax.swing.JLabel();
        lblNhanvien = new javax.swing.JLabel();
        cboTrangthai = new javax.swing.JComboBox<>();
        roundPanel3 = new com.app.views.UI.panel.RoundPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        tblHoadonchitiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã hàng", "Tên hàng", "Đơn giá", "Số lượng", "Giảm giá", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblHoadonchitiet);

        jButton3.setText("In");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Xuất DS");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Xóa");

        jLabel1.setForeground(new java.awt.Color(0, 204, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Tổng số lượng :");

        jLabel2.setForeground(new java.awt.Color(51, 204, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Tổng tiền hàng :");

        jLabel3.setForeground(new java.awt.Color(51, 204, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Giảm giá :");

        jLabel4.setForeground(new java.awt.Color(51, 204, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Thanh toán :");

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Ghi chú :");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        lblTongsoluong.setText("jLabel11");

        lblTongtienhang.setText("jLabel11");

        lblThanhtoan.setText("jLabel11");

        lblGiamgia.setText("jLabel11");

        javax.swing.GroupLayout roundPanel4Layout = new javax.swing.GroupLayout(roundPanel4);
        roundPanel4.setLayout(roundPanel4Layout);
        roundPanel4Layout.setHorizontalGroup(
            roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(roundPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))
                    .addGroup(roundPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                        .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblThanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGiamgia, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTongtienhang, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTongsoluong, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14))))
        );
        roundPanel4Layout.setVerticalGroup(
            roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(lblTongsoluong))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel4Layout.createSequentialGroup()
                        .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTongtienhang))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGiamgia))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblThanhtoan)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton4)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Mã hóa đơn :");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Thời gian :");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Khách hàng :");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Trạng thái :");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Nhân viên :");

        lblMaHd.setText("lblMaHd");

        lblNgayTao.setText("jLabel11");

        lblTenkhachhang.setText("jLabel11");

        lblNhanvien.setText("jLabel11");

        cboTrangthai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đã thanh toán", "Chưa thanh toán", "Đã hủy" }));

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addComponent(roundPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundPanel2Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(133, 133, 133))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblNgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblMaHd, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                                    .addComponent(lblTenkhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(13, 13, 13)))
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblNhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(roundPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cboTrangthai, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(cboTrangthai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(lblMaHd))))
                .addGap(39, 39, 39)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel6)
                    .addComponent(lblNgayTao)
                    .addComponent(lblNhanvien))
                .addGap(18, 18, 18)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblTenkhachhang))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Hóa đơn chi tiết", roundPanel2);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout roundPanel3Layout = new javax.swing.GroupLayout(roundPanel3);
        roundPanel3.setLayout(roundPanel3Layout);
        roundPanel3Layout.setHorizontalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
        );
        roundPanel3Layout.setVerticalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Lịch sử hóa đơn", roundPanel3);

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        inDanhSach();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cboTrangthai;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblGiamgia;
    private javax.swing.JLabel lblMaHd;
    private javax.swing.JLabel lblNgayTao;
    private javax.swing.JLabel lblNhanvien;
    private javax.swing.JLabel lblTenkhachhang;
    private javax.swing.JLabel lblThanhtoan;
    private javax.swing.JLabel lblTongsoluong;
    private javax.swing.JLabel lblTongtienhang;
    private com.app.views.UI.panel.RoundPanel roundPanel1;
    private com.app.views.UI.panel.RoundPanel roundPanel2;
    private com.app.views.UI.panel.RoundPanel roundPanel3;
    private com.app.views.UI.panel.RoundPanel roundPanel4;
    private javax.swing.JTable tblHoadonchitiet;
    // End of variables declaration//GEN-END:variables
}
