/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.core.lam.repositories;

import com.app.common.configs.DBConnect;
import com.app.core.lam.models.LamLichSuModels;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class LamLichSuRepositories {

    public ArrayList<LamLichSuModels> getLS() {
        String sql = """
                 SELECT KhachHang.id
                       ,HoaDon.ma
                       ,HoaDonChiTiet.gia_ban
                       ,HoaDonChiTiet.so_luong
                       ,HoaDon.ngay_tao
                       ,HoaDon.trang_thai_xoa
                   FROM HoaDon
                   INNER JOIN HoaDonChiTiet ON HoaDon.id = HoaDonChiTiet.id_hoa_don
                   INNER JOIN KhachHang ON HoaDon.id_khach_hang = KhachHang.id
                  WHERE HoaDon.trang_thai_xoa = ?
                 """;
        ArrayList<LamLichSuModels> listRPLS = new ArrayList<>();
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LamLichSuModels ls = new LamLichSuModels();
                ls.setIdKH(rs.getInt(1));
                ls.setMaHD(rs.getString(2));
                ls.setGiaBan(rs.getDouble(3));
                ls.setSoLuong(rs.getInt(4));
                ls.setNgayMua(rs.getObject(5, LocalDateTime.class));
                ls.setTrangThaiXoa(rs.getBoolean(6));
                listRPLS.add(ls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listRPLS;
    }

    public boolean updateLichSu(String maHD) {
        String sql = """
            UPDATE HoaDon
            SET gia_ban = ?, so_luong = ?, trang_thai_xoa = ?
            WHERE ma = ?
        """;

        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maHD);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteHoaDon(String maHD) {
        String sql = """
            DELETE FROM HoaDon
            WHERE ma = ?
        """;

        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maHD);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
