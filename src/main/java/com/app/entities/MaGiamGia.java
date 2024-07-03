package com.app.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "MaGiamGia", schema = "dbo")
public class MaGiamGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "don_hang_toi_thieu", nullable = false, precision = 19, scale = 2)
    private BigDecimal donHangToiThieu;

    @Column(name = "giam_toi_da", nullable = false, precision = 19, scale = 2)
    private BigDecimal giamToiDa;

    @Column(name = "ma", nullable = false, length = 50, unique = true)
    private String ma;

    @Column(name = "ngay_bat_dau", nullable = false)
    private Instant ngayBatDau;

    @Column(name = "ngay_ket_thuc", nullable = false)
    private Instant ngayKetThuc;

    @Column(name = "phan_tram_giam_gia", nullable = false)
    private Integer phanTramGiamGia;

    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;

    @Column(name = "trang_thai", nullable = false)
    private Boolean trangThai = false;

    @OneToMany(mappedBy = "idMaGiamGia")
    private Set<MaGiamGiaSanPham> maGiamGiaSanPhams = new LinkedHashSet<>();

}