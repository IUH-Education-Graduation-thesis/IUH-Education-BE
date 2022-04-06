package com.hong_hoan.iuheducation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TrangThai {
    RA_TRUONG("Đã ra trường"),
    DANG_HOC("Đang học"),
    BAO_LUU("Bảo lưu");

    private final String name;
}
