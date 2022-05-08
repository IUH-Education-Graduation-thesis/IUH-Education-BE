package com.hong_hoan.iuheducation.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lop_hoc_phan")
public class LopHocPhan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String maLopHocPhan;
    private String tenLopHocPhan;
    private int soNhomThucHanh;
    @Column(nullable = false)
    private int soLuongToiDa;
    private String moTa;

    private String lopDuKien;

    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai_lop_hoc_phan")
    private TrangThaiLopHocPhan trangThaiLopHocPhan;

    @ManyToMany
    @JoinTable(name = "lop_hoc_phan_giang_viens",
            joinColumns = @JoinColumn(name = "lop_hoc_phan_id"),
            inverseJoinColumns = @JoinColumn(name = "giang_viens_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<GiangVien> giangViens = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lop_id")
    private Lop lop;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hoc_phan_id")
    private HocPhan hocPhan;

    @OneToMany(mappedBy = "lopHocPhan", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<SinhVienLopHocPhan> sinhVienLopHocPhans = new HashSet<>();

    @OneToMany(mappedBy = "lopHocPhan", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<LichHoc> lichHocs = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hoc_ky_normal_id", nullable = false)
    private HocKyNormal hocKyNormal;

    public String getTenLopHocPhan() {
        if (tenLopHocPhan != null) {
            return tenLopHocPhan;
        }

        return hocPhan.getMonHoc().getTen();
    }

    public Integer getSoLuongHienTai() {
        try {
            return sinhVienLopHocPhans.size();
        } catch (NullPointerException ex) {
            return 0;
        }
    }

    public String getTrangThaiLopHocPhan() {
        try {
            return this.trangThaiLopHocPhan.getName();

        } catch (NullPointerException ex) {
            return null;
        }
    }

    public TrangThaiLopHocPhan getTrangThaiLopHocPhanEnum() {
        return this.trangThaiLopHocPhan;
    }

    ;

    public String getLopDuKien() {
        if (this.lopDuKien != null) return lopDuKien;

        try {
            return this.lop.getTen();
        } catch (NullPointerException ex) {
            return null;
        }
    }
}