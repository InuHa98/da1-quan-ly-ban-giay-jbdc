/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.core.lam.models;

/**
 *
 * @author Admin
 */
public class LichSuModels {
    private int idKH;
    
    private String tenKH;
    
    private String soDienThoai;
    
    private boolean gioiTinh;
    
    private String diaChi;
    
    private boolean trangThaiXoa;

    public LichSuModels() {
    }

    public LichSuModels(String tenKH, String soDienThoai, boolean gioiTinh, String diaChi, boolean trangThaiXoa) {
        this.tenKH = tenKH;
        this.soDienThoai = soDienThoai;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.trangThaiXoa = trangThaiXoa;
    }

    public int getIdKH() {
        return idKH;
    }

    public void setIdKH(int idKH) {
        this.idKH = idKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public boolean isTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(boolean trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }

    @Override
    public String toString() {
        return "LichSuModels{" + "idKH=" + idKH + ", tenKH=" + tenKH + ", soDienThoai=" + soDienThoai + ", gioiTinh=" + gioiTinh + ", diaChi=" + diaChi + ", trangThaiXoa=" + trangThaiXoa + '}';
    }
    
}
