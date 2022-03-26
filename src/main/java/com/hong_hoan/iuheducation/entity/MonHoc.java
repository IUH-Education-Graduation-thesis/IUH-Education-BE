package com.hong_hoan.iuheducation.entity;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mon_hoc")
public class MonHoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String ten;
    private String moTa;
    private int soTinChiLyThuyet;
    private int soTinChiThucHanh;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "mon_hoc_chuyen_nganhs",
            joinColumns = @JoinColumn(name = "mon_hoc_id"),
            inverseJoinColumns = @JoinColumn(name = "chuyen_nganhs_id"))
    private Set<ChuyenNganh> chuyenNganhs = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "khoa_vien_id")
    private KhoaVien khoaVien;

}