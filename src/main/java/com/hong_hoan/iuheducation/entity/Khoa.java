package com.hong_hoan.iuheducation.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "khoa")
public class Khoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private int khoa;
    private String moTa;

    private Date thoiGianBatDau;
    private Date thoiGianKetThuc;

    @ManyToOne
    @JoinColumn(name = "chuyen_nganh_id")
    private ChuyenNganh chuyenNganh;

    @OneToMany(mappedBy = "khoa", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Lop> lops = new ArrayList<>();

    @OneToMany(mappedBy = "khoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HocKy> hocKies = new ArrayList<>();

}