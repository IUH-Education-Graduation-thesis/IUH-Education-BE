package com.hong_hoan.iuheducation.resolvers.input.chuyen_nganh;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FindChuyenNganhInputs {
    private String id;
    private List<Long> khoaVienIds;
}
