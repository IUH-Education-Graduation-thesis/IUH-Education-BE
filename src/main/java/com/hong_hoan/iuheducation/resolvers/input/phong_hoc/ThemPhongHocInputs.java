package com.hong_hoan.iuheducation.resolvers.input.phong_hoc;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ThemPhongHocInputs {
    private String tenPhongHoc;
    private String moTa;
    private int sucChua;
    private long dayNhaId;
}
