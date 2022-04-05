package com.hong_hoan.iuheducation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DanToc {
    KINH("Kinh"),
    HOA("Hoa");

    private final String name;
}
