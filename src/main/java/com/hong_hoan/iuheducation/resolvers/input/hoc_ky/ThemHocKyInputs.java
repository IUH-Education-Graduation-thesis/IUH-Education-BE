package com.hong_hoan.iuheducation.resolvers.input.hoc_ky;

import lombok.*;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ThemHocKyInputs {
    private Long khoaId;
    private String moTa;
    private Integer thuTu;
}
