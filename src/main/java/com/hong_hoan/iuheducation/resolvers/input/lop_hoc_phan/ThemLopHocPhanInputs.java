package com.hong_hoan.iuheducation.resolvers.input.lop_hoc_phan;

import com.hong_hoan.iuheducation.entity.TrangThaiLopHocPhan;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ThemLopHocPhanInputs {
    private Integer soNhomThucHanh;
    private Integer soLuongToiDa;
    private String moTa;
    private String lopDuKien;
    private Long hocPhanId;
    private Long hocKyNormalId;
    private TrangThaiLopHocPhan trangThaiLopHocPhan;
}
