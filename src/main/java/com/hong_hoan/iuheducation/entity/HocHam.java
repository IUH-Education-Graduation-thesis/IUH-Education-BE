package com.hong_hoan.iuheducation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HocHam {
    THAC_SI("Thạc sĩ"),
    TIEN_SI("Tiến sĩ");

    private final String name;
}
