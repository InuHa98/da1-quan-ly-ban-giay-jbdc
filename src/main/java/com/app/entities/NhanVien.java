package com.app.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "NhanVien", schema = "dbo")
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "avatar", length = 250)
    private String avatar;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Nationalized
    @Column(name = "dia_chi", length = 250)
    private String diaChi;

    @Nationalized
    @Column(name = "email", nullable = false, length = 250, unique = true)
    private String email;

    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;

    @Nationalized
    @Column(name = "ho_ten", length = 250)
    private String hoTen;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "otp", length = 6)
    private String otp;

    @Nationalized
    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "sdt", nullable = false, length = 13, unique = true)
    private String sdt;

    @Column(name = "trang_thai")
    private Boolean trangThai;

    @Column(name = "username", nullable = false, length = 25, unique = true)
    private String username;

    @OneToMany(mappedBy = "idNhanVien")
    private Set<HoaDon> hoaDons = new LinkedHashSet<>();

}