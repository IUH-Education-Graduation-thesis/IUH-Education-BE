package com.hong_hoan.iuheducation.resolvers.input.hoc_ky_normal;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThemHocKyNormalInputs {
    private Integer thuTuHocKy;
    private String ghiChu;
    private Long namHocId;
}
