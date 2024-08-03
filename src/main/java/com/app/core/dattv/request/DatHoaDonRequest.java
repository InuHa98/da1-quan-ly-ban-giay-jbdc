/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.core.dattv.request;

import java.util.Date;



/**
 *
 * @author WIN
 */

public class DatHoaDonRequest {
   
    private String maHd;
    private Date thoiGian;
    private String khachHang;
    private double tongTienhang;
    private double giamGia;
    private double thanhTien;
    private boolean trangThai;
    private int phuongThucTT;
    private boolean trangThaixoa;

    public DatHoaDonRequest() {
    }

    public DatHoaDonRequest(String maHd, Date thoiGian, String khachHang, double tongTienhang, double giamGia, double thanhTien, boolean trangThai, int phuongThucTT) {
        this.maHd = maHd;
        this.thoiGian = thoiGian;
        this.khachHang = khachHang;
        this.tongTienhang = tongTienhang;
        this.giamGia = giamGia;
        this.thanhTien = thanhTien;
        this.trangThai = trangThai;
        this.phuongThucTT = phuongThucTT;
        
    }

    public DatHoaDonRequest(boolean trangThaixoa) {
        this.trangThaixoa = trangThaixoa;
    }

 

    public String getMaHd() {
        return maHd;
    }

    public void setMaHd(String maHd) {
        this.maHd = maHd;
    }

    public Date getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(String khachHang) {
        this.khachHang = khachHang;
    }

    public double getTongTienhang() {
        return tongTienhang;
    }

    public void setTongTienhang(double tongTienhang) {
        this.tongTienhang = tongTienhang;
    }

    public double getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(double giamGia) {
        this.giamGia = giamGia;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public int getPhuongThucTT() {
        return phuongThucTT;
    }

    public void setPhuongThucTT(int phuongThucTT) {
        this.phuongThucTT = phuongThucTT;
    }

    public boolean isTrangThaixoa() {
        return trangThaixoa;
    }

    public void setTrangThaixoa(boolean trangThaixoa) {
        this.trangThaixoa = trangThaixoa;
    }

    

    

    @Override
    public String toString() {
        return "DatHoaDonRequest{" + "trangThaixoa=" + trangThaixoa + ", maHd=" + maHd + ", thoiGian=" + thoiGian + ", khachHang=" + khachHang + ", tongTien=" + tongTienhang + ", giamGia=" + giamGia + ", thanhTien=" + thanhTien + ", trangThai=" + trangThai +", phuongThucthanhtoan= "+ phuongThucTT + '}';
    }

    

    
    
    

    
    
}
