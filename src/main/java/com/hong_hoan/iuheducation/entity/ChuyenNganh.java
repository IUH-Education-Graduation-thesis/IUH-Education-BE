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

    @OneToMany(mappedBy = "chuyenNganh")
    private List<Lop> lops = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "chuyen_nganh_mon_hocs",
            joinColumns = @JoinColumn(name = "chuyen_nganh_id"),
            inverseJoinColumns = @JoinColumn(name = "mon_hocs_id"))
    private Set<MonHoc> monHocs = new LinkedHashSet<>();

}