package com.hong_hoan.iuheducation.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "nam_hoc")
public class NamHoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private Integer namBatDau;

    @Column(nullable = false)
    private Integer namKetThuc;
    private String ghiChu;
}