package com.hong_hoan.iuheducation.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mon_hoc")
public class MonHoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String ten;
    private String moTa;
    private int soTinChiLyThuyet;
    private int soTinChiThucHanh;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hoc_phan_id")
    private HocPhan hocPhan;

    @ManyToOne
    @JoinColumn(name = "khoa_vien_id")
    private KhoaVien khoaVien;

}