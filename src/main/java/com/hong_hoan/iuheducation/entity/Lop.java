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
@Table(name = "lop")
public class Lop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String ten;
    private String moTa;

    @ManyToOne
    @JoinColumn(name = "chuyen_nganh_id")
    private ChuyenNganh chuyenNganh;


    @ManyToOne
    @JoinColumn(name = "giang_vien_id")
    private GiangVien giangVien;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "khoa_id")
    private Khoa khoa;

    @OneToMany(mappedBy = "lop", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SinhVien> sinhViens = new ArrayList<>();

}