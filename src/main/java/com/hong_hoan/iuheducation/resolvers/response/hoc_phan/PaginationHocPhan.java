package com.hong_hoan.iuheducation.resolvers.response.hoc_phan;

import com.hong_hoan.iuheducation.entity.HocPhan;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class PaginationHocPhan {
    private int count;
    private List<HocPhan> data;
}
