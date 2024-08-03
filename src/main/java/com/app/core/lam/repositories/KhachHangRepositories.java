/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.core.lam.repositories;

import com.app.common.configs.DBConnect;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.app.core.lam.models.KhachHangModels;
import com.app.core.lam.models.LichSuModels;

/**
 *
 * @author Admin
 */
public class KhachHangRepositories {

    public ArrayList<KhachHangModels> getKH() {
        String sql = """
                     SELECT [id]
                           ,[ho_ten]
                           ,[sdt]
                           ,[gioi_tinh]
                           ,[dia_chi]
                           ,[trang_thai_xoa]
                       FROM [dbo].[KhachHang]
                     """;
        ArrayList<KhachHangModels> listRPKH = new ArrayList<>();
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                KhachHangModels kh = new KhachHangModels();
                kh.setIdKH(rs.getInt(1));
                kh.setTenKH(rs.getString(2));
                kh.setSoDienThoai(rs.getString(3));
                kh.setGioiTinh(rs.getBoolean(4));
                kh.setDiaChi(rs.getString(5));
                kh.setTrangThaiXoa(rs.getBoolean(6));
                listRPKH.add(kh);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listRPKH;
    }

    public boolean addKhachHang(KhachHangModels kh) {
        String sql = """
                 INSERT INTO [dbo].[KhachHang]
                    ([ho_ten], [sdt], [gioi_tinh], [dia_chi], [trang_thai_xoa])
                 VALUES
                    (?, ?, ?, ?, ?)
                 """;
        int check = 0;
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setString(1, kh.getTenKH());
            ps.setString(2, kh.getSoDienThoai());
            ps.setBoolean(3, kh.isGioiTinh());
            ps.setString(4, kh.getDiaChi());
            ps.setBoolean(5, kh.isTrangThaiXoa());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;

    }

    public boolean updateKhachHang(KhachHangModels kh) {
        String sql = """
                     UPDATE [dbo].[KhachHang]
                        SET [ho_ten] = ?
                           ,[gioi_tinh] = ?
                           ,[dia_chi] = ?
                           ,[trang_thai_xoa] = ?
                      WHERE  sdt = ?
                     """;
        int check = 0;
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, kh.getTenKH());
            ps.setBoolean(2, kh.isGioiTinh());
            ps.setString(3, kh.getDiaChi());
            ps.setBoolean(4, kh.isTrangThaiXoa());
            ps.setString(5, kh.getSoDienThoai());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean deleteKhachHang(String soDienThoai) {
        String sql = """
                     DELETE FROM [dbo].[KhachHang]
                           WHERE sdt = ?
                     """;
        int check = 0;
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, soDienThoai);

            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public ArrayList<LichSuModels> getLS() {
        String sql = """
                     SELECT 
                            [id]
                           ,[ho_ten]
                           ,[sdt]
                           ,[gioi_tinh]
                           ,[dia_chi]
                           ,[trang_thai_xoa]
                       FROM [dbo].[KhachHang]
                     """;
        ArrayList<LichSuModels> listRPLS = new ArrayList<>();
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                LichSuModels ls = new LichSuModels();
                ls.setTenKH(rs.getString(1));
                ls.setTenKH(rs.getString(2));
                ls.setSoDienThoai(rs.getString(3));
                ls.setGioiTinh(rs.getBoolean(4));
                ls.setDiaChi(rs.getString(5));
                ls.setTrangThaiXoa(rs.getBoolean(6));
                listRPLS.add(ls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listRPLS;
    }
    
    public boolean deleteLichSu(String soDienThoai) {
        return deleteKhachHang(soDienThoai);
    }
    
    public boolean updateLichSu(LichSuModels ls) {
        String sql = """
                     UPDATE [dbo].[KhachHang]
                        SET [ho_ten] = ?
                           ,[gioi_tinh] = ?
                           ,[dia_chi] = ?
                           ,[trang_thai_xoa] = ?
                      WHERE  sdt = ?
                     """;
        int check = 0;
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ls.getTenKH());
            ps.setBoolean(2, ls.isGioiTinh());
            ps.setString(3, ls.getDiaChi());
            ps.setBoolean(4, ls.isTrangThaiXoa());
            ps.setString(5, ls.getSoDienThoai());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }
}
