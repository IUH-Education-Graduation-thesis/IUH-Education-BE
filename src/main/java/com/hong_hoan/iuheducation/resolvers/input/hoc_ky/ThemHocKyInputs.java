package com.hong_hoan.iuheducation.resolvers.input.hoc_ky;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ThemHocKyInputs {
    private String namHocId;
    private String moTa;
    private int thuTu;
}
