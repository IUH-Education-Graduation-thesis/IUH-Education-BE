package com.hong_hoan.iuheducation.resolvers.input.nam_hoc;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Builder
@Getter
@ToString
public class ThemNamHocInputs {
    private String moTa;
    private Date ngayBatDau;
    private Date ngayKetThuc;
}
