/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.core.dattv.repositoris;

import com.app.common.configs.DBConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.app.common.helper.JbdcHelper;
import com.app.common.infrastructure.interfaces.IDAOinterface;
import com.app.core.dattv.models.DatHoaDonModel;
import com.app.core.dattv.request.DatHoaDonRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import com.app.common.helper.JbdcHelper;
import com.app.common.infrastructure.interfaces.IDAOinterface;
import com.app.common.infrastructure.request.FillterRequest;
import com.app.core.dattv.request.DatFillerHoaDonRequest;
import com.app.utils.TimeUtils;
import java.util.Optional;

/**
 *
 * @author WIN
 */
public class DatHoaDonRepository implements IDAOinterface<DatHoaDonModel, Integer> {

    private final static String TABLE_NAME = "HoaDon";

    @Override
    public int insert(DatHoaDonModel model) throws SQLException {
        int result = 0;
        String query = String.format("""
            INSERT INTO %s( id_tai_khoan,
                            id_khach_hang,
                            id_phieu_giam_gia,
                            ma,
                            tien_giam, 
                            phuong_thuc_thanh_toan,
                            trang_thai,
                            trang_thai_xoa,
                            ngay_tao,ngay_cap_nhat
                             )
            VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """, TABLE_NAME);
        try {
            Object[] args = new Object[]{
                model.getId_tai_khoan(),
                model.getId_khach_hang(),
                model.getId_phieu_giam_gia(),
                model.getMaHd(),
                model.getPhuong_thuc_thanh_toan(),
                model.getTrangThai(),
                model.isTrangThaixoa(),
                TimeUtils.currentDate(),
                TimeUtils.currentDate(),};
            result = JbdcHelper.updateAndFlush(query, args);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }

        return result;
    }

    @Override
    public int update(DatHoaDonModel model) throws SQLException {
        int result = 0;
        String query = String.format("""
            UPDATE %s SET
                id_tai_khoan=?,
                id_khach_hang=?,
                id_phieu_giam_gia=?,
                ma=?,
                tien_giam=?, 
                phuong_thuc_thanh_toan=?,
                trang_thai=?,
                trang_thai_xoa=?,
                ngay_tao,ngay_cap_nhat=?
            WHERE id = ?
        """, TABLE_NAME);
        try {
            Object[] args = new Object[]{
                model.getId_tai_khoan(),
                model.getId_khach_hang(),
                model.getId_phieu_giam_gia(),
                model.getMaHd(),
                model.getPhuong_thuc_thanh_toan(),
                model.getTrangThai(),
                model.isTrangThaixoa(),
                TimeUtils.currentDate(),
                TimeUtils.currentDate(),
                model.getId()
            };
            result = JbdcHelper.updateAndFlush(query, args);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }

        return result;
    }

    @Override
    public int delete(Integer id) throws SQLException {
    int result = 0;
        String query = String.format("""
            DELETE FROM Hoadonchitiet WHERE id_san_pham = ?;
            DELETE FROM %s WHERE id = ?
        """, TABLE_NAME);// hoi? %s
        try {
            result = JbdcHelper.updateAndFlush(query, id, id);
        } catch(Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
        return result;    }

    @Override
    public boolean has(Integer id) throws SQLException {
        String query = String.format("SELECT TOP(1) 1 FROM %s WHERE id = ? AND trang_thai_xoa = 0", TABLE_NAME);
        try {
            return JbdcHelper.value(query, id) != null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }    }

    public boolean has(String name) throws SQLException {
        String query = String.format("SELECT TOP(1) 1 FROM %s WHERE ten LIKE ? AND trang_thai_xoa = 0", TABLE_NAME);
        try {
            return JbdcHelper.value(query, name) != null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
    }
     

    @Override
    public Optional<DatHoaDonModel> getById(Integer id) throws SQLException {
ResultSet resultSet = null;
        DatHoaDonModel model = null;

        String query = String.format("""
            SELECT
                
                
            FROM %s AS hd
                
            WHERE
                hd.id = ? AND
                hd.trang_thai_xoa = 0
        """, TABLE_NAME);// chua viet query

        try {
            resultSet = JbdcHelper.query(query, id);
            while(resultSet.next()) {
                model = buildData(resultSet, false);
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
        finally {
            JbdcHelper.close(resultSet);
        }

        return Optional.ofNullable(model);    }

    @Override
    public List<DatHoaDonModel> selectAll() throws SQLException {
        List<DatHoaDonModel> list = new ArrayList<>();
        ResultSet resultSet = null;

        String query = String.format("""
            SELECT
                sp.*,
                ROW_NUMBER() OVER (ORDER BY sp.id DESC) AS stt,
                
            FROM %s AS hd
                
            WHERE
                hd.trang_thai_xoa = 0
            ORDER BY hd.id DESC 
        """, TABLE_NAME);

        try {
            resultSet = JbdcHelper.query(query);
            while(resultSet.next()) {
                DatHoaDonModel model = buildData(resultSet);
                list.add(model);
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
        finally {
            JbdcHelper.close(resultSet);
        }

        return list;    }

    @Override
    public List<DatHoaDonModel> selectPage(FillterRequest request) throws SQLException {
        DatFillerHoaDonRequest filter = (DatFillerHoaDonRequest) request;
        
        List<DatHoaDonModel> list = new ArrayList<>();
        ResultSet resultSet = null;
        
        String query = String.format("""
            WITH TableCTE AS (
                SELECT
                    sp.*,
                    ROW_NUMBER() OVER (ORDER BY sp.id DESC) AS stt,
                    
                FROM %s AS sp
                    LEFT JOIN 
                    LEFT JOIN 
                    LEFT JOIN 
                    LEFT JOIN 
                    LEFT JOIN 
                WHERE
                    sp.trang_thai_xoa = 0 AND
                    (
                        (? IS NULL OR sp.ten LIKE ? OR sp.ma LIKE ?) AND
                        (COALESCE(?, 0) < 1 OR hd.id_danh_muc = ?) AND
                        (COALESCE(?, 0) < 1 OR hd.id_thuong_hieu = ?) AND
                        (COALESCE(?, 0) < 0 OR hd.trang_thai = ?)
                    )
            )
            SELECT *
            FROM TableCTE
            WHERE stt BETWEEN ? AND ?
        """, TABLE_NAME);

        int[] offset = FillterRequest.getOffset(filter.getPage(), filter.getSize());
        int start = offset[0];
        int limit = offset[1];

        Object[] args = new Object[] {
            filter.getKeyword(),
            String.format("%%%s%%", filter.getKeyword()),
            String.format("%%%s%%", filter.getKeyword()),
            filter.getIdNguoitao(),
            filter.getIdPhuongThucthanhtoan(),
            filter.getTrangThai(),
            filter.getTrangThai(),
            start,
            limit
        };

        try {
            resultSet = JbdcHelper.query(query, args);
            while(resultSet.next()) {
                DatHoaDonModel model = buildData(resultSet);
                list.add(model);
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
        finally {
            JbdcHelper.close(resultSet);
        }

        return list;
}

    @Override
    public int count(FillterRequest request) throws SQLException {
    DatFillerHoaDonRequest filter = (DatFillerHoaDonRequest) request;
        
        int totalPages = 0;
        int totalRows = 0;

        String query = String.format("""
            SELECT COUNT(*)
            FROM %s
            WHERE
                trang_thai_xoa = 0 AND  
                (
                    (? IS NULL OR ten LIKE ? OR ma LIKE ?) AND
                    (COALESCE(?, 0) < 1 OR id_danh_muc = ?) AND
                    (COALESCE(?, 0) < 1 OR id_thuong_hieu = ?) AND
                    (COALESCE(?, 0) < 0 OR trang_thai = ?)
                ) 
        """, TABLE_NAME);

        Object[] args = new Object[] { 
            filter.getKeyword(),
            String.format("%%%s%%", filter.getKeyword()),
            String.format("%%%s%%", filter.getKeyword()),
            filter.getIdNguoitao(),
            filter.getIdPhuongThucthanhtoan(),
            filter.getTrangThai(),
            filter.getTrangThai()
        };
        
        try {
            totalRows = (int) JbdcHelper.value(query, args);
            totalPages = (int) Math.ceil((double) totalRows / request.getSize());
        } catch(Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
        return totalPages;    }
        private DatHoaDonModel buildData(ResultSet resultSet) throws SQLException { 
        return buildData(resultSet, true);
    }

private DatHoaDonModel buildData(ResultSet resultSet, boolean addSTT) throws SQLException { 
        
        return DatHoaDonModel.builder()
            .id(resultSet.getInt("id"))
            .stt(addSTT ? resultSet.getInt("stt") : -1)
            .id_tai_khoan(resultSet.getInt("id_tai_khoan"))
            .id_khach_hang(resultSet.getInt("id_khach_hang"))
            .id_phieu_giam_gia(resultSet.getInt("id_phieu_giam_gia"))
            .maHd(resultSet.getString("maHd"))
            .tienGiam(resultSet.getInt("tienGiam"))
            .phuong_thuc_thanh_toan(resultSet.getInt("phuong_thuc_thanh_toan"))
            .trangThai(resultSet.getInt("trang_thai"))
            .ngayTao(resultSet.getDate("ngay_tao"))
            .ngayCapnhat(resultSet.getDate("ngay_cap_nhat"))
            .trangThaixoa(resultSet.getBoolean("trang_thai_xoa"))           
            .build();
    }

public String getLastCode() throws SQLException {
        String query = String.format("SELECT TOP(1) ma FROM %s ORDER BY id DESC", TABLE_NAME);
        try {
            return (String) JbdcHelper.value(query);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
    }
   
}



    

