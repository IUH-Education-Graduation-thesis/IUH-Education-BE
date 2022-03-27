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
@Table(name = "chuyen_nganh")
public class ChuyenNganh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String ten;
    private String moTa;

    @ManyToOne
    @JoinColumn(name = "khoa_vien_id")
    private KhoaVien khoaVien;

    @OneToMany(mappedBy = "chuyenNganh", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Lop> lops = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "chuyen_nganh_mon_hocs",
            joinColumns = @JoinColumn(name = "chuyen_nganh_id"),
            inverseJoinColumns = @JoinColumn(name = "mon_hocs_id"))
    private Set<MonHoc> monHocs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "chuyenNganh", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<GiangVien> giangViens = new LinkedHashSet<>();

}