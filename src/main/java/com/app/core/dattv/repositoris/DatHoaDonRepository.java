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
public class DatHoaDonRepository implements IDAOinterface<DatHoaDonRequest, Integer> {
    private static DatHoaDonRepository instance = null;
    
    public static DatHoaDonRepository getInstance() { 
	if (instance == null) { 
	    instance = new DatHoaDonRepository();
	}
	return instance;
    }

    public DatHoaDonRepository() {
    }
    
    @Override
    public int insert(DatHoaDonRequest model) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(DatHoaDonRequest model) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean has(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<DatHoaDonRequest> getById(Integer id) throws SQLException {
        ResultSet rs = null;
        DatHoaDonRequest model = null;

        String sql = ("""
          WITH CTE_HoaDon AS (
                                   SELECT
                                       hd.ma,
                                       hd.ngay_tao,
                                       kh3.ho_ten,
                                       SUM(hdct.gia_ban * so_luong) AS tong_gia_ban,
                                       hd.tien_giam,
                                       SUM((hdct.gia_ban * so_luong) - hd.tien_giam) AS tong_sau_giam,
                                       hd.trang_thai,
                                       hd.trang_thai_xoa,
                                       hd.phuong_thuc_thanh_toan,
                                       hd.id,
                                   FROM 
                                       HoaDon3 hd
                                   INNER JOIN 
                                       HoaDonChiTiet3 hdct ON hd.id = hdct.id_hoa_don
                                   INNER JOIN 
                                       KhachHang3 kh3 ON hd.id_khach_hang = kh3.id
                                   INNER JOIN 
                                       TaiKhoan3 tk3 ON hd.id_tai_khoan = tk3.id 
                                   WHERE 
                                       hd.trang_thai_xoa = 0 AND
                                       hd.id=?
                                   GROUP BY 
                                       hd.ma,
                                       hd.ngay_tao,
                                       kh3.ho_ten,
                                       hd.tien_giam,
                                       hd.trang_thai,
                                       hd.trang_thai_xoa,
                                       hd.phuong_thuc_thanh_toan,
                                       hd.id
                               )
                              
        """);
        ArrayList<DatHoaDonRequest> lists = new ArrayList<>();
        try {
            Connection con = DBConnect.getInstance().getConnect();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, id);
            rs = ps.executeQuery();
            while(rs.next()) {
                DatHoaDonRequest datHoaDonRequest=new DatHoaDonRequest();
                
                datHoaDonRequest.setMaHd(rs.getString(1));
                datHoaDonRequest.setThoiGian(rs.getDate(2));
                datHoaDonRequest.setKhachHang(rs.getString(3));
                datHoaDonRequest.setTongTienhang(rs.getDouble(4));
                datHoaDonRequest.setGiamGia(rs.getDouble(5));
                datHoaDonRequest.setThanhTien(rs.getDouble(6));
                datHoaDonRequest.setTrangThai(rs.getInt(7));
                datHoaDonRequest.setTrangThaixoa(rs.getBoolean(8));
                datHoaDonRequest.setPhuongThucTT(rs.getInt(9));
                datHoaDonRequest.setId(rs.getInt(10));
                
                lists.add(datHoaDonRequest);
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
        finally {
            JbdcHelper.close(rs);
        }

        return Optional.ofNullable(model);
    }

    @Override
    public List<DatHoaDonRequest> selectAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<DatHoaDonRequest> selectPage(FillterRequest request) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int count(FillterRequest request) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}



    

