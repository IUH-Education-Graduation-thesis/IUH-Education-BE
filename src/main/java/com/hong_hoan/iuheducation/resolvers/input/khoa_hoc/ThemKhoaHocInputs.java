package com.hong_hoan.iuheducation.resolvers.input.khoa_hoc;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ThemKhoaHocInputs {
    private int khoa;
    private String moTa;
}
