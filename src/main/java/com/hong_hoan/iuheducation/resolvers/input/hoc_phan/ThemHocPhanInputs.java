package com.hong_hoan.iuheducation.resolvers.input.hoc_phan;

import lombok.*;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ThemHocPhanInputs {
    private String moTa;
    private boolean batBuoc;
    private Long hocKyId;
    private Long monHocId;
    private Integer soTinChiLyThuyet;
    private Integer soTinChiThucHanh;
}
