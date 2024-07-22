package com.app.core.inuha.repositories;

import com.app.common.helper.JbdcHelper;
import com.app.common.infrastructure.interfaces.IDAOinterface;
import com.app.common.infrastructure.request.FillterRequest;
import com.app.core.inuha.models.InuhaDanhMucModel;
import com.app.core.inuha.models.InuhaTaiKhoanModel;
import com.app.core.inuha.request.InuhaFillterTaiKhoanRequest;
import com.app.utils.TimeUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author InuHa
 */
public class InuhaDanhMucRepository implements IDAOinterface<InuhaDanhMucModel, Integer> {

    @Override
    public int insert(InuhaDanhMucModel model) throws SQLException {
        int result = 0;
        String query = """
            INSERT INTO DanhMuc(ten, ngay_tao)
            VALUES (?, ?)
        """;
        try {
            Object[] args = new Object[] {
                model.getTen(),
                TimeUtils.currentDate()
            };
            result = JbdcHelper.updateAndFlush(query, args);
        } catch(Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }

        return result;
    }

    @Override
    public int update(InuhaDanhMucModel model) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean has(Integer id) throws SQLException {
        String query = "SELECT TOP(1) 1 FROM DanhMuc WHERE id = ? AND trang_thai_xoa = 0";
        try {
            return JbdcHelper.value(query, id) != null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
    }

    public boolean has(String name) throws SQLException {
        String query = "SELECT TOP(1) 1 FROM DanhMuc WHERE ten LIKE ? AND trang_thai_xoa = 0";
        try {
            return JbdcHelper.value(query, name) != null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
    }
        
    @Override
    public Optional<InuhaDanhMucModel> getById(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Set<InuhaDanhMucModel> selectAll() throws SQLException {
        Set<InuhaDanhMucModel> list = new LinkedHashSet<>();
        ResultSet resultSet = null;

        String query = """
            SELECT 
                *,
                ROW_NUMBER() OVER (ORDER BY id DESC) AS stt
            FROM DanhMuc
            WHERE trang_thai_xoa = 0
            ORDER BY id DESC 
        """;

        try {
            resultSet = JbdcHelper.query(query);
            while(resultSet.next()) {
                InuhaDanhMucModel model = InuhaDanhMucModel.builder()
                    .stt(resultSet.getInt("stt"))
                    .ten(resultSet.getString("ten"))
                    .ngayTao(resultSet.getString("ngay_tao"))
                    .ngayCapNhat(resultSet.getString("ngay_cap_nhat"))
                    .build();
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
    public Set<InuhaDanhMucModel> selectPage(FillterRequest request) throws SQLException {
        Set<InuhaDanhMucModel> list = new LinkedHashSet<>();
        ResultSet resultSet = null;

        String query = """
            WITH TableCTE AS (
                SELECT
                    *,
                    ROW_NUMBER() OVER (ORDER BY id DESC) AS stt
                FROM DanhMuc
            )
            SELECT *
            FROM TableCTE
            WHERE trang_thai_xoa = 0 AND (stt BETWEEN ? AND ?)
        """;

        int[] offset = FillterRequest.getOffset(request.getPage(), request.getSize());
        int start = offset[0];
        int limit = offset[1];

        Object[] args = new Object[] {
            start,
            limit
        };

        try {
            resultSet = JbdcHelper.query(query, args);
            while(resultSet.next()) {
                InuhaDanhMucModel model = InuhaDanhMucModel.builder()
                        .stt(resultSet.getInt("stt"))
                        .ten(resultSet.getString("ten"))
                        .ngayTao(resultSet.getString("ngay_tao"))
                        .ngayCapNhat(resultSet.getString("ngay_cap_nhat"))
                        .build();
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
        int totalPages = 0;
        int totalRows = 0;

        String query = "SELECT COUNT(*) FROM DanhMuc";

        try {
            totalRows = (int) JbdcHelper.value(query);
            totalPages = (int) Math.ceil((double) totalRows / request.getSize());
        } catch(Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
        return totalPages;
    }
    
}
