/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.core.dattv.repositoris;

import com.app.common.configs.DBConnect;
import com.app.common.helper.JbdcHelper;
import com.app.core.dattv.request.DatHoaDonRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
                     kh3.ho_ten,
                     SUM(hdct.gia_ban * so_luong) AS tong_gia_ban,
                     hd.tien_giam,
                     SUM((hdct.gia_ban * so_luong) - hd.tien_giam) AS tong_sau_giam,
                     hd.trang_thai,
                 	hd.trang_thai_xoa,
                 	hd.phuong_thuc_thanh_toan
                 FROM 
                     HoaDon3 hd
                 INNER JOIN 
                     HoaDonChiTiet3 hdct ON hd.id = hdct.id_hoa_don
                 INNER JOIN 
                     KhachHang3 kh3 ON hd.id_khach_hang = kh3.id
                 INNER JOIN 
                     TaiKhoan3 tk3 ON hd.id_tai_khoan = tk3.id 
                 GROUP BY 
                 hd.ma,
                 hd.ngay_tao,
                 kh3.ho_ten,
                 hd.tien_giam,
                 hd.trang_thai,
                 hd.trang_thai_xoa,
                 hd.phuong_thuc_thanh_toan;
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
                datHoaDonRequest.setTrangThai(rs.getBoolean(7));
                datHoaDonRequest.setTrangThaixoa(rs.getBoolean(8));
                datHoaDonRequest.setPhuongThucTT(rs.getInt(9));
                
                lists.add(datHoaDonRequest);
               
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return lists;
    }
    public ArrayList<DatHoaDonRequest> search(String keyword ) {
       
        String sql = """
                 select HoaDon1.ma,ngay_tao,tenKhachhang,tongtien,tien_giam,thanhtoan,trang_thai,trang_thai_xoa,phuong_thuc_thanh_toan from HoaDon1
                     where ma like ? or tenNV like ?
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
                datHoaDonRequest.setTrangThai(rs.getBoolean(7));
                datHoaDonRequest.setTrangThaixoa(rs.getBoolean(8));
                datHoaDonRequest.setPhuongThucTT(rs.getInt(9));
                
                lists.add(datHoaDonRequest);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return lists;
    }
    public ArrayList<DatHoaDonRequest> locNhanVien(Integer id) {
        String sql = """
                 SELECT cts.Id ,sp.Ma ,sp.Ten ,
                        cts.NamBH ,cts.MoTa ,
                        ms.Ten ,n.Ten ,ds.Ten ,
                        cts.SoLuongTon ,cts.GiaBan 
                      FROM  ChiTietSP cts ,MauSac ms ,
                        NSX n ,DongSP ds ,SanPham sp 
                      WHERE cts.IdSP =sp.Id 
                      AND cts.IdMauSac =ms.Id 
                      AND cts.IdNsx =n.Id 
                      AND cts.IdDongSP =ds.Id
                     AND ds.Id = ?
                     """;
        ArrayList<DatHoaDonRequest> lists = new ArrayList<>();
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DatHoaDonRequest datHoaDonRequest=new DatHoaDonRequest();
                
                datHoaDonRequest.setMaHd(rs.getString(1));
                datHoaDonRequest.setThoiGian(rs.getDate(2));
                datHoaDonRequest.setKhachHang(rs.getString(3));
                datHoaDonRequest.setTongTienhang(rs.getDouble(4));
                datHoaDonRequest.setGiamGia(rs.getDouble(5));
                datHoaDonRequest.setThanhTien(rs.getDouble(6));
                datHoaDonRequest.setTrangThai(rs.getBoolean(7));
                datHoaDonRequest.setTrangThaixoa(rs.getBoolean(8));
                datHoaDonRequest.setPhuongThucTT(rs.getInt(9));
                
                lists.add(datHoaDonRequest);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return lists;
    }
    public ArrayList<DatHoaDonRequest> locData(int idNv,int phuongThucTT,int trangThai) {
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
                                      	hd.phuong_thuc_thanh_toan
                                      FROM 
                                          HoaDon3 hd
                                      INNER JOIN 
                                          HoaDonChiTiet3 hdct ON hd.id = hdct.id_hoa_don
                                      INNER JOIN 
                                          KhachHang3 kh3 ON hd.id_khach_hang = kh3.id
                                      INNER JOIN 
                                          TaiKhoan3 tk3 ON hd.id_tai_khoan = tk3.id 
                     				WHERE  
                     					( hd.phuong_thuc_thanh_toan=? OR ? is null)
                     					AND( hd.trang_thai=? OR  ? is null)
                     					AND( hd.id_tai_khoan=? OR ? is null)
                     				GROUP BY 
                                          hd.ma,
                                          hd.ngay_tao,
                                          kh3.ho_ten,
                                          hd.tien_giam,
                                          hd.trang_thai,
                                      	hd.trang_thai_xoa,
                                      	hd.phuong_thuc_thanh_toan;
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
            
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DatHoaDonRequest datHoaDonRequest=new DatHoaDonRequest();
                
                datHoaDonRequest.setMaHd(rs.getString(1));
                datHoaDonRequest.setThoiGian(rs.getDate(2));
                datHoaDonRequest.setKhachHang(rs.getString(3));
                datHoaDonRequest.setTongTienhang(rs.getDouble(4));
                datHoaDonRequest.setGiamGia(rs.getDouble(5));
                datHoaDonRequest.setThanhTien(rs.getDouble(6));
                datHoaDonRequest.setTrangThai(rs.getBoolean(7));
                datHoaDonRequest.setTrangThaixoa(rs.getBoolean(8));
                datHoaDonRequest.setPhuongThucTT(rs.getInt(9));
                
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
                datHoaDonRequest.setTrangThai(rs.getBoolean(7));
                datHoaDonRequest.setTrangThaixoa(rs.getBoolean(8));
                datHoaDonRequest.setPhuongThucTT(rs.getInt(9));
        

                
       

        return datHoaDonRequest;
    }
    public List<DatHoaDonRequest> selectTrangThai(int keyword) {
        String sql = "SELECT \n" +
"                                          hd.ma,\n" +
"                                          hd.ngay_tao,\n" +
"                                          kh3.ho_ten,\n" +
"                                          SUM(hdct.gia_ban * so_luong) AS tong_gia_ban,\n" +
"                                          hd.tien_giam,\n" +
"                                          SUM((hdct.gia_ban * so_luong) - hd.tien_giam) AS tong_sau_giam,\n" +
"                                          hd.trang_thai,\n" +
"                                      	hd.trang_thai_xoa,\n" +
"                                      	hd.phuong_thuc_thanh_toan\n" +
"                                      FROM \n" +
"                                          HoaDon3 hd\n" +
"                                      INNER JOIN \n" +
"                                          HoaDonChiTiet3 hdct ON hd.id = hdct.id_hoa_don\n" +
"                                      INNER JOIN \n" +
"                                          KhachHang3 kh3 ON hd.id_khach_hang = kh3.id\n" +
"                                      INNER JOIN \n" +
"                                          TaiKhoan3 tk3 ON hd.id_tai_khoan = tk3.id \n" +
"                     				WHERE  \n" +
"                     					hd.trang_thai_xoa=0 And hd.trang_thai=?\n" +
"                     				GROUP BY \n" +
"                                          hd.ma,\n" +
"                                          hd.ngay_tao,\n" +
"                                          kh3.ho_ten,\n" +
"                                          hd.tien_giam,\n" +
"                                          hd.trang_thai,\n" +
"                                      	hd.trang_thai_xoa,\n" +
"                                      	hd.phuong_thuc_thanh_toan;";

        return select(sql, keyword);
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
    public List<DatHoaDonRequest> selectThanhToan(int keyword,int trangThai) {
        String sql = "SELECT \n" +
"                                          hd.ma,\n" +
"                                          hd.ngay_tao,\n" +
"                                          kh3.ho_ten,\n" +
"                                          SUM(hdct.gia_ban * so_luong) AS tong_gia_ban,\n" +
"                                          hd.tien_giam,\n" +
"                                          SUM((hdct.gia_ban * so_luong) - hd.tien_giam) AS tong_sau_giam,\n" +
"                                          hd.trang_thai,\n" +
"                                      	hd.trang_thai_xoa,\n" +
"                                      	hd.phuong_thuc_thanh_toan\n" +
"                                      FROM \n" +
"                                          HoaDon3 hd\n" +
"                                      INNER JOIN \n" +
"                                          HoaDonChiTiet3 hdct ON hd.id = hdct.id_hoa_don\n" +
"                                      INNER JOIN \n" +
"                                          KhachHang3 kh3 ON hd.id_khach_hang = kh3.id\n" +
"                                      INNER JOIN \n" +
"                                          TaiKhoan3 tk3 ON hd.id_tai_khoan = tk3.id \n" +
"                     				WHERE  \n" +
"                     					hd.trang_thai_xoa=0 and hd.phuong_thuc_thanh_toan=?And hd.trang_thai=?\n" +
"										\n" +
"                     				GROUP BY \n" +
"                                          hd.ma,\n" +
"                                          hd.ngay_tao,\n" +
"                                          kh3.ho_ten,\n" +
"                                          hd.tien_giam,\n" +
"                                          hd.trang_thai,\n" +
"                                      	hd.trang_thai_xoa,\n" +
"                                      	hd.phuong_thuc_thanh_toan;";
        return select(sql, keyword,trangThai );
    }
}
