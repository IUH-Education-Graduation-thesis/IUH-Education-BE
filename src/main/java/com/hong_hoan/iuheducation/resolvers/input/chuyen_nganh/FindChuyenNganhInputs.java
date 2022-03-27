package com.hong_hoan.iuheducation.resolvers.input.chuyen_nganh;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class FindChuyenNganhInputs {
    private String id;
    private List<Long> khoaVienIds;
}
