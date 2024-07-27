/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.core.lam.models;

/**
 *
 * @author Admin
 */
public class DiaChiModels extends KhachHangModels {

    private int idDC;

    private int idKH;

    private String diaChi;

    private boolean trangThaiXoa;

    public DiaChiModels() {
    }

    public DiaChiModels( String diaChi, boolean trangThaiXoa) {
        this.diaChi = diaChi;
        this.trangThaiXoa = trangThaiXoa;
    }

    public int getIdDC() {
        return idDC;
    }

    public void setIdDC(int idDC) {
        this.idDC = idDC;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    @Override
    public String toString() {
        return "DiaChiModels{" + "idDC=" + idDC + ", idKH=" + idKH + ", diaChi=" + diaChi + ", trangThaiXoa=" + trangThaiXoa + '}';
    }
    
}
