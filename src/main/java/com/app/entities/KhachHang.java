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
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "KhachHang", schema = "dbo")
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "diem")
    private Integer diem;

    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;

    @Nationalized
    @Column(name = "ho_ten", nullable = false, length = 250)
    private String hoTen;

    @Column(name = "sdt", nullable = false, length = 13, unique = true)
    private String sdt;

    @OneToMany(mappedBy = "idKhachHang")
    private Set<HoaDon> hoaDons = new LinkedHashSet<>();

}