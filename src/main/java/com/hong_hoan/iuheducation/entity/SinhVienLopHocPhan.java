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
@Table(name = "sinh_vien_lop_hoc_phan")
public class SinhVienLopHocPhan {
    @EmbeddedId
    private SinhVienLopHocPhanId sinhVienLopHocPhanId;

    @ElementCollection
    @Column(name = "diem_thuong_ky")
    @CollectionTable(name = "sinh_vien_lop_hoc_phan_diem_thuong_ky")
    private List<Double> diemThuongKy = new ArrayList<>();

    private Double diemGiuaKy;

    @ElementCollection
    @Column(name = "diem_thuc_hanh")
    @CollectionTable(name = "sinh_vien_lop_hoc_phan_diem_thuc_hanh")
    private List<Double> diemThucHanh = new ArrayList<>();

    private Double diemCuoiKy;
    private String ghiChu;
    private Integer nhomThucHanh;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("sinhVienId")
    @JoinColumn(name = "sinh_vien_id")
    private SinhVien sinhVien;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("lopHocPhanId")
    @JoinColumn(name = "lop_hoc_phan_id")
    private LopHocPhan lopHocPhan;

}