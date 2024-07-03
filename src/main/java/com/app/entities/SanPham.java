package com.app.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "SanPham", schema = "dbo")
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "bar_code", length = 13, unique = true)
    private String barCode;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "gia_ban", nullable = false, precision = 19, scale = 2)
    private BigDecimal giaBan;

    @Column(name = "ma", nullable = false, length = 50, unique = true)
    private String ma;

    @Nationalized
    @Column(name = "mo_ta", length = 1000)
    private String moTa;

    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;

    @Nationalized
    @Column(name = "ten", nullable = false, length = 250)
    private String ten;

    @Column(name = "trang_thai", nullable = false)
    private Boolean trangThai = false;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_chat_lieu", nullable = false)
    private ChatLieu idChatLieu;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_danh_muc", nullable = false)
    private DanhMuc idDanhMuc;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_kich_co", nullable = false)
    private KichCo idKichCo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_kieu_dang", nullable = false)
    private KieuDang idKieuDang;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_mau_sac", nullable = false)
    private MauSac idMauSac;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_thuong_hieu", nullable = false)
    private ThuongHieu idThuongHieu;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_xuat_xu", nullable = false)
    private XuatXu idXuatXu;

    @OneToMany(mappedBy = "idSanPham")
    private Set<HinhAnh> hinhAnhs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idSanPham")
    private Set<HoaDonChiTiet> hoaDonChiTiets = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idSanPham")
    private Set<LichSuNhapHang> lichSuNhapHangs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idSanPham")
    private Set<MaGiamGiaSanPham> maGiamGiaSanPhams = new LinkedHashSet<>();

}