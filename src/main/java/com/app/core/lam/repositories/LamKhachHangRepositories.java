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
import com.app.core.lam.models.LamKhachHangModels;
import java.time.LocalDateTime;

/**
 *
 * @author Admin
 */
public class LamKhachHangRepositories {

    public ArrayList<LamKhachHangModels> getKH() {
        String sql = """
                     SELECT [id]
                           ,[ho_ten]
                           ,[sdt]
                           ,[gioi_tinh]
                           ,[dia_chi]
                           ,[ngay_tao]
                           ,[trang_thai_xoa]
                       FROM [dbo].[KhachHang]
                     """;
        ArrayList<LamKhachHangModels> listRPKH = new ArrayList<>();
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                LamKhachHangModels kh = new LamKhachHangModels();
                kh.setIdKH(rs.getInt(1));
                kh.setTenKH(rs.getString(2));
                kh.setSoDienThoai(rs.getString(3));
                kh.setGioiTinh(rs.getBoolean(4));
                kh.setDiaChi(rs.getString(5));
                kh.setNgayTao(rs.getObject(6, LocalDateTime.class));
                kh.setTrangThaiXoa(rs.getBoolean(7));
                listRPKH.add(kh);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listRPKH;
    }

    public boolean addKhachHang(LamKhachHangModels kh) {
        String sql = """
                 INSERT INTO [dbo].[KhachHang]
                            ([ho_ten]
                            ,[sdt]
                            ,[gioi_tinh]
                            ,[dia_chi]
                            ,[trang_thai_xoa])
                      VALUES
                            (?,?,?,?,?)
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

    public boolean updateKhachHang(LamKhachHangModels kh) {
        String sql = """
                     UPDATE [dbo].[KhachHang]
                        SET [ho_ten] = ?
                           ,[sdt] = ?
                           ,[gioi_tinh] = ?
                           ,[dia_chi] = ?
                           ,[trang_thai_xoa] = ?
                      WHERE  id = ?
                     """;
        int check = 0;
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, kh.getTenKH());
            ps.setString(2, kh.getSoDienThoai());
            ps.setBoolean(3, kh.isGioiTinh());
            ps.setString(4, kh.getDiaChi());
            ps.setBoolean(5, kh.isTrangThaiXoa());
            ps.setInt(6, kh.getIdKH());
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
}
