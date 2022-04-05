package com.hong_hoan.iuheducation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TonGiao {
    PHAT_GIAO("Phật giáo"),
    THIEN_CHUA_GIAO("Thiên chúa giáo");

    private final String name;
}
