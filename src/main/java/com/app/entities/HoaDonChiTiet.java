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
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "HoaDonChiTiet", schema = "dbo")
public class HoaDonChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "da_thanh_toan", nullable = false)
    private Boolean daThanhToan = false;

    @Nationalized
    @Column(name = "dia_chi", length = 250)
    private String diaChi;

    @Nationalized
    @Column(name = "ghi_chu", length = 1000)
    private String ghiChu;

    @Column(name = "gia_ban", nullable = false, precision = 19, scale = 2)
    private BigDecimal giaBan;

    @Column(name = "gia_giam", nullable = false, precision = 19, scale = 2)
    private BigDecimal giaGiam;

    @Nationalized
    @Column(name = "ho_ten", length = 250)
    private String hoTen;

    @Column(name = "phi_ship", precision = 19, scale = 2)
    private BigDecimal phiShip;

    @Column(name = "sdt", length = 13)
    private String sdt;

    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_hoa_don", nullable = false)
    private HoaDon idHoaDon;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_san_pham", nullable = false)
    private SanPham idSanPham;

}