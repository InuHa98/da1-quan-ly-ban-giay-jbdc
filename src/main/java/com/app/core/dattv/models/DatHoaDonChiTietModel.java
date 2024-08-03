/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.core.dattv.models;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author WIN
 */
public class DatHoaDonChiTietModel {
    private int id;
    private int giaGiam;
    private String maSp;
    private String tenSP;
    private int soLuong;
    private int giaNhap;
    private int giaBan;
    private int thanhTien;
    private int tongTienhang;
    private String tenKhachHang;
    private String tenNV;
    private String maHd;
    private Date ngayTao;
    private int trangThai;
    private int tongSoluong;

    public DatHoaDonChiTietModel() {
    }

    public DatHoaDonChiTietModel(int id, int giaGiam, String maSp, String tenSP, int soLuong, int giaNhap, int giaBan, int thanhTien, int tongTienhang, String tenKhachHang, String tenNV, String maHd, Date ngayTao, int trangThai,int tongSoluong) {
        this.id = id;
        this.giaGiam=giaGiam;
        this.maSp = maSp;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.thanhTien = thanhTien;
        this.tongTienhang = tongTienhang;
        this.tenKhachHang = tenKhachHang;
        this.tenNV = tenNV;
        this.maHd = maHd;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
        this.tongSoluong=tongSoluong;
    }

    public int getTongSoluong() {
        return tongSoluong;
    }

    public void setTongSoluong(int tongSoluong) {
        this.tongSoluong = tongSoluong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGiaGiam() {
        return giaGiam;
    }

    public void setGiaGiam(int giaGiam) {
        this.giaGiam = giaGiam;
    }

   

   

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(int giaNhap) {
        this.giaNhap = giaNhap;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public int getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }

    public int getTongTienhang() {
        return tongTienhang;
    }

    public void setTongTienhang(int tongTienhang) {
        this.tongTienhang = tongTienhang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getMaHd() {
        return maHd;
    }

    public void setMaHd(String maHd) {
        this.maHd = maHd;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
    
    
    
}
