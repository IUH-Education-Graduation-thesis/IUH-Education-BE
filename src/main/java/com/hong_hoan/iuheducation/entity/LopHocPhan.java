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

    private String maLopHocPhan;
    private String tenLopHocPhan;
    private int soNhomThucHanh;
    private int soLuongToiDa;
    private String moTa;

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

}