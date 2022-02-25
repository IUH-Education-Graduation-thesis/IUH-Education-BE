package com.hong_hoan.iuheducation.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "chuyenNganh", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MonHoc> monHocs = new ArrayList<>();


    @OneToMany(mappedBy = "chuyenNganh")
    private List<Lop> lops = new ArrayList<>();

}