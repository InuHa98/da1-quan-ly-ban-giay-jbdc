package com.app.entities;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NhanVien", schema = "dbo")
public class NhanVien implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "id_nguoi_tao")
    private String idNguoiTao;

    @Basic
    @Column(name = "username", nullable = false, columnDefinition = "varchar(25)")
    private String username;

    @Basic
    @Column(name = "password", nullable = false, columnDefinition = "varchar(255)")
    private String password;
	
    @Basic
    @Column(name = "ho_ten", nullable = false, columnDefinition = "nvarchar(255)")
    private String hoTen;

    @Basic
    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Basic
    @Column(name = "so_dien_thoai", nullable = false)
    private String soDienThoai;

    @Basic
    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;

    @Basic
    @Column(name = "avatar", columnDefinition = "nvarchar(255)")
    private String avatar;

    @Basic
    @Column(name = "otp", columnDefinition = "varchar(6)")
    private String otp;

    @Basic
    @Column(name = "chuc_vu", nullable = false, columnDefinition = "varchar(10)")
    private String chucVu;

    @Basic
    @Column(name = "trang_thai", nullable = false)
    private Boolean trangThai;

    @Basic
    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Basic
    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;
}
