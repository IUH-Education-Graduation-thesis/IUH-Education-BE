package com.hong_hoan.iuheducation.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hoc_ky")
public class HocKy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private int thuTu;
    private String moTa;

    @OneToMany(mappedBy = "hocKy")
    private List<HocPhan> hocPhans = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "khoa_id")
    private Khoa khoa;

}