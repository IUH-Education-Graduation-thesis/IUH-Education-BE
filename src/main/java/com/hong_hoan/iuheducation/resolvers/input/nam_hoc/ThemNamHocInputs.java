package com.hong_hoan.iuheducation.resolvers.input.nam_hoc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ThemNamHocInputs {
    private Integer namBatDau;
    private Integer namKetThuc;
    private String ghiChu;

}
