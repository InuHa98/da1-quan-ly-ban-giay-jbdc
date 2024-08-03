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
    DatHoaDonRequest datHoaDonRequest= null;
    
    public ArrayList<DatHoaDonChiTietModel> loadDatHoaDonChiTietModels() {
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
                     					kh3.ho_ten,
                     					tk3.ho_ten,
                 						hd.ma,
                     					hd.ngay_tao,
                     					hd.trang_thai	
                                       
                                      FROM 
                                          HoaDonChiTiet3 hdct
                                      INNER JOIN 
                                          HoaDon3 hd ON hd.id = hdct.id_hoa_don
                                      INNER JOIN 
                                          KhachHang3 kh3 ON hd.id_khach_hang = kh3.id
                                      INNER JOIN 
                                          TaiKhoan3 tk3 ON hd.id_tai_khoan = tk3.id 
                     					 
                                      GROUP BY 
                     					 hdct.id , 
                 						 gia_giam ,
                     					 hdct.id_san_pham,
                     					 hdct.tensp,
                     					 so_luong ,
                     					 gia_nhap,
                     					 gia_ban,
                     					
                     					kh3.ho_ten,
                     					tk3.ho_ten,
                 						hd.ma,
                     					hd.ngay_tao,
                     					hd.trang_thai	                        
                                                               		                 
                                              		
                     """;
        ArrayList<DatHoaDonChiTietModel> lists = new ArrayList<>();
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql)) {
                
                ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                
                DatHoaDonChiTietModel datHoaDonChiTietModel=new DatHoaDonChiTietModel();
                
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
                                                             					kh3.ho_ten,
                                                             					tk3.ho_ten,
                                                         						hd.ma,
                                                             					hd.ngay_tao,
                                                             					hd.trang_thai	
                                                                               
                                                                              FROM 
                                                                                  HoaDonChiTiet3 hdct
                                                                              INNER JOIN 
                                                                                  HoaDon3 hd ON hd.id = hdct.id_hoa_don
                                                                              INNER JOIN 
                                                                                  KhachHang3 kh3 ON hd.id_khach_hang = kh3.id
                                                                              INNER JOIN 
                                                                                  TaiKhoan3 tk3 ON hd.id_tai_khoan = tk3.id 
                                                             				where hd.ma=?	 
                                                                              GROUP BY 
                                                             					 hdct.id , 
                                                         						 gia_giam ,
                  	 hdct.id_san_pham,
               hdct.tensp,
         so_luong ,
         gia_nhap,
         gia_ban,

	kh3.ho_ten,
        tk3.ho_ten,
		hd.ma,
        hd.ngay_tao,
 	hd.trang_thai	 
                     """;
        ArrayList<DatHoaDonChiTietModel> lists = new ArrayList<>();
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setObject(1, mahd);
                ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                
                DatHoaDonChiTietModel datHoaDonChiTietModel=new DatHoaDonChiTietModel();
                
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
}
