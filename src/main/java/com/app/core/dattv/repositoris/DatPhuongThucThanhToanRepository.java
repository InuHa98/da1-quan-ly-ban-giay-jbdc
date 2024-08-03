/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.core.dattv.repositoris;

import com.app.common.configs.DBConnect;
import com.app.core.dattv.models.DatPhuongThucThanhToanModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author WIN
 */
public class DatPhuongThucThanhToanRepository {
    public ArrayList<DatPhuongThucThanhToanModel> getAll() {
        String sql = """
                 select pt.id,pt.mota From phuongthucthanhtoan pt
                     """;
        ArrayList<DatPhuongThucThanhToanModel> lists = new ArrayList<>();
        try (Connection con = DBConnect.getInstance().getConnect();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DatPhuongThucThanhToanModel  datPhuongThucThanhToanModel=new DatPhuongThucThanhToanModel();
                        datPhuongThucThanhToanModel.setId(rs.getInt(1));
                        datPhuongThucThanhToanModel.setMoTa(rs.getString(2));
                        
                        lists.add(datPhuongThucThanhToanModel);
             
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return lists;}
}
