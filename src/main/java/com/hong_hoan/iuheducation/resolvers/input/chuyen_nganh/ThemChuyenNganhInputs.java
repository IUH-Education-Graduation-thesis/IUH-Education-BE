package com.hong_hoan.iuheducation.resolvers.input.chuyen_nganh;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ThemChuyenNganhInputs {
    private String ten;
    private String moTa;
    private long khoaVienID;
}
