package com.hong_hoan.iuheducation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BacDaoTao {
    DAI_HOC("Đại học"),
    CAO_DANG("Cao đẳng"),
    THAC_SI("Thạc sĩ");

    private final String name;
}
