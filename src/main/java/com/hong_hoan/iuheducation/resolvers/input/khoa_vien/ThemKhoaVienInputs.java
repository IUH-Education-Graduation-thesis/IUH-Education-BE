package com.hong_hoan.iuheducation.resolvers.input.khoa_vien;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ThemKhoaVienInputs {
    private String ten;
    private String link;
    private String moTa;
}
