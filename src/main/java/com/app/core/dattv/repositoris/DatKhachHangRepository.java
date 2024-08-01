/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.core.dattv.repositoris;

import com.app.common.configs.DBConnect;
import com.app.core.dattv.models.DatKhachHangModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author WIN
 */
public class DatKhachHangRepository {
    public ArrayList<DatKhachHangModel> getAll() {
        String sql = """
                 
                     """;
        ArrayList<DatKhachHangModel> lists = new ArrayList<>();
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DatKhachHangModel  datKhachHangModel=new DatKhachHangModel();
                        datKhachHangModel.setId(rs.getInt(1));
                        datKhachHangModel.setTenKhachHang(rs.getString(2));
             
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return lists;
    }
}
