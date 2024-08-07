/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.core.dattv.repositoris;

import com.app.common.configs.DBConnect;
import com.app.core.dattv.models.DatHoaDonChiTietModel;
import com.app.core.dattv.request.DatHoaDonRequest;
import com.app.core.dattv.views.DatHoaDonView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author WIN
 */
public class DatHoaDonChiTietRepository {

    DatHoaDonRequest datHoaDonRequest = null;

    public ArrayList<DatHoaDonChiTietModel> loadDatHoaDonChiTietModels() {
        //SUA LAI CAU QUERY

        String sql = """
                    SELECT
                                                     hdct.id,
                                                     hdct.gia_giam AS giaGiam,
                                                     sp.ma AS maSp,
                                                     sp.ten AS tenSP,
                                                     hdct.so_luong AS soLuong,
                                                     hdct.gia_nhap AS giaNhap,
                                                     hdct.gia_ban AS giaBan,
                                                     (hdct.so_luong * hdct.gia_ban) AS thanhTien,
                                                     SUM(hdct.so_luong * hdct.gia_ban) OVER (PARTITION BY hd.id) AS tongTienhang,
                                                     ISNULL(kh.ho_ten, N'Khách hàng lẻ') AS tenKhachHang,
                                                     tk.ho_ten AS tenNV,
                                                     hd.ma AS maHd,
                                                     hd.ngay_tao AS ngayTao,
                                                     hd.trang_thai AS trangThai,
                                                     SUM(hdct.so_luong) OVER (PARTITION BY hd.id) AS tongSoluong
                                                 FROM
                                                     HoaDonChiTiet hdct
                                                 INNER JOIN
                                                     HoaDon hd ON hd.id = hdct.id_hoa_don
                                                 INNER JOIN
                                                     SanPhamChiTiet spct ON hdct.id_san_pham_chi_tiet = spct.id
                                                 INNER JOIN
                                                     SanPham sp ON spct.id_san_pham = sp.id
                                                 LEFT JOIN
                                                     KhachHang kh ON hd.id_khach_hang = kh.id
                                                 INNER JOIN
                                                     TaiKhoan tk ON hd.id_tai_khoan = tk.id
                                                 WHERE
                                                     hd.trang_thai_xoa != 1
                                                 ORDER BY
                                                     hd.ngay_tao DESC;   		                 
                                              		
                     """;
        ArrayList<DatHoaDonChiTietModel> lists = new ArrayList<>();
        try (Connection con = DBConnect.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                DatHoaDonChiTietModel datHoaDonChiTietModel = new DatHoaDonChiTietModel();

                datHoaDonChiTietModel.setId(rs.getInt(1));
                datHoaDonChiTietModel.setGiaGiam(rs.getInt(2));
                datHoaDonChiTietModel.setMaSp(rs.getString(3));
                datHoaDonChiTietModel.setTenSP(rs.getString(4));
                datHoaDonChiTietModel.setSoLuong(rs.getInt(5));
                datHoaDonChiTietModel.setGiaNhap(rs.getInt(6));
                datHoaDonChiTietModel.setGiaBan(rs.getInt(7));
                datHoaDonChiTietModel.setThanhTien(rs.getInt(8));
                datHoaDonChiTietModel.setTongTienhang(rs.getInt(9));
                datHoaDonChiTietModel.setTenKhachHang(rs.getString(10));

                datHoaDonChiTietModel.setTenNV(rs.getString(11));
                datHoaDonChiTietModel.setMaHd(rs.getString(12));
                datHoaDonChiTietModel.setNgayTao(rs.getDate(13));
                datHoaDonChiTietModel.setTrangThai(rs.getInt(14));

                lists.add(datHoaDonChiTietModel);

            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return lists;
    }

    public ArrayList<DatHoaDonChiTietModel> loadDatHoaDonChiTietTable(String mahd) {
        //SUA LAI CAU QUERY

        String sql = """
     SELECT
             hdct.id,
             hdct.gia_giam AS giaGiam,
             sp.ma AS maSp,
             sp.ten AS tenSP,
             hdct.so_luong AS soLuong,
             hdct.gia_nhap AS giaNhap,
             hdct.gia_ban AS giaBan,
             (hdct.so_luong * hdct.gia_ban) AS thanhTien,
             SUM(hdct.so_luong * hdct.gia_ban) OVER (PARTITION BY hd.id) AS tongTienhang,
             ISNULL(kh.ho_ten, N'Khách hàng lẻ') AS tenKhachHang,
             tk.ho_ten AS tenNV,
             hd.ma AS maHd,
             hd.ngay_tao AS ngayTao,
             hd.trang_thai AS trangThai,
             SUM(hdct.so_luong) OVER (PARTITION BY hd.id) AS tongSoluong
         FROM
             HoaDonChiTiet hdct
         INNER JOIN
             HoaDon hd ON hd.id = hdct.id_hoa_don
         INNER JOIN
             SanPhamChiTiet spct ON hdct.id_san_pham_chi_tiet = spct.id
         INNER JOIN
             SanPham sp ON spct.id_san_pham = sp.id
         LEFT JOIN
             KhachHang kh ON hd.id_khach_hang = kh.id
         INNER JOIN
             TaiKhoan tk ON hd.id_tai_khoan = tk.id
         WHERE
             hd.trang_thai_xoa != 1 and hd.ma=?
         ORDER BY
             hd.ngay_tao DESC;	 
                     """;
        ArrayList<DatHoaDonChiTietModel> lists = new ArrayList<>();
        try (Connection con = DBConnect.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, mahd);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                DatHoaDonChiTietModel datHoaDonChiTietModel = new DatHoaDonChiTietModel();

                datHoaDonChiTietModel.setId(rs.getInt(1));
                datHoaDonChiTietModel.setGiaGiam(rs.getInt(2));
                datHoaDonChiTietModel.setMaSp(rs.getString(3));
                datHoaDonChiTietModel.setTenSP(rs.getString(4));
                datHoaDonChiTietModel.setSoLuong(rs.getInt(5));
                datHoaDonChiTietModel.setGiaNhap(rs.getInt(6));
                datHoaDonChiTietModel.setGiaBan(rs.getInt(7));
                datHoaDonChiTietModel.setThanhTien(rs.getInt(8));
                datHoaDonChiTietModel.setTongTienhang(rs.getInt(9));
                datHoaDonChiTietModel.setTenKhachHang(rs.getString(10));

                datHoaDonChiTietModel.setTenNV(rs.getString(11));
                datHoaDonChiTietModel.setMaHd(rs.getString(12));
                datHoaDonChiTietModel.setNgayTao(rs.getDate(13));
                datHoaDonChiTietModel.setTrangThai(rs.getInt(14));
                datHoaDonChiTietModel.setTongSoluong(rs.getInt(15));

                lists.add(datHoaDonChiTietModel);

            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return lists;
    }

    public void tongSoLuong(String maHd) {
        String sql = """
            SELECT	
                           
                            sum(hdct.so_luong)
              FROM HoaDonChiTiet hdct
                            INNER JOIN HoaDon hd ON hd.id = hdct.id_hoa_don
               where hd.ma=?	 
              
                     """;
        ArrayList<DatHoaDonChiTietModel> lists = new ArrayList<>();
        try (Connection con = DBConnect.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, maHd);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int tongSoLuong=rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }

    }
}
