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
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "DanhMuc", schema = "dbo")
public class DanhMuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "ghi_chu", length = 250)
    private String ghiChu;

    @Nationalized
    @Column(name = "ten", nullable = false, length = 250)
    private String ten;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refid")
    private DanhMuc refid;

    @OneToMany(mappedBy = "refid")
    private Set<DanhMuc> danhMucs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idDanhMuc")
    private Set<SanPham> sanPhams = new LinkedHashSet<>();

}