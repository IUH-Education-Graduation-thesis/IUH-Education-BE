package com.hong_hoan.iuheducation.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "lop_hoc_phan_giang_viens",
            joinColumns = @JoinColumn(name = "lop_hoc_phan_id"),
            inverseJoinColumns = @JoinColumn(name = "giang_viens_id"))
    private Set<GiangVien> giangViens = new LinkedHashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lop_id")
    private Lop lop;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hoc_phan_id")
    private HocPhan hocPhan;

    @OneToMany(mappedBy = "lopHocPhan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SinhVienLopHocPhan> sinhVienLopHocPhans = new ArrayList<>();

    @OneToMany(mappedBy = "lopHocPhan", orphanRemoval = true)
    private List<LichHoc> lichHocs = new ArrayList<>();

}