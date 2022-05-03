package com.hong_hoan.iuheducation.resolvers.input.chuyen_nganh;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ThemChuyenNganhInputs {
    private String ten;
    private String moTa;
    private long khoaVienID;
}
