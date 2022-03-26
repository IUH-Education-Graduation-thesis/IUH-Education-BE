package com.hong_hoan.iuheducation.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "lich_hoc")
public class LichHoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "ngay_hoc_trong_tuan", nullable = false)
    private int ngayHocTrongTuan;
    @Column(name = "nhom_thuc_hanh", nullable = false)
    private int nhomThucHanh;
    @Column(nullable = false)
    private Date thoiGianBatDau;
    @Column(nullable = false)
    private Date thoiGianKetThuc;
    @Column(nullable = false)
    private int tietHocBatDau;
    @Column(nullable = false)
    private int tietHocKetThuc;
    @Column(nullable = false)
    private String ghiChu;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "phong_hoc_id")
    private PhongHoc phongHoc;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lop_hoc_phan_id")
    private LopHocPhan lopHocPhan;

}