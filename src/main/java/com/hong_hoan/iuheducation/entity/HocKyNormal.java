package com.hong_hoan.iuheducation.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hoc_ky_normal")
public class HocKyNormal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer thuTuHocKy;

    private String ghiChu;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "nam_hoc_id", nullable = false)
    private NamHoc namHoc;

}
