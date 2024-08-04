/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.core.lam.models;

import com.app.utils.TimeUtils;
import java.time.LocalDateTime;

/**
 *
 * @author Admin
 */
public class LichSuModels {

    private int idKH;

    private String maHD;

    private Double giaBan;

    private int soLuong;

    private LocalDateTime ngayMua;

    private boolean trangThaiXoa;

    public LichSuModels() {
    }

    public LichSuModels(int idKH, String maHD, Double giaBan, int soLuong, boolean trangThaiXoa) {
        this.idKH = idKH;
        this.maHD = maHD;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.ngayMua = LocalDateTime.now();
        this.trangThaiXoa = trangThaiXoa;
    }

    public int getIdKH() {
        return idKH;
    }

    public void setIdKH(int idKH) {
        this.idKH = idKH;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public Double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(Double giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public LocalDateTime getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(LocalDateTime ngayMua) {
        this.ngayMua = ngayMua;
    }

    public boolean isTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(boolean trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }

    @Override
    public String toString() {
        return "LichSuModels{" + "idKH=" + idKH + ", maHD=" + maHD + ", giaBan=" + giaBan + ", soLuong=" + soLuong + ", ngayMua=" + ngayMua + ", trangThaiXoa=" + trangThaiXoa + '}';
    }

}
