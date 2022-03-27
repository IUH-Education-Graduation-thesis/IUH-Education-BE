package com.hong_hoan.iuheducation.resolvers.input.mon_hoc;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ThemMonHocInputs {
    private String ten;
    private String moTa;
    private int soTinChiLyThuyet;
    private int soTinChiThucHanh;
    private long khoaVienID;
}
