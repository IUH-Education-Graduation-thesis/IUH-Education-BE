package com.hong_hoan.iuheducation.entity;

import lombok.*;
import javax.persistence.*;
import java.util.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "khoa_vien")
public class KhoaVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String ten;
    private String link;
    private String moTa;

    @OneToMany(mappedBy = "khoaVien", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<MonHoc> monHocs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "khoaVien", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ChuyenNganh> chuyenNganhs = new ArrayList<>();

}