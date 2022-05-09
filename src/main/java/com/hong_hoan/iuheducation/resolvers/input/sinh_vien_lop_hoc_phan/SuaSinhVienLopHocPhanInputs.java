package com.hong_hoan.iuheducation.resolvers.input.sinh_vien_lop_hoc_phan;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SuaSinhVienLopHocPhanInputs {
    private Long sinhVienId;
    private Long lopHocPhanId;
    private List<Double> diemThuongKy;
    private Double diemGiuaKy;
    private List<Double> diemThucHanh;
    private Double diemCuoiKy;
    private String ghiChu;
}
