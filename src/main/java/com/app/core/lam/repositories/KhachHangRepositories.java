/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.core.lam.repositories;

import com.app.common.configs.DBConnect;
import com.app.core.lam.models.DiaChiModels;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.app.core.lam.models.KhachHangModels;

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
                kh.setTrangThaiXoa(rs.getBoolean(5));
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
                                ([ho_ten]
                                ,[sdt]
                                ,[gioi_tinh]
                                ,[trang_thai_xoa])
                          VALUES
                                (?,?,?,?);
                     """;
        int check = 0;
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, kh.getTenKH());
            ps.setString(2, kh.getSoDienThoai());
            ps.setBoolean(3, kh.isGioiTinh());
            ps.setBoolean(4, kh.isTrangThaiXoa());
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
                           ,[sdt] = ?
                           ,[gioi_tinh] = ?
                           ,[trang_thai_xoa] = ?
                      WHERE id = ?
                     """;
        int check = 0;
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, kh.getTenKH());
            ps.setString(2, kh.getSoDienThoai());
            ps.setBoolean(3, kh.isGioiTinh());
            ps.setBoolean(4, kh.isTrangThaiXoa());
            ps.setInt(5, kh.getIdKH());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean deleteKhachHang(int idKH) {
        String sql = """
                     DELETE FROM [dbo].[KhachHang]
                           WHERE id = ?
                     """;
        int check = 0;
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idKH);

            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public ArrayList<DiaChiModels> getDC() {
        String sqlDC = """
                     SELECT 
                     	DiaChi.id AS dia_chi_id,
                        KhachHang.id AS khach_hang_id,
                     	DiaChi.dia_chi,
                        DiaChi.trang_thai_xoa
                     FROM 
                     KhachHang LEFT JOIN DiaChi ON KhachHang.id = DiaChi.id_khach_hang;
                     """;
        ArrayList<DiaChiModels> listRPDC = new ArrayList<>();
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sqlDC);
                ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                DiaChiModels dc = new DiaChiModels();
                dc.setIdDC(rs.getInt(1));
                dc.setIdKH(rs.getInt(2));
                dc.setDiaChi(rs.getString(3));
                dc.setTrangThaiXoa(rs.getBoolean(4));
                listRPDC.add(dc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listRPDC;
    }

    public boolean addDiaChi(DiaChiModels dc) {
        String checkSql = "SELECT COUNT(*) FROM [dbo].[DiaChi] WHERE id_khach_hang = ?";
        String insertSql = """
        INSERT INTO [dbo].[DiaChi]
            ([id_khach_hang], [dia_chi], [trang_thai_xoa])
        VALUES (?, ?, ?);
    """;

        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement checkPs = con.prepareStatement(checkSql)) {
            checkPs.setObject(1, dc.getIdKH());
            ResultSet rs = checkPs.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // If id_khach_hang already exists, do not insert
                System.out.println("id_khach_hang already exists.");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // If id_khach_hang does not exist, proceed to insert
        int checkDC = 0;
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(insertSql)) {
            ps.setObject(1, dc.getIdKH());
            ps.setObject(2, dc.getDiaChi());
            ps.setObject(3, dc.isTrangThaiXoa());
            checkDC = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return checkDC > 0;
    }

    public boolean updateDiaChi(DiaChiModels dc) {
        String sql = """
                     
                     """;
        int check = 0;
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql)) {

            int rowsUpdatedDC = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean deleteDiaChi(int idDC) {
        String sql = """
                     
                     """;
        int check = 0;
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idDC);
            int rowsDeletedDC = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }
}
