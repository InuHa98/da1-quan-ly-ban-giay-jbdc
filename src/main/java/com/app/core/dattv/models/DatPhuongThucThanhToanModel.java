/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.core.dattv.models;

/**
 *
 * @author WIN
 */
public class DatPhuongThucThanhToanModel {
    private int id;
    private String moTa;

    public DatPhuongThucThanhToanModel() {
    }

    public DatPhuongThucThanhToanModel(int id, String moTa) {
        this.id = id;
        this.moTa = moTa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    @Override
    public String toString() {
        return moTa.toString();
    }

   

    
    
    
}
