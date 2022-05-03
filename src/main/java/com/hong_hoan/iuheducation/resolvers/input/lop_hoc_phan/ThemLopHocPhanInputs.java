package com.hong_hoan.iuheducation.resolvers.input.lop_hoc_phan;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ThemLopHocPhanInputs {
    private String maLopHocPhan;
    private Integer soNhomThucHanh;
    private Integer soLuongToiDa;
    private String moTa;
    private String lopDuKien;
    private Long hocPhanId;
    private Long hocKyNormalId;
}