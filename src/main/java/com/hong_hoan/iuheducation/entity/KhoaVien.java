package com.hong_hoan.iuheducation.entity;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import
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

    private String ten;
    private String link;
    private String moTa;


    @OneToMany(mappedBy = "khoaVien")
    private List<ChuyenNganh> chuyenNganhs = new ArrayList<>();

}