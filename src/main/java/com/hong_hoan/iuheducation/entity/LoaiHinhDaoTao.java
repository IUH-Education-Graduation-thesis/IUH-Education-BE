package com.hong_hoan.iuheducation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoaiHinhDaoTao {
    TIEN_TIEN("Tiên tiến"),
    DAI_TRA("Đại trà");

    private final String name;
}
