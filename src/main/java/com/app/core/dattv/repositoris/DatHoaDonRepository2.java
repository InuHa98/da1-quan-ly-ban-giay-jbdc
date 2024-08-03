/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.core.dattv.repositoris;

import com.app.common.configs.DBConnect;
import com.app.common.helper.JbdcHelper;
import com.app.core.dattv.request.DatHoaDonRequest;
import com.app.utils.QrCodeUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.imageio.ImageIO;


/**
 *
 * @author WIN
 */
public class DatHoaDonRepository2 {
    public ArrayList<DatHoaDonRequest> getAll() {
        //SUA LAI CAU QUERY
        String sql = """
                    SELECT    
                              hd.ma,
                              hd.ngay_tao,
                              kh.ho_ten,
                              SUM(hdct.gia_ban * so_luong) AS tong_gia_ban,
                              hd.tien_giam,
                              (SUM(hdct.gia_ban * so_luong) - hd.tien_giam) AS tong_sau_giam,
                              hd.trang_thai,
                              hd.trang_thai_xoa,
                              hd.phuong_thuc_thanh_toan,
                              hd.id
                          FROM 
                              HoaDon hd
                          INNER JOIN 
                              HoaDonChiTiet hdct ON hd.id = hdct.id_hoa_don
                          INNER JOIN 
                              KhachHang kh ON hd.id_khach_hang = kh.id
                          INNER JOIN 
                              TaiKhoan tk ON hd.id_tai_khoan = tk.id 
                          GROUP BY 
                              
                              hd.ma,
                              hd.ngay_tao,
                              kh3.ho_ten,
                              hd.tien_giam,
                              hd.trang_thai,
                              hd.trang_thai_xoa,
                              hd.phuong_thuc_thanh_toan,
                              hd.id
                          ORDER BY 
                              hd.ngay_tao DESC;
                     """;
        ArrayList<DatHoaDonRequest> lists = new ArrayList<>();
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DatHoaDonRequest datHoaDonRequest=new DatHoaDonRequest();
                
                datHoaDonRequest.setMaHd(rs.getString(1));
                datHoaDonRequest.setThoiGian(rs.getDate(2));
                datHoaDonRequest.setKhachHang(rs.getString(3));
                datHoaDonRequest.setTongTienhang(rs.getDouble(4));
                datHoaDonRequest.setGiamGia(rs.getDouble(5));
                datHoaDonRequest.setThanhTien(rs.getDouble(6));
                datHoaDonRequest.setTrangThai(rs.getInt(7));
                datHoaDonRequest.setTrangThaixoa(rs.getBoolean(8));
                datHoaDonRequest.setPhuongThucTT(rs.getInt(9));
                datHoaDonRequest.setId(rs.getInt(10));
                lists.add(datHoaDonRequest);
               
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return lists;
    }
    public ArrayList<DatHoaDonRequest> search(String keyword ) {
       
        String sql = """
                 SELECT 
                                       hd.ma,
                                       hd.ngay_tao,
                                       kh.ho_ten,
                                       SUM(hdct.gia_ban * so_luong) AS tong_gia_ban,
                                       hd.tien_giam,
                                       (SUM(hdct.gia_ban * so_luong) - hd.tien_giam) AS tong_sau_giam,
                                       hd.trang_thai,
                                       hd.trang_thai_xoa,
                                       hd.phuong_thuc_thanh_toan,
                                        hd.id,
                                        tk.ho_ten as tennv
                                   FROM 
                                       HoaDon hd
                                   INNER JOIN 
                                       HoaDonChiTiet hdct ON hd.id = hdct.id_hoa_don
                                   INNER JOIN 
                                       KhachHang kh ON hd.id_khach_hang = kh.id
                                   INNER JOIN 
                                       TaiKhoan tk ON hd.id_tai_khoan = tk.id 
                                   WHERE  
                                       hd.trang_thai_xoa = 0
                                       AND (hd.ma like ?) OR (kh.ho_ten like ?)
                                   GROUP BY 
                                       hd.ma,
                                       hd.ngay_tao,
                                       kh.ho_ten,
                                       hd.tien_giam,
                                       hd.trang_thai,
                                       hd.trang_thai_xoa,
                                       hd.phuong_thuc_thanh_toan,
                                       hd.id,
                                       tk.ho_ten 
                 		   ORDER BY hd.ngay_tao desc
                     """;
        ArrayList<DatHoaDonRequest> lists = new ArrayList<>();
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setObject(1, keyword);
                ps.setObject(2, keyword);
                //ps.setObject(3, keyword);

                
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DatHoaDonRequest datHoaDonRequest=new DatHoaDonRequest();
                
                datHoaDonRequest.setMaHd(rs.getString(1));
                datHoaDonRequest.setThoiGian(rs.getDate(2));
                datHoaDonRequest.setKhachHang(rs.getString(3));
                datHoaDonRequest.setTongTienhang(rs.getDouble(4));
                datHoaDonRequest.setGiamGia(rs.getDouble(5));
                datHoaDonRequest.setThanhTien(rs.getDouble(6));
                datHoaDonRequest.setTrangThai(rs.getInt(7));
                datHoaDonRequest.setTrangThaixoa(rs.getBoolean(8));
                datHoaDonRequest.setPhuongThucTT(rs.getInt(9));
                datHoaDonRequest.setId(rs.getInt(10));
                datHoaDonRequest.setTenNv(rs.getString(11));
                lists.add(datHoaDonRequest);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return lists;
    }
    
 
    private DatHoaDonRequest readFromResultSet(ResultSet rs) throws SQLException {
        DatHoaDonRequest datHoaDonRequest = new DatHoaDonRequest();
        
                datHoaDonRequest.setMaHd(rs.getString(1));
                datHoaDonRequest.setThoiGian(rs.getDate(2));
                datHoaDonRequest.setKhachHang(rs.getString(3));
                datHoaDonRequest.setTongTienhang(rs.getDouble(4));
                datHoaDonRequest.setGiamGia(rs.getDouble(5));
                datHoaDonRequest.setThanhTien(rs.getDouble(6));
                datHoaDonRequest.setTrangThai(rs.getInt(7));
                datHoaDonRequest.setTrangThaixoa(rs.getBoolean(8));
                datHoaDonRequest.setPhuongThucTT(rs.getInt(9));
                datHoaDonRequest.setId(rs.getInt(10));
        

                
       

        return datHoaDonRequest;
    }
    
    public ArrayList<DatHoaDonRequest> locDataTheoNgay(int phuongThucTT, int trangThai, int idNv, Date startDate, Date endDate) {
    String sql = """
          SELECT      
                      hd.ma,
                      hd.ngay_tao,
                      kh.ho_ten,
                      SUM(hdct.gia_ban * so_luong) AS tong_gia_ban,
                      hd.tien_giam,
                      SUM((hdct.gia_ban * so_luong) - hd.tien_giam) AS tong_sau_giam,
                      hd.trang_thai,
                      hd.trang_thai_xoa,
                      hd.phuong_thuc_thanh_toan,
                      hd.id
                  FROM 
                      HoaDon hd
                  INNER JOIN 
                      HoaDonChiTiet hdct ON hd.id = hdct.id_hoa_don
                  INNER JOIN 
                      KhachHang kh ON hd.id_khach_hang = kh.id
                  INNER JOIN 
                      TaiKhoan tk ON hd.id_tai_khoan = tk.id 
                  WHERE  
                      hd.trang_thai_xoa = 0
                      AND (hd.ngay_tao >= ? OR ? IS NULL)
                      AND (hd.ngay_tao <= ? OR ? IS NULL)
                      AND (COALESCE(?, -1) < 0 OR hd.phuong_thuc_thanh_toan = ?)
                      AND (COALESCE(?, -1) < 0 OR hd.trang_thai = ?)
                      AND (COALESCE(?, 0) = 0 OR tk.id = ?)
                  GROUP BY 
                      hd.ma,
                      hd.ngay_tao,
                      kh.ho_ten,
                      hd.tien_giam,
                      hd.trang_thai,
                      hd.trang_thai_xoa,
                      hd.phuong_thuc_thanh_toan,
                      hd.id
                  ORDER BY 
                      hd.ngay_tao DESC;
     """;

    ArrayList<DatHoaDonRequest> lists = new ArrayList<>();
    try (Connection con = DBConnect.getInstance().getConnect();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
       
         // Thiết lập các tham số
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
            DatHoaDonRequest datHoaDonRequest = new DatHoaDonRequest();
            
            datHoaDonRequest.setMaHd(rs.getString(1));
            datHoaDonRequest.setThoiGian(rs.getDate(2));
            datHoaDonRequest.setKhachHang(rs.getString(3));
            datHoaDonRequest.setTongTienhang(rs.getDouble(4));
            datHoaDonRequest.setGiamGia(rs.getDouble(5));
            datHoaDonRequest.setThanhTien(rs.getDouble(6));
            datHoaDonRequest.setTrangThai(rs.getInt(7));
            datHoaDonRequest.setTrangThaixoa(rs.getBoolean(8));
            datHoaDonRequest.setPhuongThucTT(rs.getInt(9)); // Đổi từ boolean thành int
            datHoaDonRequest.setId(rs.getInt(10));
            
            lists.add(datHoaDonRequest);
        }
    } catch (Exception e) {
        e.printStackTrace(System.out); // Ném lỗi khi xảy ra
    }
    return lists;
}
    private List<DatHoaDonRequest> select(String sql, Object... args) {
        List<DatHoaDonRequest> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JbdcHelper.query(sql, args);
                while (rs.next()) {
                    DatHoaDonRequest model = readFromResultSet(rs);
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }
   
    public void delete(int idHoaDon) throws SQLException{
        String sql=" delete from HoaDonChiTiet where id_hoa_don = ?\n" +
"                   		delete from HoaDon3 where id=?";
    
        try (Connection con = DBConnect.getInstance().getConnect();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setObject(1, idHoaDon);
            ps.setObject(2, idHoaDon);

             ps.executeQuery();
        }
    }
 
    public ArrayList<DatHoaDonRequest> getTop15(long trang) {
        //SUA LAI CAU QUERY
        String sql = """
                  WITH CTE_HoaDon AS (
                        SELECT
                            hd.ma,
                            hd.ngay_tao,
                            kh.ho_ten,
                            SUM(hdct.gia_ban * so_luong) AS tong_gia_ban,
                            hd.tien_giam,
                            (SUM(hdct.gia_ban * so_luong) - hd.tien_giam) AS tong_sau_giam,
                            hd.trang_thai,
                            hd.trang_thai_xoa,
                            hd.phuong_thuc_thanh_toan,
                            hd.id,
                            tk.ho_ten as tennv,
                            ROW_NUMBER() OVER (ORDER BY hd.ngay_tao DESC) AS row_num
                        FROM 
                            HoaDon hd
                        INNER JOIN 
                            HoaDonChiTiet hdct ON hd.id = hdct.id_hoa_don
                        INNER JOIN 
                            KhachHang kh ON hd.id_khach_hang = kh.id
                        INNER JOIN 
                            TaiKhoan tk ON hd.id_tai_khoan = tk.id 
                        GROUP BY 
                            hd.ma,
                            hd.ngay_tao,
                            kh.ho_ten,
                            hd.tien_giam,
                            hd.trang_thai,
                            hd.trang_thai_xoa,
                            hd.phuong_thuc_thanh_toan,
                            hd.id,
                            tk.ho_ten
                    )
                    SELECT 
                        ma,
                        ngay_tao,
                        ho_ten,
                        tong_gia_ban,
                        tien_giam,
                        tong_sau_giam,
                        trang_thai,
                        trang_thai_xoa,
                        phuong_thuc_thanh_toan,
                        id,
                        tennv
                    FROM 
                        CTE_HoaDon
                    WHERE 
                        row_num > (?*15-15) AND row_num <=(?*15)
                    ORDER BY 
                        ngay_tao DESC;
                     """;
        ArrayList<DatHoaDonRequest> lists = new ArrayList<>();
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setObject(1, trang);
                ps.setObject(2, trang);
                ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DatHoaDonRequest datHoaDonRequest=new DatHoaDonRequest();
                
                datHoaDonRequest.setMaHd(rs.getString(1));
                datHoaDonRequest.setThoiGian(rs.getDate(2));
                datHoaDonRequest.setKhachHang(rs.getString(3));
                datHoaDonRequest.setTongTienhang(rs.getDouble(4));
                datHoaDonRequest.setGiamGia(rs.getDouble(5));
                datHoaDonRequest.setThanhTien(rs.getDouble(6));
                datHoaDonRequest.setTrangThai(rs.getInt(7));
                datHoaDonRequest.setTrangThaixoa(rs.getBoolean(8));
                datHoaDonRequest.setPhuongThucTT(rs.getInt(9));
                datHoaDonRequest.setId(rs.getInt(10));
                datHoaDonRequest.setTenNv(rs.getString(11));
                lists.add(datHoaDonRequest);
               
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return lists;
    }
  
    public ArrayList<DatHoaDonRequest> locDataPage(int phuongThucTT,int trangThai,int idNv,long trang) {
        String sql = """
                WITH CTE_HoaDon AS (
                         SELECT
                             hd.ma,
                             hd.ngay_tao,
                             kh.ho_ten,
                             SUM(hdct.gia_ban * so_luong) AS tong_gia_ban,
                             hd.tien_giam,
                             (SUM(hdct.gia_ban * so_luong) - hd.tien_giam) AS tong_sau_giam,
                             hd.trang_thai,
                             hd.trang_thai_xoa,
                             hd.phuong_thuc_thanh_toan,
                             hd.id,
                             tk.ho_ten as tennv,
                             ROW_NUMBER() OVER (ORDER BY hd.ngay_tao DESC) AS row_num
                         FROM 
                             HoaDon hd
                         INNER JOIN 
                             HoaDonChiTiet hdct ON hd.id = hdct.id_hoa_don
                         INNER JOIN 
                             KhachHang kh ON hd.id_khach_hang = kh.id
                         INNER JOIN 
                             TaiKhoan tk ON hd.id_tai_khoan = tk.id 
                         WHERE 
                             hd.trang_thai_xoa = 0 AND
                             (COALESCE(?, 0) < 0 OR hd.phuong_thuc_thanh_toan = ?) AND
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
                             hd.id,
                             tk.ho_ten
                     )
                     SELECT 
                         ma,
                         ngay_tao,
                         ho_ten,
                         tong_gia_ban,
                         tien_giam,
                         tong_sau_giam,
                         trang_thai,
                         trang_thai_xoa,
                         phuong_thuc_thanh_toan,
                         id,
                         tennv
                     FROM 
                         CTE_HoaDon
                     WHERE 
                         row_num > (? * 15 - 15) AND row_num <= (? * 15)
                     ORDER BY 
                         ngay_tao DESC;
                     """;
        ArrayList<DatHoaDonRequest> lists = new ArrayList<>();
        try (Connection con = DBConnect.getInstance().getConnect();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setObject(1, phuongThucTT);
            ps.setObject(2, phuongThucTT);
            ps.setObject(3, trangThai);
            ps.setObject(4, trangThai);
            ps.setObject(5, idNv);
            ps.setObject(6, idNv);
            ps.setObject(7, trang);
            ps.setObject(8, trang);
            
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DatHoaDonRequest datHoaDonRequest=new DatHoaDonRequest();
                
                datHoaDonRequest.setMaHd(rs.getString(1));
                datHoaDonRequest.setThoiGian(rs.getDate(2));
                datHoaDonRequest.setKhachHang(rs.getString(3));
                datHoaDonRequest.setTongTienhang(rs.getDouble(4));
                datHoaDonRequest.setGiamGia(rs.getDouble(5));
                datHoaDonRequest.setThanhTien(rs.getDouble(6));
                datHoaDonRequest.setTrangThai(rs.getInt(7));
                datHoaDonRequest.setTrangThaixoa(rs.getBoolean(8));
                datHoaDonRequest.setPhuongThucTT(rs.getInt(9));
                datHoaDonRequest.setId(rs.getInt(10));
                datHoaDonRequest.setTenNv(rs.getString(11));
                
                lists.add(datHoaDonRequest);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return lists;
    }
    public ArrayList<DatHoaDonRequest> locDataPage2(int phuongThucTT,int trangThai,int idNv,Date startDate,Date endDate,long trang) {
        String sql = """
                WITH CTE_HoaDon AS (
                     SELECT
                         hd.ma,
                         hd.ngay_tao,
                         kh.ho_ten,
                         SUM(hdct.gia_ban * so_luong) AS tong_gia_ban,
                         hd.tien_giam,
                         (SUM(hdct.gia_ban * so_luong) - hd.tien_giam) AS tong_sau_giam,
                         hd.trang_thai,
                         hd.trang_thai_xoa,
                         hd.phuong_thuc_thanh_toan,
                         hd.id,
                     tk.ho_ten as tennv,
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
                         hd.id,
                         tk.ho_ten
                 )
                 SELECT 
                     ma,
                     ngay_tao,
                     ho_ten,
                     tong_gia_ban,
                     tien_giam,
                     tong_sau_giam,
                     trang_thai,
                     trang_thai_xoa,
                     phuong_thuc_thanh_toan,
                     id,
                     tennv
                 FROM 
                     CTE_HoaDon
                 WHERE 
                     row_num > (?*15-15) AND row_num <=(?*15)
                 ORDER BY 
                     ngay_tao DESC;
                     """;
        ArrayList<DatHoaDonRequest> lists = new ArrayList<>();
        try (Connection con = DBConnect.getInstance().getConnect();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
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
        ps.setLong(11,trang); // ID tài khoản
        ps.setLong(12, trang); // ID tài khoản
            
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DatHoaDonRequest datHoaDonRequest=new DatHoaDonRequest();
                
                datHoaDonRequest.setMaHd(rs.getString(1));
                datHoaDonRequest.setThoiGian(rs.getDate(2));
                datHoaDonRequest.setKhachHang(rs.getString(3));
                datHoaDonRequest.setTongTienhang(rs.getDouble(4));
                datHoaDonRequest.setGiamGia(rs.getDouble(5));
                datHoaDonRequest.setThanhTien(rs.getDouble(6));
                datHoaDonRequest.setTrangThai(rs.getInt(7));
                datHoaDonRequest.setTrangThaixoa(rs.getBoolean(8));
                datHoaDonRequest.setPhuongThucTT(rs.getInt(9));
                datHoaDonRequest.setId(rs.getInt(10));
                datHoaDonRequest.setTenNv(rs.getString(11));
                
                lists.add(datHoaDonRequest);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return lists;
    }

     public static BufferedImage hoadonQRCodeImage(String barcodeText) throws Exception{
         QRCodeWriter barCodeWriter=new QRCodeWriter();
         BitMatrix bitMatrix=barCodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 300, 300);
         return  MatrixToImageWriter.toBufferedImage(bitMatrix);
     }
     public  static void saveQRCodeImage(BufferedImage qrCodeImage, String desktop) throws IOException{
         ImageIO.write(qrCodeImage, "PNG", new File(desktop));
     }
     public static byte[] convertBufferedImageToByByteArray(BufferedImage bufferedImage) throws IOException{
         ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
         ImageIO.write(bufferedImage, "PNG", byteArrayOutputStream);
         return byteArrayOutputStream.toByteArray();
     }
     
     public void taoQrCode(){
         try {
             String desktop="C:\\Users\\WIN\\Desktop\\qr";
             DatHoaDonRepository2 datHoaDonRepository2=new DatHoaDonRepository2();
             ArrayList<DatHoaDonRequest> list=datHoaDonRepository2.getAll();
         
             File directoryFile=new File(desktop);
                 if(!directoryFile.exists()){
                    directoryFile.mkdirs();
            }
                 for (DatHoaDonRequest datHoaDonRequest : list) {
                 String barcodeText="Mã HĐ : "+datHoaDonRequest.getMaHd();
                 BufferedImage qrCodeImage=hoadonQRCodeImage(barcodeText);
                 String filePath=desktop + "HoaDon_"+ datHoaDonRequest.getId()+ ".png";
                     saveQRCodeImage(qrCodeImage, filePath);
                     System.out.println("QR hóa đơn : "+ datHoaDonRequest.getId());
             }
         
         } catch (Exception e) {
         }
     }
     
     public ArrayList<DatHoaDonRequest> findById(int idHd){
         String sql = """
                    SELECT    
                              hd.ma,
                              hd.ngay_tao,
                              kh3.ho_ten,
                              SUM(hdct.gia_ban * so_luong) AS tong_gia_ban,
                              hd.tien_giam,
                              SUM((hdct.gia_ban * so_luong) - hd.tien_giam) AS tong_sau_giam,
                              hd.trang_thai,
                              hd.trang_thai_xoa,
                              hd.phuong_thuc_thanh_toan,
                              hd.id
                          FROM 
                              HoaDon3 hd
                          INNER JOIN 
                              HoaDonChiTiet3 hdct ON hd.id = hdct.id_hoa_don
                          INNER JOIN 
                              KhachHang3 kh3 ON hd.id_khach_hang = kh3.id
                          INNER JOIN 
                              TaiKhoan3 tk3 ON hd.id_tai_khoan = tk3.id 
                          WHERE hd.id=?
                          GROUP BY 
                              
                              hd.ma,
                              hd.ngay_tao,
                              kh3.ho_ten,
                              hd.tien_giam,
                              hd.trang_thai,
                              hd.trang_thai_xoa,
                              hd.phuong_thuc_thanh_toan,
                              hd.id
                          
                     """;
        ArrayList<DatHoaDonRequest> lists = new ArrayList<>();
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql)) {
                   ps.setObject(1, idHd);
                ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DatHoaDonRequest datHoaDonRequest=new DatHoaDonRequest();
                
                datHoaDonRequest.setMaHd(rs.getString(1));
                datHoaDonRequest.setThoiGian(rs.getDate(2));
                datHoaDonRequest.setKhachHang(rs.getString(3));
                datHoaDonRequest.setTongTienhang(rs.getDouble(4));
                datHoaDonRequest.setGiamGia(rs.getDouble(5));
                datHoaDonRequest.setThanhTien(rs.getDouble(6));
                datHoaDonRequest.setTrangThai(rs.getInt(7));
                datHoaDonRequest.setTrangThaixoa(rs.getBoolean(8));
                datHoaDonRequest.setPhuongThucTT(rs.getInt(9));
                datHoaDonRequest.setId(rs.getInt(10));
                lists.add(datHoaDonRequest);
               
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return lists;
     }
     
}
