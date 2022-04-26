package com.hong_hoan.iuheducation.resolvers.input.khoa_hoc;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ThemKhoaHocInputs {
    private Integer khoa;
    private String moTa;
    private Date thoiGianBatDau;
    private Date thoiGianKetThuc;
    private Long chuyenNganhId;
}
