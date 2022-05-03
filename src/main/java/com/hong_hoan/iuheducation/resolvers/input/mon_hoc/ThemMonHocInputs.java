package com.hong_hoan.iuheducation.resolvers.input.mon_hoc;

import lombok.*;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ThemMonHocInputs {
    private String ten;
    private String moTa;
    private long khoaVienID;
}
