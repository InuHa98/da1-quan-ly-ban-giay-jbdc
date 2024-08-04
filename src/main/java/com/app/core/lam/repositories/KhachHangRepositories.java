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
import java.time.LocalDateTime;

/**
 *
 * @author Admin
 */
public class KhachHangRepositories {

    public ArrayList<LichSuModels> getLS() {
        String sql = """
                 SELECT   
                    KhachHang.id,
                    HoaDon.ma,  
                    HoaDonChiTiet.gia_ban,  
                    HoaDonChiTiet.so_luong,  
                    KhachHang.ngay_tao,  
                    KhachHang.trang_thai_xoa
                 FROM   
                     HoaDon JOIN HoaDonChiTiet ON HoaDon.id = HoaDonChiTiet.id_hoa_don  
                 JOIN   
                     KhachHang ON HoaDon.id_khach_hang = KhachHang.id;
                        
                 """;
        ArrayList<LichSuModels> listRPLS = new ArrayList<>();
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                LichSuModels ls = new LichSuModels();
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
        System.out.println("Data Retrieved: " + listRPLS.size());
        return listRPLS;
    }

    public boolean deleteLichSu(String maHD) {
        String sqlDeleteHoaDon = "DELETE FROM HoaDon WHERE maHD = ?";

        int checkHoaDon = 0;

        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sqlDeleteHoaDon)) {

            ps.setString(1, maHD);
            checkHoaDon = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return checkHoaDon > 0;
    }

    public boolean updateLichSu(LichSuModels ls) {
        String sql = """
                    UPDATE LichSu
                    SET giaBan = ?, soLuong = ?, trangThaiXoa = ?
                    WHERE maHD = ?
                    """;
        int check = 0;
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, ls.getGiaBan());
            ps.setInt(2, ls.getSoLuong());
            ps.setBoolean(3, ls.isTrangThaiXoa());
            ps.setString(4, ls.getMaHD());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public ArrayList<KhachHangModels> getKH() {
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
                kh.setNgayTao(rs.getObject(6, LocalDateTime.class));
                kh.setTrangThaiXoa(rs.getBoolean(7));
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

    public boolean updateKhachHang(KhachHangModels kh) {
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
            if (kh != null) {
                int id = kh.getIdKH();
                ps.setInt(6, id);
            } else {
                System.out.println("KhachHangModels is null");
            }
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
