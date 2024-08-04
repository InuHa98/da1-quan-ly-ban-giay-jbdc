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
                                          WITH HoaDonTongTien AS (
                                               SELECT
                                                   hd.id AS hoa_don_id,
                                                   SUM(hdct.so_luong * hdct.gia_ban) AS Tong_tien
                                               FROM
                                                   HoaDonChiTiet hdct
                                               INNER JOIN
                                                   HoaDon hd ON hd.id = hdct.id_hoa_don
                                               GROUP BY
                                                   hd.id
                                           )
                                           SELECT	
                                               hdct.id,
                                               hdct.gia_giam,
                                               hdct.id_san_pham,
                                               hdct.tensp,
                                               hdct.so_luong,
                                               hdct.gia_nhap,
                                               hdct.gia_ban,
                                               (hdct.so_luong * hdct.gia_ban) AS thanh_tien,
                                               hdt.Tong_tien,
                                               ISNULL(kh.ho_ten, N'Khách hàng lẻ') AS ho_ten,
                                               tk.ho_ten AS nhan_vien_ho_ten,
                                               hd.ma,
                                               hd.ngay_tao,
                                               hd.trang_thai
                                           FROM 
                                               HoaDonChiTiet hdct
                                           INNER JOIN 
                                               HoaDon hd ON hd.id = hdct.id_hoa_don
                                           LEFT JOIN 
                                               KhachHang kh ON hd.id_khach_hang = kh.id
                                           INNER JOIN 
                                               TaiKhoan tk ON hd.id_tai_khoan = tk.id 
                                           INNER JOIN 
                                               HoaDonTongTien hdt ON hd.id = hdt.hoa_don_id
                                           GROUP BY 
                                               hdct.id,
                                               hdct.gia_giam,
                                               hdct.id_san_pham,
                                               hdct.tensp,
                                               hdct.so_luong,
                                               hdct.gia_nhap,
                                               hdct.gia_ban,
                                               hdt.Tong_tien,
                                               kh.ho_ten,
                                               tk.ho_ten,
                                               hd.ma,
                                               hd.ngay_tao,
                                               hd.trang_thai;                      
                                                               		                 
                                              		
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
              hdct.id , 
              gia_giam ,
              hdct.id_san_pham,
              hdct.tensp,
              so_luong ,
              gia_nhap,
              gia_ban,
              (hdct.so_luong*hdct.gia_ban) as 'thanh_tien',
              sum((hdct.so_luong*hdct.gia_ban)) as 'Tong_tien',
              ISNULL(kh.ho_ten, N'Khách hàng lẻ') AS ho_ten,
              tk.ho_ten,
              hd.ma,
              hd.ngay_tao,
              hd.trang_thai,
              sum(hdct.so_luong)
              FROM HoaDonChiTiet hdct
               INNER JOIN HoaDon hd ON hd.id = hdct.id_hoa_don
               Left join   KhachHang kh ON hd.id_khach_hang = kh.id
               INNER JOIN TaiKhoan tk ON hd.id_tai_khoan = tk.id 
               where hd.ma=?	 
                GROUP BY hdct.id , 
                         gia_giam ,
                       	hdct.id_san_pham,
     					hdct.tensp,                                               					 
     					so_luong ,
     					gia_nhap,
     					gia_ban,
     					kh.ho_ten,
     					tk.ho_ten,
     					hd.ma,
     					hd.ngay_tao,
      					hd.trang_thai	 
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
