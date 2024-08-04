/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.app.core.dattv.views;


import com.app.Application;
import com.app.common.configs.DBConnect;
import com.app.common.helper.MessageModal;
import com.app.common.helper.MessageToast;
import com.app.common.helper.Pagination;
import com.app.common.helper.QrCodeHelper;
import com.app.common.infrastructure.constants.ErrorConstant;
import com.app.common.infrastructure.exceptions.ServiceResponseException;
import com.app.core.dattv.models.DatHoaDonChiTietModel;
import com.app.core.dattv.models.DatHoaDonModel;
import com.app.core.dattv.models.DatKhachHangModel;
import com.app.core.dattv.models.DatNhanvienModel;
import java.util.ArrayList;
import com.app.core.dattv.repositoris.DatHoaDonRepository;
import com.app.core.dattv.repositoris.DatNhanVienRepository;
import com.app.core.dattv.request.DatHoaDonRequest;
import com.app.core.dattv.service.DatHoaDonService;
import com.app.utils.QrCodeUtils;
import com.app.views.UI.combobox.ComboBoxItem;
import com.app.views.UI.dialog.LoadingDialog;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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
public class DatHoaDonView  extends RoundPanel{
    DatHoaDonRepository datHoaDonRepository=new DatHoaDonRepository();
    DatHoaDonService datHoaDonService=new DatHoaDonService();
    ArrayList<DatHoaDonRequest> list;
    Date curentDate=new Date();
    private static DatHoaDonView instance;
    private Pagination pagination =new Pagination();
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    int count,soTrang,trang=1;
    
    /**
     * Creates new form DatHoaDon
     */
    
    public DatHoaDonView() {
        initComponents();
        instance = this;
        
        tblHoadon.setRowHeight(50);
        loadCbbNguoitaoHD();
        ckoToanthoigian.setSelected(true);
        dateNgaybatdau.setDate(curentDate);
        dateNgayketthuc.setDate(curentDate);
        
        loadDataPage1(trang);
        
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
     public void count() {
        //SUA LAI CAU QUERY
        String sql = """
                    SELECT    count(*) from hoadon
                     """;
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                
              count= rs.getInt(1);
              
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
       
    }
     public void countPage1(int phuongThucTT,int trangThai,int idNv) {
        //SUA LAI CAU QUERY
        String sql = """
                       WITH CTE_HoaDon AS (
                                         SELECT
                                             hd.ma,
                                             hd.ngay_tao,
                                             ISNULL(kh.ho_ten, N'Khách hàng lẻ') AS ho_ten,
                                             SUM(hdct.gia_ban * so_luong) AS tong_gia_ban,
                                             hd.tien_giam,
                                             SUM((hdct.gia_ban * so_luong) - hd.tien_giam) AS tong_sau_giam,
                                             hd.trang_thai,
                                             hd.trang_thai_xoa,
                                             hd.phuong_thuc_thanh_toan,
                                             hd.id,
                                             ROW_NUMBER() OVER (ORDER BY hd.ngay_tao DESC) AS row_num
                                         FROM 
                                             HoaDon hd
                                         INNER JOIN 
                                             HoaDonChiTiet hdct ON hd.id = hdct.id_hoa_don
                                         INNER JOIN 
                                             KhachHang kh ON hd.id_khach_hang = kh.id
                                         INNER JOIN 
                                             TaiKhoan tk ON hd.id_tai_khoan = tk.id 
                                     	Where hd.trang_thai_xoa =0 and
                                              (COALESCE( ?, 0) < 0 OR hd.phuong_thuc_thanh_toan = ?) AND
                                              (COALESCE( ?, 0 )< 0  OR hd.trang_thai = ?) AND
                                              (COALESCE(?, 0) < 1 OR tk.id = ?) 
                                         GROUP BY 
                                             hd.ma,
                                             hd.ngay_tao,
                                             kh.ho_ten,
                                             hd.tien_giam,
                                             hd.trang_thai,
                                             hd.trang_thai_xoa,
                                             hd.phuong_thuc_thanh_toan,
                                             hd.id
                                     ) Select count (*) from CTE_HoaDon;
                     
                     """;
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setObject(1, phuongThucTT);
                ps.setObject(2, phuongThucTT);
                ps.setObject(3, trangThai);
                ps.setObject(4, trangThai);
                ps.setObject(5, idNv);
                ps.setObject(6, idNv);
                ResultSet rs = ps.executeQuery();
        while (rs.next()) {
                
              count= rs.getInt(1);
              
            }
           
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
       
    }
     public void countPage2(int phuongThucTT,int trangThai,int idNv,Date startDate,Date endDate) {
        String sqlCount = """
                WITH CTE_HoaDon AS (
                     SELECT
                         hd.ma,
                         hd.ngay_tao,
                         ISNULL(kh.ho_ten, N'Khách hàng lẻ') AS ho_ten,
                         SUM(hdct.gia_ban * so_luong) AS tong_gia_ban,
                         hd.tien_giam,
                         SUM((hdct.gia_ban * so_luong) - hd.tien_giam) AS tong_sau_giam,
                         hd.trang_thai,
                         hd.trang_thai_xoa,
                         hd.phuong_thuc_thanh_toan,
                         hd.id,
                         ROW_NUMBER() OVER (ORDER BY hd.ngay_tao DESC) AS row_num
                     FROM 
                         HoaDon hd
                     INNER JOIN 
                         HoaDonChiTiet hdct ON hd.id = hdct.id_hoa_don
                     INNER JOIN 
                         KhachHang kh ON hd.id_khach_hang = kh.id
                     INNER JOIN 
                         TaiKhoan tk ON hd.id_tai_khoan = tk.id 
                 	Where hd.trang_thai_xoa =0 and
                          hd.trang_thai_xoa = 0
                           AND (hd.ngay_tao >= ? OR ? IS NULL)
                           AND (hd.ngay_tao <= ? OR ? IS NULL)
                           AND (COALESCE(?, -1) < 0 OR hd.phuong_thuc_thanh_toan = ?)
                           AND (COALESCE(?, -1) < 0 OR hd.trang_thai = ?)
                           AND (COALESCE(?, 0) = 0 OR tk3.id = ?)
                     GROUP BY 
                         hd.ma,
                         hd.ngay_tao,
                         kh.ho_ten,
                         hd.tien_giam,
                         hd.trang_thai,
                         hd.trang_thai_xoa,
                         hd.phuong_thuc_thanh_toan,
                         hd.id
                 )
                 SELECT  count(*)  FROM CTE_HoaDon
                
                     """;
 
        try (Connection con = DBConnect.getInstance().getConnect();
            PreparedStatement ps = con.prepareStatement(sqlCount)) {
            
        ps.setDate(1, new java.sql.Date(startDate.getTime())); // Ngày bắt đầu
        ps.setDate(2, new java.sql.Date(startDate.getTime())); // Ngày bắt đầu (sử dụng lại cho điều kiện IS NULL)
        ps.setDate(3, new java.sql.Date(endDate.getTime()));   // Ngày kết thúc
        ps.setDate(4, new java.sql.Date(endDate.getTime()));   // Ngày kết thúc (sử dụng lại cho điều kiện IS NULL)
        ps.setInt(5, phuongThucTT); // Phương thức thanh toán
        ps.setInt(6, phuongThucTT); // Phương thức thanh toán
        ps.setInt(7, trangThai); // Trạng thái
        ps.setInt(8, trangThai); // Trạng thái
        ps.setInt(9, idNv); // ID tài khoản
        ps.setInt(10, idNv); // ID tài khoản
     
      
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
               long count=rs.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        
    }
     
    void loadData(ArrayList<DatHoaDonRequest> list) {
        DefaultTableModel tableModel = (DefaultTableModel) tblHoadon.getModel();
        tableModel.setRowCount(0);

    for (DatHoaDonRequest datHoaDonRequest : list) {
        String trangThaiString = getTrangThaiString(datHoaDonRequest.getTrangThai());

        Object[] row = {
            false,
            datHoaDonRequest.getMaHd(),
            datHoaDonRequest.getThoiGian(),
            datHoaDonRequest.getKhachHang(),
            datHoaDonRequest.getTongTienhang(),
            datHoaDonRequest.getGiamGia(),
            datHoaDonRequest.getThanhTien(),
            trangThaiString
        };

        tableModel.addRow(row);
    }
    }
    public void printSelected(){
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
            cell.setCellValue("Thời gian");
            
            cell=row.createCell(3, CellType.STRING);
            cell.setCellValue("Khách Hàng");
            
            cell=row.createCell(4, CellType.STRING);
            cell.setCellValue("Tổng tiền hàng");
            
            cell=row.createCell(5, CellType.STRING);
            cell.setCellValue("Giảm giá");
            
            cell=row.createCell(6, CellType.STRING);
            cell.setCellValue("Thành Tiền");
            
            cell=row.createCell(7, CellType.STRING);
            cell.setCellValue("Nhân viên");
            
            cell=row.createCell(8, CellType.STRING);
            cell.setCellValue("Trạng thái");
            
            for (int i = 0; i < tblHoadon.getRowCount(); i++) {
                
            boolean isChecked=(Boolean)tblHoadon.getValueAt(i, 0);
            if(isChecked){
                String maHd = (String) tblHoadon.getValueAt(i, 1);
                Date thoiGian = (Date) tblHoadon.getValueAt(i, 2);
                String khachHang = (String) tblHoadon.getValueAt(i, 3);
                double tongTienhang = (Double) tblHoadon.getValueAt(i, 4);
                double giamGia = (Double) tblHoadon.getValueAt(i, 5);
                double thanhTien = (Double) tblHoadon.getValueAt(i, 6);
                String trangThaiString = (String) tblHoadon.getValueAt(i, 7);
                
                row=sheet.createRow(4+i);
                cell=row.createCell(0,CellType.NUMERIC);
                cell.setCellValue(i+1);
                
                cell=row.createCell(1, CellType.STRING);
                cell.setCellValue(maHd);
                
                cell=row.createCell(2, CellType.STRING);
                cell.setCellValue(thoiGian);
                
                cell=row.createCell(3, CellType.STRING);
                cell.setCellValue(khachHang);
                
                cell=row.createCell(4, CellType.STRING);
                cell.setCellValue(tongTienhang);

                cell=row.createCell(5, CellType.STRING);
                cell.setCellValue(giamGia);

                cell=row.createCell(6, CellType.STRING);
                cell.setCellValue(thanhTien);

                cell=row.createCell(7, CellType.STRING);
                cell.setCellValue(trangThaiString);
                
            }
        }
  
            JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new File("DefaultFilename.xlsx")); // Tên mặc định của file
        
        int userSelection = fileChooser.showSaveDialog(null);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileOutputStream fileOut = new FileOutputStream(fileToSave)) {
                workBook.write(fileOut);
                JOptionPane.showMessageDialog(this, "Lưu thành công !");
                workBook.close();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi lưu file Excel.");
            }
        }
           
        } catch (Exception e) {
        }

    }

  public void loadDataPage1(long trang) {
   

    int pt = -1;
    int trangThai = -1;
    int idNv = cbbNguoitao.getSelectedIndex();

    // Xác định phương thức thanh toán
    switch (cbbPhuongthucTT.getSelectedItem().toString()) {
        case "Tiền mặt":
            pt = 0;
            break;
        case "Chuyển khoản":
            pt = 1;
            break;
        case "Tiền mặt + Chuyển khoản":
            pt = 2;
            break;
    }

    // Xác định trạng thái thanh toán
    switch (cbbTrangthai.getSelectedItem().toString()) {
        case "Đã thanh toán":
            trangThai = 1;
            break;
        case "Chưa thanh toán":
            trangThai = 0;
            break;
        case "Đã hủy":
            trangThai = 2;
            break;
    }

    Date startDate = dateNgaybatdau.getDate();
    Date endDate = dateNgayketthuc.getDate();
    
    if (pt == -1 && trangThai == -1 && idNv == 0 && ckoToanthoigian.isSelected()) {
        // Trường hợp không có bộ lọc
        this.count();

        if (count % 15 == 0) {
            soTrang = count / 15;
        } else {
            soTrang = count / 15 + 1;
        }

        list = datHoaDonRepository.getTop15(trang);
        loadData(list);
        lblTrang.setText(""+trang);
        lblSotrang.setText(trang +" / " + soTrang);
    } else {
        if ((pt != -1 || trangThai != -1 || idNv != 0 )&&ckoToanthoigian.isSelected()) {
        // Trường hợp có bộ lọc
        Date curentDate = new Date();
        dateNgaybatdau.setDate(curentDate);
        dateNgayketthuc.setDate(curentDate);
        startDate = null; // Đặt ngày bắt đầu về null
        endDate = null;   // Đặt ngày kết thúc về null

        this.countPage1(pt, trangThai, idNv);
        if (count % 15 == 0) {
            soTrang = count / 15;
        } else {
            soTrang = count / 15 + 1;
        }
      
        list = datHoaDonRepository.locDataPage(pt, trangThai, idNv, trang);
        try {
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn");
            } else {
                loadData(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu hóa đơn");
        }
        lblTrang.setText(""+trang);
        lblSotrang.setText(trang + " / " + soTrang);
    } else {
        // Nếu không chọn toàn thời gian, đảm bảo ngày không null và chuyển đổi sang java.sql.Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        startDate = startDate != null ? new java.sql.Date(startDate.getTime()) : null;
        endDate = endDate != null ? new java.sql.Date(endDate.getTime()) : null;

        this.countPage2(pt, trangThai, idNv, startDate, endDate);
        if (count % 15 == 0) {
            soTrang = count / 15;
        } else {
            soTrang = count / 15 + 1;
        }
        list = datHoaDonRepository.locDataPage2(pt, trangThai, idNv, startDate, endDate, trang);
        try {
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn");
            } else {
                loadData(list);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu hóa đơn");
        }
        lblTrang.setText(""+trang);
        lblSotrang.setText(trang + " / " + soTrang);
    }
    }
}

private String getTrangThaiString(int trangThai) {
    switch (trangThai) {
        case 0:
            return "Chưa thanh toán";
        case 1:
            return "Đã thanh toán";
        case 2:
            return "Đã hủy";
        default:
            return "Trạng thái không xác định";
    }
}
private void handleClickButtonScanQrCode() {
        QrCodeHelper.showWebcam("Tìm kiếm hóa đơn bằng QR", result -> {

           LoadingDialog loading = new LoadingDialog();
            executorService.submit(() -> {
               try {
                   String code = result.getText();

                 DatHoaDonRequest hoaDonRequest = null;
                   int id;
                    if ((id = QrCodeUtils.getIdHoaDon(code)) > 0) {
                      hoaDonRequest = datHoaDonService.getById(id);
                    
                    } else { 
                        loading.dispose();
                       MessageToast.error("QRCode không hợp lệ!!!");
                       return;
                   }
                   loading.dispose();
                   showHoadonchitiet();
               } catch (ServiceResponseException e) {
                   loading.dispose();
                   MessageModal.error(e.getMessage());
               } catch (Exception e) {
                   e.printStackTrace();
                  loading.dispose();
                   MessageModal.error(ErrorConstant.DEFAULT_ERROR);
                }
            });
            loading.setVisible(true);
        });

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
            cell.setCellValue("Thời gian");
            
            cell=row.createCell(3, CellType.STRING);
            cell.setCellValue("Khách Hàng");
            
            cell=row.createCell(4, CellType.STRING);
            cell.setCellValue("Tổng tiền hàng");
            
            cell=row.createCell(5, CellType.STRING);
            cell.setCellValue("Giảm giá");
            
            cell=row.createCell(6, CellType.STRING);
            cell.setCellValue("Thành Tiền");
            
            cell=row.createCell(7, CellType.STRING);
            cell.setCellValue("Nhân viên");
            
            cell=row.createCell(8, CellType.STRING);
            cell.setCellValue("Trạng thái");
            
            
            ArrayList<DatHoaDonRequest> list=datHoaDonRepository.getAll();
            for (int i = 0; i < list.size(); i++) {
                DatHoaDonRequest datHoaDonRequest=list.get(i);
                row=sheet.createRow(4+i);
                
                cell=row.createCell(0,CellType.NUMERIC);
                cell.setCellValue(i+1);
                
                cell=row.createCell(1, CellType.STRING);
                cell.setCellValue(list.get(i).getMaHd());
                
                cell=row.createCell(2, CellType.STRING);
                cell.setCellValue(list.get(i).getThoiGian());
            
                cell=row.createCell(3, CellType.STRING);
                cell.setCellValue(list.get(i).getKhachHang());

                cell=row.createCell(4, CellType.STRING);
                cell.setCellValue(list.get(i).getTongTienhang());

                cell=row.createCell(5, CellType.STRING);
                cell.setCellValue(list.get(i).getGiamGia());

                cell=row.createCell(6, CellType.STRING);
                cell.setCellValue(list.get(i).getThanhTien());

                cell=row.createCell(7, CellType.STRING);
                cell.setCellValue(list.get(i).getTrangThai());
                
                
            }
            
            
            JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new File("DefaultFilename.xlsx")); // Tên mặc định của file
        
        int userSelection = fileChooser.showSaveDialog(null);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileOutputStream fileOut = new FileOutputStream(fileToSave)) {
                workBook.write(fileOut);
                JOptionPane.showMessageDialog(this, "Lưu thành công !");
                workBook.close();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi lưu file Excel.");
            }
        }
           
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
        ckoToanthoigian = new javax.swing.JCheckBox();
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
        btnXuatdanhsach = new javax.swing.JButton();
        plnPhantrang = new javax.swing.JPanel();
        btnNext = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        lblTrang = new javax.swing.JLabel();
        btnPreview = new javax.swing.JButton();
        lblSotrang = new javax.swing.JLabel();
        btnLoadhoadon = new javax.swing.JButton();

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        plnSearch.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 153, 51));
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
        dateNgaybatdau.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);

        dateNgayketthuc.setDateFormatString("yyyy-MM-dd");

        ckoToanthoigian.setText("Toàn thời gian");

        javax.swing.GroupLayout plnTimeLayout = new javax.swing.GroupLayout(plnTime);
        plnTime.setLayout(plnTimeLayout);
        plnTimeLayout.setHorizontalGroup(
            plnTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plnTimeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plnTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(plnTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(plnTimeLayout.createSequentialGroup()
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(plnTimeLayout.createSequentialGroup()
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(18, 18, 18)))
                    .addGroup(plnTimeLayout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)))
                .addGroup(plnTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dateNgayketthuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateNgaybatdau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ckoToanthoigian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        plnTimeLayout.setVerticalGroup(
            plnTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plnTimeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plnTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(ckoToanthoigian))
                .addGap(23, 23, 23)
                .addGroup(plnTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(dateNgaybatdau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(plnTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(dateNgayketthuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 153, 0));
        jLabel7.setText("Bộ lọc");

        plnTrangthai.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Trạng Thái");

        cbbTrangthai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--- Tất cả trạng thái ---", "Chưa thanh toán", "Đã thanh toán", "Đã hủy" }));
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

        cbbPhuongthucTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--- Tất cả phương thức  ---", "Tiền mặt", "Chuyển khoản", "Tiền mặt +Chuyển khoản" }));
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
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLoc)
                .addContainerGap(12, Short.MAX_VALUE))
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
                "#", "Mã hóa đơn", "Thời gian", "Khách hàng", "Tổng tiền hàng", "Giảm giá", "Thành tiền", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

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
            .addComponent(scrDanhsach, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        plnTableLayout.setVerticalGroup(
            plnTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plnTableLayout.createSequentialGroup()
                .addComponent(scrDanhsach)
                .addContainerGap())
        );

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 153, 51));
        jLabel13.setText("Hóa Đơn");

        btnXuatdanhsach.setText("Xuất danh sách");
        btnXuatdanhsach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatdanhsachActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXuatdanhsach)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXuatdanhsach))
                .addContainerGap())
        );

        btnNext.setText("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        jButton3.setText("Last");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Fisrt");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        lblTrang.setText("trang");

        btnPreview.setText("Prev");
        btnPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviewActionPerformed(evt);
            }
        });

        lblSotrang.setText("jLabel2");

        javax.swing.GroupLayout plnPhantrangLayout = new javax.swing.GroupLayout(plnPhantrang);
        plnPhantrang.setLayout(plnPhantrangLayout);
        plnPhantrangLayout.setHorizontalGroup(
            plnPhantrangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plnPhantrangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(lblTrang)
                .addGap(27, 27, 27)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblSotrang, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        plnPhantrangLayout.setVerticalGroup(
            plnPhantrangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plnPhantrangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plnPhantrangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(lblTrang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPreview, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(lblSotrang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(plnPhantrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 149, Short.MAX_VALUE)
                        .addComponent(btnLoadhoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))
                    .addComponent(plnTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(plnTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnLoadhoadon)
                            .addComponent(plnPhantrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimkiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimkiemActionPerformed

    private void btnLoadhoadonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadhoadonActionPerformed
        // TODO add your handling code here:
        
        cbbNguoitao.setSelectedIndex(0);
        cbbPhuongthucTT.setSelectedIndex(0);
        cbbTrangthai.setSelectedIndex(0);
        dateNgaybatdau.setDate(curentDate);
        dateNgayketthuc.setDate(curentDate);
        ckoToanthoigian.setSelected(true);
        txtTimkiem.setText(null);
        trang=1;
        loadDataPage1(trang);
    }//GEN-LAST:event_btnLoadhoadonActionPerformed

    private void btnQrcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQrcodeActionPerformed
        // TODO add your handling code here:
        handleClickButtonScanQrCode();
    }//GEN-LAST:event_btnQrcodeActionPerformed

    private void btnTimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimkiemActionPerformed
        // TODO add your handling code here: 
        cbbNguoitao.setSelectedIndex(0);
        cbbPhuongthucTT.setSelectedIndex(0);
        cbbTrangthai.setSelectedIndex(0);
        ckoToanthoigian.setSelected(true);
        String searchkeyword="%"+ txtTimkiem.getText()+"%";
        int index=tblHoadon.getSelectedRow();
        list = datHoaDonRepository.search(searchkeyword);
        if(searchkeyword.trim()==null){
            JOptionPane.showMessageDialog(this, "Nhập thông tin hóa đơn hoặc tên khách hàng -SĐT khách hàng!");
        }
        if(!list.isEmpty()){
            loadData(list);

        } else  {
            JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn");
        }
        if(index!=-1){
             showHoadonchitiet();   
        }
       
            
           
            


            
            
        
        
    }//GEN-LAST:event_btnTimkiemActionPerformed

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        // TODO add your handling code here:   
        loadDataPage1(trang);
        txtTimkiem.setText(null);
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
            showHoadonchitiet();
        }
    }//GEN-LAST:event_tblHoadonMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
     trang=1;
        loadDataPage1(trang);
       
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewActionPerformed
        // TODO add your handling code here:
        if(trang>1){
            trang--;
            loadDataPage1(trang);
        }else{
            JOptionPane.showMessageDialog(this, "Bạn đang ở trang đầu tiên ! ");
        }
    }//GEN-LAST:event_btnPreviewActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        
        if(trang<soTrang){
            trang++;   
            loadDataPage1(trang);
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnXuatdanhsachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatdanhsachActionPerformed
        // TODO add your handling code here:
        List<String> selectedInvoices = new ArrayList<>();
        for (int i = 0; i < tblHoadon.getRowCount(); i++) {
            Boolean isChecked = (Boolean) tblHoadon.getValueAt(i, 0);
            if (isChecked) {
                selectedInvoices.add((String) tblHoadon.getValueAt(i, 1));
            }
        }
        if(selectedInvoices.isEmpty()){
            int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn tiếp tục?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        // Kiểm tra kết quả và thực hiện hành động tương ứng
            if (result == JOptionPane.YES_OPTION) {
                inDanhSach();
            } 
        }else{
            int result = JOptionPane.showConfirmDialog(this, "In hóa đơn đã chọn ?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        // Kiểm tra kết quả và thực hiện hành động tương ứng
            if (result == JOptionPane.YES_OPTION) {
                printSelected();
            } 
        }
         
    }//GEN-LAST:event_btnXuatdanhsachActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        trang=soTrang;
        loadDataPage1(trang);
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoadhoadon;
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPreview;
    private javax.swing.JButton btnQrcode;
    private javax.swing.JButton btnTimkiem;
    private javax.swing.JButton btnXuatdanhsach;
    private javax.swing.JComboBox<Object> cbbNguoitao;
    private javax.swing.JComboBox<Object> cbbPhuongthucTT;
    private javax.swing.JComboBox<String> cbbTrangthai;
    private javax.swing.JCheckBox ckoToanthoigian;
    private com.toedter.calendar.JDateChooser dateNgaybatdau;
    private com.toedter.calendar.JDateChooser dateNgayketthuc;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
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
    private javax.swing.JLabel lblSotrang;
    private javax.swing.JLabel lblTrang;
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
    
   
    
    private void showHoadonchitiet() {
        
            ModalDialog.showModal(Application.app, new SimpleModalBorder(new DatHoaDonChiTietView(list.get(tblHoadon.getSelectedRow())),"Chi tiết hóa đơn"));
            
        
    }
}
